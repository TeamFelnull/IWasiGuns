package dev.felnull.iwasi.gun;

/**
 * 銃の情報
 *
 * @param capacity               薬室内の装弾数
 * @param shotCoolDown           発射後のクールダウン
 * @param maxContinuousShotCount 最大連続射出数(負の数で無限)
 */
public record GunProperties(int capacity, int shotCoolDown, int maxContinuousShotCount) {

    public static Builder builder() {
        return new Builder();
    }

    protected static class Builder {
        private int capacity = 1;
        private int shotCoolDown = 3;
        private int maxContinuousShotCount = -1;

        public Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder shotCoolDown(int shotCoolDown) {
            this.shotCoolDown = shotCoolDown;
            return this;
        }

        public Builder maxContinuousShotCount(int maxContinuousShotCount) {
            this.maxContinuousShotCount = maxContinuousShotCount;
            return this;
        }

        public GunProperties build() {
            return new GunProperties(capacity, shotCoolDown, maxContinuousShotCount);
        }
    }
}
