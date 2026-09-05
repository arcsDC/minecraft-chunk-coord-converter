package com.example.chunkcoords;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.lwjgl.glfw.GLFW;

public class ChunkCoordsMod implements ClientModInitializer {
    public static final String MOD_ID = "chunkcoords";
    private static KeyBinding toggleKey;
    private static boolean visible = true;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Toggle Chunk Coordinates",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "Chunk Coords"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                visible = !visible;
            }

            if (visible && client.player != null) {
                BlockPos pos = client.player.getBlockPos();
                ChunkPos chunk = new ChunkPos(pos);
                String info = String.format("Chunk: %d, %d", chunk.x, chunk.z);
                client.inGameHud.getDebugHud().addLine(Text.literal(info));
            }
        });
    }
}
