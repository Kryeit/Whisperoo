package com.kryeit.mixin;

import com.kryeit.commands.Reply;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.commands.MsgCommand;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(MsgCommand.class)
public class MessageCommandMixin {
    @Inject(method = "sendMessage", at = @At("HEAD"))
    private static void onSendMessage(CommandSourceStack source, Collection<ServerPlayer> targets, PlayerChatMessage message, CallbackInfo ci) {
        ServerPlayer sender = source.getPlayer();
        if (sender != null) {
            for (ServerPlayer target : targets)
                Reply.onMessageReceived(sender.getUUID(), target.getUUID());
        }
    }
}
