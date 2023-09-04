package com.massivecraft.factions.cmd.banner.struct;

import com.google.common.collect.MapMaker;
import com.massivecraft.factions.FactionsPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class BannerManager {

    private final Map<String, FactionBanner> factionBannerMap = (new MapMaker())
            .concurrencyLevel(32).makeMap();

    public Map<String, FactionBanner> getFactionBannerMap() {
        return this.factionBannerMap;
    }

    public void onEnable(FactionsPlugin plugin) {
        (new BukkitRunnable() {
            public void run() {
                BannerManager.this.factionBannerMap.forEach((fId, banner) -> {
                    if (banner.hasExpired()) {
                        banner.removeBanner();
                        BannerManager.this.factionBannerMap.remove(fId);
                    }
                });
            }
        }).runTaskTimer(plugin, 20L, 20L);
    }

    public void onDisable(FactionsPlugin plugin) {
        this.factionBannerMap.values().forEach(FactionBanner::removeBanner);
    }

}
