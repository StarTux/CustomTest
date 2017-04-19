package com.winthier.customtest;

import com.winthier.custom.item.CustomItem;
import com.winthier.custom.item.ItemContext;
import com.winthier.custom.item.TickableItem;
import com.winthier.custom.util.Msg;
import java.util.Random;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

@Getter
public class TestItem implements CustomItem, TickableItem {
    private final String customId = "test:stick";
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public ItemStack spawnItemStack(int amount) {
        Material mat;
        ItemStack item = new ItemStack(Material.STICK, amount);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(Msg.format("&rTest Stick"));
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock().getRelative(event.getBlockFace());
            Location loc = block.getLocation().add(0.5, 0.5, 0.5);
            block.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc.getX(), loc.getY(), loc.getZ(), 32);
            event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_CAT_AMBIENT, 1.0f, 1.0f);
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.isCancelled()) return;
        Location loc = event.getRightClicked().getLocation();
        event.getRightClicked().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc.getX(), loc.getY(), loc.getZ(), 32);
        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_CAT_AMBIENT, 1.0f, 1.0f);
        Vector velo = event.getRightClicked().getVelocity();
        velo = velo.add(new Vector(0.0, 5.0 * random.nextDouble(), 0.0));
        event.getRightClicked().setVelocity(velo);
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event, ItemContext context) {
        if (context.getPosition() != ItemContext.Position.HAND) return;
        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_CAT_AMBIENT, 1.0f, 1.0f);
    }

    @Override
    public void onTick(ItemContext context) {
        
    }
}
