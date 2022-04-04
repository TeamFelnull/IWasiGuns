package dev.felnull.iwasi.client;

import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.felnull.iwasi.IWasi;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class IWKeyMapping {
    public static final KeyMapping RELOAD = new KeyMapping("key." + IWasi.MODID + ".reload", GLFW.GLFW_KEY_R, "key.categories." + IWasi.MODID);

    public static void init() {
        KeyMappingRegistry.register(RELOAD);
    }
}
