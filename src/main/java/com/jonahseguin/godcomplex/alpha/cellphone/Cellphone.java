package com.jonahseguin.godcomplex.alpha.cellphone;

import com.jonahseguin.godcomplex.alpha.player.AlphaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public interface Cellphone {

    void init(@Nonnull AlphaPlayer alphaPlayer);

    void home();

    void sendMessage(Player target);

    void contacts();

    Inventory getInventory();

}
