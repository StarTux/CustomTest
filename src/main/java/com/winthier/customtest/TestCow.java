package com.winthier.customtest;

import com.winthier.custom.entity.CustomEntity;
import com.winthier.custom.entity.EntityContext;
import com.winthier.custom.entity.EntityWatcher;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@Getter
public class TestCow implements CustomEntity {
    private final String customId = "test:cow";

    @Override
    public Entity spawnEntity(Location loc) {
        Cow cow = loc.getWorld().spawn(loc, Cow.class);
        cow.setCustomName("Test Cow");
        return cow;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event, EntityContext context) {
        System.out.println("HERE");
        event.setCancelled(true);
        if (context.getPosition() != EntityContext.Position.ENTITY) return;
        Entity entity = event.getEntity();
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1.0f, 1.0f);
    }

    @Override
    public void entityWasDiscovered(EntityWatcher entityWatcher) {
        System.out.println("Test Cow reporting for duty. Moo!");
    }

    @Override
    public void entityWillUnload(EntityWatcher entityWatcher) {
        System.out.println("Test Cow over and out!");
    }
}
