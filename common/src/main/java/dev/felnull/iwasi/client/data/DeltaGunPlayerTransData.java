package dev.felnull.iwasi.client.data;

import dev.felnull.iwasi.gun.trans.player.GunPlayerTrans;

public record DeltaGunPlayerTransData(GunPlayerTrans gunTrans, float progress, int step) {
}