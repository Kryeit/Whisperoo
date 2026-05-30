package com.kryeit.commands;

import com.kryeit.MinecraftServerSupplier;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.UUID;

public class Reply {
    private static final HashMap<UUID, UUID> lastMessageSender = new HashMap<>();

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        UUID playerUUID = player.getUUID();
        if (!lastMessageSender.containsKey(playerUUID)) {
            player.sendSystemMessage(Component.literal("No one to reply to."));
            return 1;
        }

        UUID targetUUID = lastMessageSender.get(playerUUID);
        ServerPlayer targetPlayer = MinecraftServerSupplier.getServer().getPlayerList().getPlayer(targetUUID);
        if (targetPlayer == null) {
            player.sendSystemMessage(Component.literal("Player is not online."));
            return 1;
        }

        String message = StringArgumentType.getString(context, "message");

        String command = String.format("/tell %s %s", targetPlayer.getName().getString(), message);
        MinecraftServerSupplier.getServer().getCommands().performPrefixedCommand(context.getSource(), command);

        lastMessageSender.put(targetUUID, playerUUID);
        return 1;
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("reply")
                .then(Commands.argument("message", StringArgumentType.greedyString())
                        .executes(Reply::execute)
                )
        );

        dispatcher.register(Commands.literal("r")
                .then(Commands.argument("message", StringArgumentType.greedyString())
                        .executes(Reply::execute)
                )
        );
    }

    public static void onMessageReceived(UUID senderUUID, UUID receiverUUID) {
        lastMessageSender.put(receiverUUID, senderUUID);
    }
}
