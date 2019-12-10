package com.jonahseguin.godcomplex.alpha.player;

import com.jonahseguin.godcomplex.alpha.util.Service;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public interface PlayerService extends Service {

    /**
     * Get or create an {@link AlphaPlayer} instance by {@link UUID}
     * @param uuid Player's UUID
     * @return the AlphaPlayer instance
     */
    @Nullable
    AlphaPlayer get(@Nonnull UUID uuid);

    /**
     * Get or create an {@link AlphaPlayer} instance by {@link Player}
     * @param player the Player
     * @return the AlphaPlayer instance
     */
    @Nonnull
    AlphaPlayer get(@Nonnull Player player);

}
