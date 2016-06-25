package com.winthier.customtest;

import com.winthier.custom.CustomPlugin;
import com.winthier.custom.item.AbstractItem;
import com.winthier.custom.item.ItemContext;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class TestItem extends AbstractItem {
    Material material = Material.EGG;
    String id = "TestItem";
    String displayName = "Test Item";
    String description = "This item is designed to test the Custom item plugin. The purpose of said plugin is to introduce custom(ized) items to a spigot server.";

    @Override
    public boolean handleEvent(Event event, ItemContext context) {
        if (event instanceof PlayerInteractEvent) {
            return handlePlayerInteract((PlayerInteractEvent)event);
        }
        return false;
    }

    boolean handlePlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock().getRelative(event.getBlockFace());
            Location loc = block.getLocation().add(0.5, 0.5, 0.5);
            block.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc.getX(), loc.getY(), loc.getZ(), 32);
        }
        event.setCancelled(true);
        new BukkitRunnable() {
            @Override public void run() {
                event.getPlayer().updateInventory();
            }
        }.runTask(CustomPlugin.getInstance());
        return true;
    }
}
