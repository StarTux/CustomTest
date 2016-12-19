package com.winthier.customtest;

import com.winthier.custom.CustomConfig;
import com.winthier.custom.block.*;
import lombok.*;
import org.bukkit.Material;
import org.bukkit.block.Block;

@Getter
public class TestBlock implements CustomBlock {
    final String customId = "Test";

    @Override
    public void setBlock(Block block, CustomConfig config) {
        block.setType(Material.WEB);
    }

    @Override
    public BlockWatcher createBlockWatcher(Block block, CustomConfig config) {
        return null;
    }
}
