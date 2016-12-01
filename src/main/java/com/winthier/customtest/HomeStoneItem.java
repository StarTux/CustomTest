// package com.winthier.customtest;

// import com.winthier.custom.item.AbstractItem;
// import com.winthier.custom.item.ItemContext;
// import com.winthier.custom.item.ItemUtil;
// import com.winthier.custom.util.Msg;
// import java.util.HashMap;
// import java.util.Map;
// import lombok.Getter;
// import org.bukkit.Bukkit;
// import org.bukkit.Location;
// import org.bukkit.Material;
// import org.bukkit.World;
// import org.bukkit.enchantments.Enchantment;
// import org.bukkit.entity.Player;
// import org.bukkit.event.Event;
// import org.bukkit.event.player.PlayerInteractEvent;
// import org.bukkit.inventory.ItemStack;

// @Getter
// public class HomeStoneItem extends AbstractItem {
//     String id = "HomeStone";
//     String displayName = "Home Stone";
//     Material material = Material.ENDER_PEARL;
//     String description = "Right click with this item to make it save your current location.";
//     Map<Enchantment, Integer> enchantments = ItemUtil.getDefaultEnchantments();

//     @Override
//     public boolean handleEvent(Event event, ItemContext itemContext) {
//         if (event instanceof PlayerInteractEvent) {
//             onPlayerInteract((PlayerInteractEvent)event, itemContext);
//             return true;
//         }
//         return false;
//     }

//     void onPlayerInteract(PlayerInteractEvent event, ItemContext itemContext) {
//         event.setCancelled(true);
//         Player player = event.getPlayer();
//         if (itemContext.getJson().containsKey("home")) {
//             @SuppressWarnings("unchecked")
//             Map<String, Object> map = (Map<String, Object>)itemContext.getJson().get("home");
//             @SuppressWarnings("unchecked")
//             String worldName = (String)map.get("world");
//             World world = Bukkit.getServer().getWorld(worldName);
//             if (world == null) return;
//             @SuppressWarnings("unchecked")
//             int x = ((Number)map.get("x")).intValue();
//             @SuppressWarnings("unchecked")
//             int y = ((Number)map.get("y")).intValue();
//             @SuppressWarnings("unchecked")
//             int z = ((Number)map.get("z")).intValue();
//             Location location = world.getBlockAt(x, y, z).getLocation().add(0.5, 0.0, 0.5);
//             player.teleport(location);
//             Msg.send(player, "The Home Stone takes you to its home location.");
//         } else {
//             Location loc = player.getLocation();
//             Map<String, Object> json = itemContext.getJson();
//             Map<String, Object> map = new HashMap<>();
//             json.put("home", map);
//             map.put("world", loc.getWorld().getName());
//             map.put("x", loc.getBlockX());
//             map.put("y", loc.getBlockY());
//             map.put("z", loc.getBlockZ());
//             ItemStack replaceItem = ItemUtil.updateJson(itemContext.getItemStack(), json);
//             replaceItem.setType(Material.EYE_OF_ENDER);
//             itemContext.setReplaceItem(replaceItem);
//             Msg.send(player, "The Home Rock stores your current location.");
//         }
//     }
// }
