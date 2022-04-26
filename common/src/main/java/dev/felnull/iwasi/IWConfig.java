package dev.felnull.iwasi;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = IWasi.MODID)
public class IWConfig implements ConfigData {
    @ConfigEntry.Category("client")
    public boolean toggleHold = false;

    @ConfigEntry.Category("client")
    public boolean reverseHoldKeyAndTriggerKey = false;

    // @ConfigEntry.Category("client")
    // public boolean testForceAlex = false;
}
