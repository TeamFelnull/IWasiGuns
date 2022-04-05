package dev.felnull.iwasi.gun;

/**
 * 銃のプロパティ
 *
 * @param chamberCapacity 薬室内の装弾数
 * @param shotCoolDown    発射後のクールダウン
 * @param weight          重さ(g)
 */
public record GunProperties(int chamberCapacity, int shotCoolDown, float weight) {

    public static class Builder {
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

        public GunProperties create() {
            return new GunProperties(chamberCapacity, shotCoolDown, weight);
        }
    }
}
