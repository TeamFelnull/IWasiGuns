package dev.felnull.iwasi.data;

import dev.felnull.iwasi.gun.trans.GunTransRegistry;
import dev.felnull.iwasi.gun.trans.item.GunItemTrans;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public record GunItemTransData(@NotNull ResourceLocation name, int progress, int step) {
    public GunItemTransData(GunItemTrans gunTrans) {
        this(gunTrans, 0, 0);
    }

    public GunItemTransData(GunItemTrans gunTrans, int progress, int step) {
        this(gunTrans.getName(), progress, step);
    }

    @Nullable
    public GunItemTrans getGunTrans() {
        return GunTransRegistry.getByName(name);
    }

    public static List<GunItemTransData> readList(CompoundTag tag) {
        List<GunItemTransData> list = new ArrayList<>();
        if (tag != null && tag.contains("GunTransList", Tag.TAG_LIST)) {
            ListTag listTag = tag.getList("GunTransList", Tag.TAG_COMPOUND);
            if (listTag != null) {
                for (int i = 0; i < listTag.size(); ++i) {
                    CompoundTag tag2 = listTag.getCompound(i);
                    list.add(read(tag2));
                }
            }
        }
        return list;
    }

    public static CompoundTag writeList(CompoundTag tag, List<GunItemTransData> gunTransItemDatas) {
        ListTag listTag;
        if (tag.contains("GunTransList", Tag.TAG_LIST)) {
            listTag = tag.getList("GunTransList", Tag.TAG_COMPOUND);
            listTag.clear();
        } else {
            listTag = new ListTag();
        }
        for (GunItemTransData entry : gunTransItemDatas) {
            listTag.add(entry.write(new CompoundTag()));
        }
        tag.put("GunTransList", listTag);
        return tag;
    }

    public static GunItemTransData read(CompoundTag tag) {
        return new GunItemTransData(new ResourceLocation(tag.getString("Name")), tag.getInt("Progress"), tag.getInt("Step"));
    }

    public CompoundTag write(CompoundTag tag) {
        tag.putString("Name", name.toString());
        tag.putInt("Progress", progress);
        tag.putInt("Step", step);
        return tag;
    }
}
