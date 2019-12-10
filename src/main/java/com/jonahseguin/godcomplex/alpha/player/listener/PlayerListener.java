package com.jonahseguin.godcomplex.alpha.player.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jonahseguin.godcomplex.alpha.player.PlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Singleton
public class PlayerListener implements Listener {

    private final PlayerService playerService;

    @Inject
    public PlayerListener(PlayerService playerService) {
        this.playerService = playerService;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onAsyncLogin(AsyncPlayerPreLoginEvent event) {
        playerService.get(event.getUniqueId()); // Creates the instance
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerService.get(event.getPlayer()).cleanup();
    }

}
