package dev.felnull.iwasi.client.data;

import dev.felnull.iwasi.gun.trans.player.GunPlayerTrans;

public record DeltaGunTransData(GunPlayerTrans gunTrans, float progress, int step) {
}