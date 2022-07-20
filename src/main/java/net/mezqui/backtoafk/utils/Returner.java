package net.mezqui.backtoafk.utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

import static net.mezqui.backtoafk.BackToAfk.isActive;
import static net.minecraft.client.MinecraftClient.getInstance;

public class Returner {
    static MinecraftClient mc = getInstance();
//    private static int ticks;

    public static void tpBack(boolean isActive) {
        if (isActive && Objects.equals(getBiome(), "minecraft:the_void")){
                   timerTeleport(0);
        }
    }


    private static String getBiome() {
        if (mc.world != null && mc.player != null) {
            return String.valueOf(mc.world.getRegistryManager().get(Registry.BIOME_KEY).getId(mc.world.getBiome(mc.player.getBlockPos()).value()));
        }
        return null;
    }


    private static void timerTeleport(int attempt) {
        attempt++;
        final int[] finalAttempt = {attempt};
        final int[] ticks = {20 * 30 * finalAttempt[0]};
        final int[] msgTicks = {20*5};

        ClientTickEvents.START_CLIENT_TICK.register(
                        (MinecraftClient) -> {
                            if (msgTicks[0] >= 0 && --msgTicks[0] == 0 && mc.player != null && Objects.equals(getBiome(), "minecraft:the_void")) {
                             mc.inGameHud.getChatHud().addMessage(Text.literal("You are at the hub so you will be teleported in "+30* finalAttempt[0] +"s. ("+ finalAttempt[0] +"/5)").formatted(Formatting.GREEN));
                            }
                            if (ticks[0] >= 0 && --ticks[0] == 0 && mc.player != null && Objects.equals(getBiome(), "minecraft:the_void")) {
//                                mc.player.sendChatMessage("/server senior");
                                if (finalAttempt[0] <= 5 && isActive && Objects.equals(getBiome(), "minecraft:the_void")){
                                    mc.player.sendCommand("server senior");
                                    timerTeleport(finalAttempt[0]);
                                }
                            }
                        }
                );


    }

    private static void checkBiome(int attempt) {

    }


}
