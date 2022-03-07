package dev.felnull.iwasi.client.renderer.entity;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.felnull.iwasi.entity.IWEntityType;

public class IWEntityRenderers {
    public static void init() {
        EntityRendererRegistry.register(IWEntityType.TEST_BULLET::get, TestBulletRenderer::new);
    }
}
