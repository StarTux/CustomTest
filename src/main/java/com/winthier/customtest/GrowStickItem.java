package com.winthier.customtest;

import com.winthier.custom.item.AbstractItem;
import com.winthier.custom.item.ItemContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.minecraft.server.v1_10_R1.BlockPosition;
import org.bukkit.craftbukkit.v1_10_R1.util.CraftMagicNumbers;

/**
 * Code adapted from RypoFalem's GrowStick.
 * See https://github.com/RypoFalem/GrowStick
 */
@Getter
public class GrowStickItem extends AbstractItem {
    Material material = Material.STICK;
    String id = "GrowStick";
    String displayName = "Grow Stick";
    String description = "Use this grow stick to make things grow.";
    List<Material> doNotUpdateList = Arrays.<Material>asList(Material.PORTAL);
    float baseMultiplier = .2f;
    float skillMultiplier = .005f;
    Random rand = new Random(System.currentTimeMillis());

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        return defaultEnchantments();
    }

    @Override
    public boolean handleEvent(Event event, ItemContext context) {
        if (event instanceof PlayerInteractEvent) {
            return handlePlayerInteract((PlayerInteractEvent)event);
        }
        return false;
    }

    boolean handlePlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return false;
        Block clickedBlock = event.getClickedBlock();
        World world = clickedBlock.getWorld();
        world.playSound(clickedBlock.getLocation(), Sound.BLOCK_WATER_AMBIENT, .1f, 1);
        int range = getRange(event.getPlayer());
        int length= 2*range + 1;
        float updateBase = length * length * getMultiplier(event.getPlayer());
        int updates = (int) Math.max(1, updateBase * baseMultiplier);
        if((updateBase - (int)(updateBase)) * baseMultiplier <= rand.nextFloat()) {
            updates++; 
        }
        for(int i = 0; i<updates; i++) {
            int x = rand.nextInt(length) - range + clickedBlock.getX();
            int y = clickedBlock.getY();
            int z = rand.nextInt(length) - range + clickedBlock.getZ();
            for(int yOffset = 2; yOffset >= -1; yOffset--) {
                Block crop = world.getBlockAt(x, y + yOffset, z);
                Material cropType = crop.getType();
                boolean isBlacklisted = false;
                for(Material mat : doNotUpdateList) {
                    if(cropType.equals(mat)) {
                        isBlacklisted = true;
                        break;
                    }
                }
                if(isBlacklisted) continue;
                if(crop.getType() != Material.AIR) { //any other block is safe to schedule an update for
                    Location blockLoc = new Location(world, x + .5, y + yOffset + .99, z + .5);
                    update(blockLoc);
                    world.playEffect(blockLoc, Effect.FLYING_GLYPH, 1 );
                }
            }
        }

        for(int xOffset = range * -1; xOffset <= range; xOffset++) {
            for(int zOffset = range * -1; zOffset <= range; zOffset++) {
                world.playEffect(clickedBlock.getLocation().clone().add(xOffset + .5, 1, zOffset + .5), Effect.SPLASH, 1);
            }
        }
        event.setCancelled(true);
        return true;
    }
    
    float getMultiplier(Player player) {
        return 1 + getSkillLevel(player) * skillMultiplier;
    }

    int getRange(Player player) {
        return Math.min(1 + (int)(getSkillLevel(player)/100), 4); 
    }

    int getSkillLevel(Player player) {
        return 0;
    }

    //schedules a "random tick" block update
    //see https://minecraft.gamepedia.com/Tick#Block_tick
    static void update(Location loc){
        net.minecraft.server.v1_10_R1.World mcWorld = ((org.bukkit.craftbukkit.v1_10_R1.CraftWorld) loc.getWorld()).getHandle();
        BlockPosition blockPos = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        mcWorld.a(blockPos, CraftMagicNumbers.getBlock(loc.getBlock()), 1);
    }
}
