package com.kryeit;

import com.kryeit.commands.Message;
import com.kryeit.commands.Reply;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Whisperoo.ID)
public class Whisperoo {

	public static final String ID = "whisperoo";
    public static final Logger LOGGER = LoggerFactory.getLogger(Whisperoo.class);

	public Whisperoo() {
		NeoForge.EVENT_BUS.addListener(Whisperoo::registerCommands);
	}

	public static void registerCommands(RegisterCommandsEvent event) {
		Reply.register(event.getDispatcher());
		Message.register(event.getDispatcher());
	}
}
