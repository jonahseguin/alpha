package com.jonahseguin.godcomplex.alpha.cellphone;

import com.jonahseguin.godcomplex.alpha.util.Service;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public interface CellphoneService extends Service {

    /**
     * Get or create a cellphone for a player
     * @param player Player
     * @return an {@link Cellphone} instance
     */
    @Nonnull
    Cellphone get(@Nonnull Player player);

}
