package com.winthier.customtest;

import com.winthier.custom.item.ItemRegisterEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomTestPlugin extends JavaPlugin implements Listener {
    @Override public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onItemRegister(ItemRegisterEvent event) {
        event.registerItem(new TestItem());
        event.registerItem(new GrowStickItem());
    }
}