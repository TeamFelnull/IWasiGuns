package dev.felnull.iwasi.client.model.pointer;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.Map;

public class ModelPointerLoader {
    public static final Logger LOGGER = LogManager.getLogger(ModelPointerLoader.class);
    private static final Gson GSON = new Gson();
    protected final Map<ResourceLocation, ModelPointer> pointers;

    protected ModelPointerLoader(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        profilerFiller.startTick();
        ImmutableMap.Builder<ResourceLocation, ModelPointer> builder = ImmutableMap.builder();

        resourceManager.listResources("model_pointer", loc -> loc.getPath().endsWith(".json")).forEach(((location, resource) -> {
            profilerFiller.push(location.toString());

            try (Reader reader = resource.openAsReader()) {
                JsonObject jo = GSON.fromJson(reader, JsonObject.class);
                var p = location.getPath();
                var mp = ModelPointer.parse(jo);
                builder.put(new ResourceLocation(location.getNamespace(), p.substring("model_pointer/".length(), p.length() - ".json".length())), mp);
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading model pointer resource json " + location, e);
            }
            profilerFiller.pop();
        }));

        this.pointers = builder.build();
        profilerFiller.endTick();
    }
}
