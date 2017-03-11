package com.winthier.customtest;

import com.winthier.custom.CustomPlugin;
import com.winthier.custom.block.BlockContext;
import com.winthier.custom.block.BlockWatcher;
import com.winthier.custom.block.CustomBlock;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

@Getter
public class TestBlock implements CustomBlock {
    private final String customId = "test:web";

    @Override
    public void setBlock(Block block) {
        block.setType(Material.WEB);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event, BlockContext context) {
        event.setCancelled(true);
        Block block = event.getBlock();
        block.getWorld().playSound(block.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_CAT_AMBIENT, 1.0f, 1.0f);
        System.out.println(customId + CustomPlugin.getInstance().getBlockManager().loadBlockData(context.getBlockWatcher()));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void blockWasLoaded(BlockWatcher blockWatcher) {
        System.out.println("Load Test Block " + blockWatcher.getBlock());
    }

    @Override
    public void blockWasCreated(BlockWatcher blockWatcher) {
        CustomPlugin.getInstance().getBlockManager().saveBlockData(blockWatcher, "This is a test!\nThis is a test!\nThis is a test!");
    }
}
