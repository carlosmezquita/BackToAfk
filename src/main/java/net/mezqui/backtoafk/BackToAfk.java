package net.mezqui.backtoafk;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.mezqui.backtoafk.utils.Returner;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class BackToAfk implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("backtoafk");
	public static final String MOD_ID = "backtoafk";

	private int ticks = -1;
	static boolean isActive = false;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ClientCommandManager.DISPATCHER.register(
				literal("bafk").then(literal("on").executes(context -> {
					context.getSource().sendFeedback(new LiteralText("BeBackAfk is now ON").formatted(Formatting.GREEN));
					isActive = true;
					return 0;
				})

		));
		ClientCommandManager.DISPATCHER.register(
				literal("bafk").then(literal("off").executes(context -> {
							context.getSource().sendFeedback(new LiteralText("BeBackAfk is now OFF.").formatted(Formatting.RED));
							isActive = false;
							return 0;
						})

				));
		ClientCommandManager.DISPATCHER.register(
				literal("bafk").executes(context -> {
							context.getSource().sendFeedback(new LiteralText("BeBackAfk is now set to: " +isActive).formatted(Formatting.GOLD));
							return 0;
						})

				);

		ClientPlayConnectionEvents.JOIN.register(
				((handler, sender, client) -> {
					if (isActive){
						ticks = 20*10;
						ClientTickEvents.START_CLIENT_TICK.register(
								(MinecraftClient) -> {
									if (ticks >= 0 && --ticks == 0) {
										Returner.tpBack(isActive);
									}
								}
						);
					}
				})
		);
	}
}
