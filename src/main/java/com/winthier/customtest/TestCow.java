package com.winthier.customtest;

import com.winthier.custom.CustomConfig;
import com.winthier.custom.entity.DefaultCustomEntity;
import com.winthier.custom.event.EntityEventContext;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TestCow extends DefaultCustomEntity {
    TestCow() {
        super("TestCow");
    }

    @Override
    public Entity spawnEntity(Location loc, CustomConfig config) {
        return loc.getWorld().spawnEntity(loc, EntityType.COW);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
        EntityEventContext context = EntityEventContext.of(event);
        if (context.getPosition() != EntityEventContext.Position.ENTITY) return;
        Entity entity = event.getEntity();
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1.0f, 1.0f);
    }
}
