package net.mezqui.backtoafk.utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

import static net.minecraft.client.MinecraftClient.getInstance;

public class Returner {
    static MinecraftClient mc = getInstance();
    private static int ticks;

    public static void tpBack(boolean isActive) {

        if (isActive && Objects.equals(getBiome(), "minecraft:the_void")){
                   mc.inGameHud.getChatHud().addMessage(Text.literal("You are at the hub so you will be teleported in 30s.").formatted(Formatting.GREEN));
                   timerTeleport();
        }
    }

    private static String getBiome() {
        if (mc.world != null && mc.player != null) {
            return String.valueOf(mc.world.getRegistryManager().get(Registry.BIOME_KEY).getId(mc.world.getBiome(mc.player.getBlockPos()).value()));
        }
        return null;
    }


    private static void timerTeleport() {
        ticks =20*30;
        ClientTickEvents.START_CLIENT_TICK.register(
                (MinecraftClient) -> {
                            if (ticks >= 0 && --ticks == 0 && mc.player != null) {
                                mc.player.sendChatMessage("/server senior");
                                ticks =20*30;

                    }
                }
                );
    }


}
