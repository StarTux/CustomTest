package com.winthier.customtest;

import com.winthier.custom.CustomConfig;
import com.winthier.custom.CustomPlugin;
import com.winthier.custom.event.ItemEventContext;
import com.winthier.custom.item.CustomItem;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@Getter
public class TestItem implements CustomItem {
    final String customId = "Test";

    @Override
    public ItemStack spawnItemStack(int amount, CustomConfig config) {
        return new ItemStack(Material.BOW, amount);
    }
    
    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock().getRelative(event.getBlockFace());
            Location loc = block.getLocation().add(0.5, 0.5, 0.5);
            block.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc.getX(), loc.getY(), loc.getZ(), 32);
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.isCancelled()) return;
        Location loc = event.getRightClicked().getLocation();
        event.getRightClicked().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc.getX(), loc.getY(), loc.getZ(), 32);
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        Player player = (Player)event.getEntity();
        player.playSound(player.getEyeLocation(), Sound.ENTITY_CAT_AMBIENT, 1.0f, 1.0f);
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        ItemEventContext context = ItemEventContext.of(event);
        if (context.getHand() != EquipmentSlot.HAND) return;
        event.setCancelled(true);
        context.getPlayer().sendMessage("BOOM");
    }
}
