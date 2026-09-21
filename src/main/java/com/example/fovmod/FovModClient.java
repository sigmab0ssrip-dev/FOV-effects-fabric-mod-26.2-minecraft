package com.example.fovmod;

import com.example.fovmod.gui.FovConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FovModClient implements ClientModInitializer {
    private static KeyBinding openFovMenuKey;

    @Override
    public void onInitializeClient() {
        // Register keybinding (Default key: O, customizable in Key Binds)
        openFovMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.fovmod.open_menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "category.fovmod.title"
        ));

        // Listen for key presses
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openFovMenuKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new FovConfigScreen(client.currentScreen));
                }
            }
        });
    }
}
