package com.jonahseguin.godcomplex.alpha.player.internal;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.jonahseguin.godcomplex.alpha.Alpha;
import com.jonahseguin.godcomplex.alpha.player.AlphaPlayer;
import com.jonahseguin.godcomplex.alpha.player.PlayerService;
import com.jonahseguin.godcomplex.alpha.player.listener.PlayerListener;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class RuntimePlayerService implements PlayerService, Listener {

    private final Injector injector;
    private final Alpha alpha;
    private boolean running = false;
    private final Map<UUID, AlphaPlayer> players = new HashMap<>();
    private PlayerListener listener = null;

    @Inject
    public RuntimePlayerService(Injector injector, Alpha alpha) {
        this.injector = injector;
        this.alpha = alpha;
    }

    @Nullable
    @Override
    public AlphaPlayer get(@Nonnull UUID uuid) {
        Preconditions.checkNotNull(uuid, "UUID cannot be null");
        Player player = alpha.getServer().getPlayer(uuid);
        if (player != null) {
            return get(player);
        }
        return null;
    }

    @Nonnull
    @Override
    public AlphaPlayer get(@Nonnull Player player) {
        Preconditions.checkNotNull(player, "Player cannot be null");
        return players.computeIfAbsent(player.getUniqueId(), u -> new AlphaPlayer(player));
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean start() {
        Preconditions.checkState(!running, "Player service is already running");
        alpha.getServer().getPluginManager().registerEvents(this, alpha);
        if (listener == null) {
            listener = injector.getInstance(PlayerListener.class);
        }
        alpha.getServer().getPluginManager().registerEvents(listener, alpha);
        running = true;
        return true;
    }

    @Override
    public boolean shutdown() {
        Preconditions.checkState(running, "Player service is not running");
        players.values().forEach(AlphaPlayer::cleanup);
        players.clear();
        HandlerList.unregisterAll(this);
        if (listener != null) {
            HandlerList.unregisterAll(listener);
            listener = null;
        }
        running = false;
        return true;
    }
}
