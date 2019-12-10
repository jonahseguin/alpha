package com.jonahseguin.godcomplex.alpha.cellphone.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jonahseguin.godcomplex.alpha.Alpha;
import com.jonahseguin.godcomplex.alpha.cellphone.Cellphone;
import com.jonahseguin.godcomplex.alpha.cellphone.CellphoneService;
import com.jonahseguin.godcomplex.alpha.cellphone.internal.RuntimeCellphone;
import com.jonahseguin.godcomplex.alpha.player.AlphaPlayer;
import com.jonahseguin.godcomplex.alpha.player.PlayerService;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;

@Singleton
public class CellphoneListener implements Listener {

    private final Alpha alpha;
    private final PlayerService playerService;
    private final CellphoneService cellphoneService;

    @Inject
    public CellphoneListener(Alpha alpha, PlayerService playerService, CellphoneService cellphoneService) {
        this.alpha = alpha;
        this.playerService = playerService;
        this.cellphoneService = cellphoneService;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCellphoneSMS(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        AlphaPlayer alphaPlayer = playerService.get(player);
        if (alphaPlayer.isMessagingTarget()) {
            event.setCancelled(true);
            String name = event.getMessage();
            Player target = alpha.getServer().getPlayer(name);
            if (target != null && target.isOnline()) {
                alphaPlayer.setMessagingTarget(false);
                if (alphaPlayer.getCellphone() == null) {
                    cellphoneService.get(player);
                }
                alphaPlayer.getCellphone().sendMessage(target);
            }
            else {
                player.sendMessage(ChatColor.RED + "Couldn't find a player by name '" + name + "'.  Try again or use '/cellphone cancel' to cancel.");
            }
        }
        else if (alphaPlayer.getMessaging() != null) {
            event.setCancelled(true);
            Player target = alphaPlayer.getMessaging();
            if (target != null && target.isOnline()) {
                player.sendMessage(ChatColor.GREEN + "Your SMS to " + target.getName() + " has been sent.");
                target.sendMessage(ChatColor.GOLD + "SMS from " + player.getName() + ": " + ChatColor.RESET + event.getMessage());
            }
            else {
                player.sendMessage(ChatColor.RED + target.getName() + " isn't online anymore.  Your SMS was not sent.");
            }
            alphaPlayer.setMessaging(null);
        }
    }

    @EventHandler
    public void onCellphoneTap(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            AlphaPlayer alphaPlayer = playerService.get(player);
            if (!alphaPlayer.hasCellphone()) {
                cellphoneService.get(player);
            }
            Cellphone cellphone = alphaPlayer.getCellphone();
            Inventory inv = alphaPlayer.getCellphone().getInventory();
            if (inv != null) {
                if (inv.equals(event.getClickedInventory())) {
                    event.setCancelled(true);
                    if (event.getCurrentItem() != null) {
                        Material click = event.getCurrentItem().getType();
                        if (click.equals(RuntimeCellphone.HOME)) {
                            cellphone.home();
                        }
                        else if (click.equals(RuntimeCellphone.CONTACTS)) {
                            cellphone.contacts();
                        }
                        else if (click.equals(RuntimeCellphone.MSG)) {
                            alphaPlayer.setMessagingTarget(true);
                            player.sendMessage(ChatColor.GOLD + "Who do you want to send an SMS to?  Type their name and hit enter.");
                            alphaPlayer.getPlayer().sendMessage(ChatColor.GRAY + "Use '/cellphone cancel' at any time to cancel.");
                            player.closeInventory();
                        }
                    }
                }
            }
        }
    }

}
