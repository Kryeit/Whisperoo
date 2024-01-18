package com.kryeit;

import com.kryeit.commands.Message;
import com.kryeit.commands.Reply;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Whisperoo implements DedicatedServerModInitializer {

	public static String ID = "whisperoo";
    public static final Logger LOGGER = LoggerFactory.getLogger(Whisperoo.class);

	@Override
	public void onInitializeServer() {
		registerCommands();
	}

	public void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicatedServer, commandFunction) -> {
			Reply.register(dispatcher);
			Message.register(dispatcher);
		});
	}
}