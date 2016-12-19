package com.winthier.customtest;

import com.winthier.custom.event.CustomRegisterEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomTestPlugin extends JavaPlugin implements Listener {
    @Override public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onCustomRegister(CustomRegisterEvent event) {
        event.addItem(new TestItem());
        event.addEntity(new TestCow());
        event.addBlock(new TestBlock());
    }
}
