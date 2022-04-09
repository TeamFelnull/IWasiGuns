package dev.felnull.iwasi.gun;

import com.mojang.math.Vector3f;

/**
 * 銃のプロパティ
 *
 * @param chamberCapacity 薬室内の装弾数
 * @param shotCoolDown    発射後のクールダウン
 * @param weight          重さ(g)
 * @param size            銃サイズ
 */
public record GunProperties(int chamberCapacity, int shotCoolDown, float weight, Vector3f size) {

    public static class Builder {
        private final Vector3f size = new Vector3f(1f, 1f, 1f);
        private int chamberCapacity = 1;
        private int shotCoolDown = 3;
        private float weight = 500;

        public Builder setChamberCapacity(int chamberCapacity) {
            this.chamberCapacity = chamberCapacity;
            return this;
        }

        public Builder setShotCoolDown(int shotCoolDown) {
            this.shotCoolDown = shotCoolDown;
            return this;
        }

        public Builder setWeight(float weight) {
            this.weight = weight;
            return this;
        }

        public Builder setSize(float x, float y, float z) {
            size.set(x, y, z);
            return this;
        }

        public Builder setSize16(float x, float y, float z) {
            float pix = 1f / 16f;
            size.set(x * pix, y * pix, z * pix);
            return this;
        }


        public GunProperties create() {
            return new GunProperties(chamberCapacity, shotCoolDown, weight, size);
        }
    }
}
