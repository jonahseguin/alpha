package com.jonahseguin.godcomplex.alpha.cellphone.internal;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.jonahseguin.godcomplex.alpha.Alpha;
import com.jonahseguin.godcomplex.alpha.cellphone.Cellphone;
import com.jonahseguin.godcomplex.alpha.cellphone.CellphoneService;
import com.jonahseguin.godcomplex.alpha.cellphone.listener.CellphoneListener;
import com.jonahseguin.godcomplex.alpha.player.AlphaPlayer;
import com.jonahseguin.godcomplex.alpha.player.PlayerService;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

@Singleton
public class RuntimeCellphoneService implements CellphoneService {

    private final Injector injector;
    private final Alpha alpha;
    private final PlayerService playerService;
    private boolean running = false;
    private CellphoneListener listener = null;

    @Inject
    public RuntimeCellphoneService(Injector injector, Alpha alpha, PlayerService playerService) {
        this.injector = injector;
        this.alpha = alpha;
        this.playerService = playerService;
    }

    @Nonnull
    @Override
    public Cellphone get(@Nonnull Player player) {
        Preconditions.checkNotNull(player, "Player cannot be null");
        AlphaPlayer alphaPlayer = playerService.get(player);
        if (alphaPlayer.hasCellphone()) {
            return alphaPlayer.getCellphone();
        }
        else {
            Cellphone cellphone = injector.getInstance(Cellphone.class);
            cellphone.init(alphaPlayer);
            alphaPlayer.setCellphone(cellphone);
            return cellphone;
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean start() {
        Preconditions.checkState(!running, "Cellphone Service is already running");
        if (listener == null) {
            listener = injector.getInstance(CellphoneListener.class);
        }
        alpha.getServer().getPluginManager().registerEvents(listener, alpha);
        running = true;
        return true;
    }

    @Override
    public boolean shutdown() {
        Preconditions.checkState(running, "Cellphone Service is not running");
        if (listener != null) {
            HandlerList.unregisterAll(listener);
            listener = null;
        }
        running = false;
        return true;
    }
}
