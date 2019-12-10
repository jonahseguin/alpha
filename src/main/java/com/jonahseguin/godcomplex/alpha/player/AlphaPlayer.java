package com.jonahseguin.godcomplex.alpha.player;

import com.jonahseguin.godcomplex.alpha.cellphone.Cellphone;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class AlphaPlayer {

    private final Player player;
    private transient Cellphone cellphone = null;
    private transient Player messaging = null;
    private boolean messagingTarget = false;

    public AlphaPlayer(Player player) {
        this.player = player;
    }

    public boolean hasCellphone() {
        return cellphone != null;
    }

    public void cleanup() {
        messaging = null;
        messagingTarget = false;
    }

}
