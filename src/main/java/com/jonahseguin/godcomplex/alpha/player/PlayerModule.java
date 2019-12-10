package com.jonahseguin.godcomplex.alpha.player;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.jonahseguin.godcomplex.alpha.player.internal.RuntimePlayerService;

public class PlayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlayerService.class).to(RuntimePlayerService.class).in(Singleton.class);
    }
}
