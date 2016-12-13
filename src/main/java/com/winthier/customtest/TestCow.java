package com.winthier.customtest;

import com.winthier.custom.CustomConfig;
import com.winthier.custom.entity.CustomEntity;
import com.winthier.custom.entity.DefaultCustomEntity;
import com.winthier.custom.entity.DefaultEntityWatcher;
import com.winthier.custom.entity.EntityContext;
import com.winthier.custom.entity.EntityWatcher;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Cow;
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
        return loc.getWorld().spawn(loc, Cow.class);
    }

    @Override
    public EntityWatcher createEntityWatcher(final Entity entity, final CustomConfig config) {
        Cow cow = (Cow)entity;
        cow.setCustomName("Test");
        return new TestCowWatcher(entity, this, config);
    }

    
    public static class TestCowWatcher extends DefaultEntityWatcher {
        TestCowWatcher(Entity entity, CustomEntity customEntity, CustomConfig config) {
            super(entity, customEntity, config);
        }
        
        @EventHandler
        public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
            event.setCancelled(true);
            EntityContext context = EntityContext.of(event);
            if (context.getPosition() != EntityContext.Position.ENTITY) return;
            Entity entity = event.getEntity();
            entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1.0f, 1.0f);
        }

        @Override
        public void didDiscoverEntity() {
            System.out.println("Test Cow reporting for duty. Moo!");
        }

        @Override
        public void willUnloadEntity() {
            System.out.println("Test Cow over and out!");
        }
    };
}
