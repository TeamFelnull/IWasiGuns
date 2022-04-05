package dev.felnull.iwasi.client.data;

import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.felnull.iwasi.IWasi;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class IWKeyMappings {
    public static final KeyMapping RELOAD = new KeyMapping("key." + IWasi.MODID + ".reload", GLFW.GLFW_KEY_R, "key.categories." + IWasi.MODID);

    public static void init() {
        KeyMappingRegistry.register(RELOAD);
    }
}
