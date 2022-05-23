package dev.felnull.iwasi.client.entrypoint;

import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.iwasi.client.particles.IWClientParticles;
import dev.felnull.iwasi.client.renderer.entity.layers.TmpItemInHandLayer;
import dev.felnull.otyacraftengine.client.entrypoint.IOEClientEntryPoint;
import dev.felnull.otyacraftengine.client.entrypoint.LayerRegister;
import dev.felnull.otyacraftengine.client.entrypoint.OEClientEntryPoint;
import dev.felnull.otyacraftengine.client.entrypoint.ParticleRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.function.Consumer;

@OEClientEntryPoint
public class IWOEClientEntryPoint implements IOEClientEntryPoint {
    @Override
    public void onModelRegistry(Consumer<ResourceLocation> addModel) {
        IWModels.init(addModel);
    }

    @Override
    public void onLayerRegistry(LayerRegister register) {
        register.addLayer(EntityType.PLAYER, TmpItemInHandLayer::new);
    }

    @Override
    public void onParticleRegistry(ParticleRegister register) {
        IWClientParticles.init(register);
    }
}