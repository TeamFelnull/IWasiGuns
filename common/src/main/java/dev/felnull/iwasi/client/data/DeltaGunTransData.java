package dev.felnull.iwasi.client.data;

import dev.felnull.iwasi.gun.trans.GunTrans;

public record DeltaGunTransData(GunTrans gunTrans, float progress, int step) {
}