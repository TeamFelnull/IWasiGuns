package dev.felnull.iwasi.client.util;

import dev.felnull.iwasi.client.data.DeltaGunTransData;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.util.IWItemUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class IWClientPlayerData {
    public static DeltaGunTransData getGunTransData(@NotNull Player player, InteractionHand hand, float delta) {
        var gun = IWItemUtil.getGunNullable(player.getItemInHand(hand));
        if (gun != null) {
            var data = (IIWDataPlayer) player;
            var lst = data.getGunTrans(hand);
            var old = data.getGunTransOld(hand);
            if (old.getGunTrans() != null) {
                int tp = lst.progress();
                int tpo = old.progress();
                if (lst.step() != old.step() || lst.getGunTrans() == null)
                    tp = old.getGunTrans().getProgress(gun, old.step()) - 1;
                return new DeltaGunTransData(lst.getGunTrans(), Mth.lerp(delta, tpo, tp), old.step());
            }
        }
        return new DeltaGunTransData(null, 0, 0);
    }
}
