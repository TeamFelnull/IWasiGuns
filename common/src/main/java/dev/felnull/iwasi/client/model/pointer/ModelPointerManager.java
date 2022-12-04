package dev.felnull.iwasi.client.model.pointer;

import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.otyacraftengine.resources.PlatformResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ModelPointerManager extends PlatformResourceReloadListener<ModelPointerLoader> {
    private static final ModelPointerManager INSTANCE = new ModelPointerManager();
    private static final ResourceLocation ID = new ResourceLocation(IWasiGuns.MODID, "model_pointer");
    private Map<ResourceLocation, ModelPointer> modelPointers;

    public static ModelPointerManager getInstance() {
        return INSTANCE;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    protected ModelPointerLoader prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return new ModelPointerLoader(resourceManager, profilerFiller);
    }

    @Override
    protected void apply(ModelPointerLoader loader, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.modelPointers = loader.pointers;
    }

    @Nullable
    public ModelPointer getModelPointer(@NotNull ResourceLocation id) {
        return modelPointers.get(id);
    }
}
