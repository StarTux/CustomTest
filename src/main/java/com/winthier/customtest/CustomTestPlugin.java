 package com.winthier.customtest;

import com.winthier.custom.CustomPlugin;
import com.winthier.custom.event.CustomRegisterEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.ShapedRecipe;

public class CustomTestPlugin extends JavaPlugin implements Listener {
    @Override public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onCustomRegister(CustomRegisterEvent event) {
        event.addItem(new TestItem());
        event.addEntity(new TestCow());
        event.addBlock(new TestBlock());
        new BukkitRunnable() {
            @Override public void run() {
                registerRecipes();
            }
        }.runTask(this);
    }

    void registerRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(CustomPlugin.getInstance().getItemManager().spawnItemStack("test:stick", 1));
        recipe = recipe.shape("xxx", "xxx", "xxx");
        recipe.setIngredient('x', Material.ENDER_PEARL);
        getServer().addRecipe(recipe);
    }
}
