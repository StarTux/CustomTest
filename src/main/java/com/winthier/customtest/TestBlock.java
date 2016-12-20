package com.winthier.customtest;

import com.winthier.custom.CustomConfig;
import com.winthier.custom.block.*;
import lombok.*;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;

@Getter
public class TestBlock implements CustomBlock {
    final String customId = "Test";

    @Override
    public void setBlock(Block block, CustomConfig config) {
        block.setType(Material.WEB);
    }

    @Override
    public BlockWatcher createBlockWatcher(Block block, CustomConfig config) {
        return new TestBlockWatcher(block, this, config);
    }

    @Getter @RequiredArgsConstructor
    public static class TestBlockWatcher extends AbstractBlockWatcher {
        final Block block;
        final TestBlock customBlock;
        final CustomConfig customConfig;

        @Override
        public void didDiscoverBlock() {
            System.out.println("Discover Test Block " + block);
        }

        @EventHandler
        public void onBlockDamage(BlockDamageEvent event) {
            System.out.println(event.getEventName());
            event.setCancelled(true);
            Block block = event.getBlock();
            block.getWorld().playSound(block.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_CAT_AMBIENT, 1.0f, 1.0f);
        }
    }
}
