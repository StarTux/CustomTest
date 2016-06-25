package com.winthier.customtest;

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

@Getter
public class TestRecipeItem extends AbstractItem {
    Material material = Material.EYE_OF_ENDER;
    String id = "TestRecipeItem";
    String displayName = "Test Recipe Item";
    String description = "This item was designed to be crafted from a couple of Test Items so the custom recipe system can be tested.";
}
