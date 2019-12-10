package com.jonahseguin.godcomplex.alpha.cellphone.internal;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.jonahseguin.godcomplex.alpha.Alpha;
import com.jonahseguin.godcomplex.alpha.cellphone.Cellphone;
import com.jonahseguin.godcomplex.alpha.player.AlphaPlayer;
import com.jonahseguin.godcomplex.alpha.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class RuntimeCellphone implements Cellphone {

    public static final Material HOME = Material.ARROW;
    public static final Material MSG = Material.PAPER;
    public static final Material CONTACTS = Material.BOOK;

    private final Alpha alpha;
    private AlphaPlayer alphaPlayer = null;
    private Inventory inventory = null;

    @Inject
    public RuntimeCellphone(Alpha alpha) {
        this.alpha = alpha;
    }

    @Override
    public void init(@Nonnull AlphaPlayer alphaPlayer) {
        Preconditions.checkNotNull(alphaPlayer, "AlphaPlayer cannot be null");
        this.alphaPlayer = alphaPlayer;
        inventory = Bukkit.createInventory(null, 27, alphaPlayer.getPlayer().getName() + "'s Cell Phone");
    }

    @Override
    public void home() {
        inventory.clear();
        inventory.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).head(alphaPlayer.getPlayer().getUniqueId()).amount(1).name(ChatColor.GOLD + alphaPlayer.getPlayer().getName()).build());
        inventory.setItem(11, new ItemBuilder(MSG).amount(1).name(ChatColor.GREEN + "Send a message").lore(ChatColor.GRAY + "Send a message to an online player").build());
        inventory.setItem(15, new ItemBuilder(CONTACTS).amount(1).name(ChatColor.GREEN + "Contacts").lore(ChatColor.GRAY + "View your contacts").build());
        alphaPlayer.getPlayer().openInventory(inventory);
    }

    @Override
    public void sendMessage(Player target) {
        if (target != null) {
            alphaPlayer.setMessaging(target);
            alphaPlayer.getPlayer().sendMessage(ChatColor.GOLD + "Type your message to " + target.getName() + ".  Hit enter when done.");
            alphaPlayer.getPlayer().sendMessage(ChatColor.GRAY + "Use '/cellphone cancel' at any time to cancel.");
        }
        else {
            alphaPlayer.getPlayer().sendMessage(ChatColor.RED + "Couldn't find that player.");
            alphaPlayer.setMessaging(null);
        }
    }

    @Override
    public void contacts() {
        inventory.clear();
        inventory.setItem(4, new ItemBuilder(CONTACTS).amount(1).name(ChatColor.GREEN + "Contacts").lore(ChatColor.GRAY + "Your contacts are displayed here.").build());
        int i = 11;
        for (Player online : alpha.getServer().getOnlinePlayers()) {
            inventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).head(online.getUniqueId()).name(ChatColor.GOLD + online.getName()).lore(ChatColor.GREEN + "Online").amount(1).build());
            i++;
        }
        inventory.setItem(26, new ItemBuilder(HOME).amount(1).name(ChatColor.RED + "Back to Home Screen").build());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
