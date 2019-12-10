package com.jonahseguin.godcomplex.alpha;

import com.google.inject.AbstractModule;
import com.jonahseguin.godcomplex.alpha.cellphone.CellphoneModule;
import com.jonahseguin.godcomplex.alpha.player.PlayerModule;

public class AlphaModule extends AbstractModule {

    private final Alpha alpha;

    public AlphaModule(Alpha alpha) {
        this.alpha = alpha;
    }

    @Override
    protected void configure() {
        bind(Alpha.class).toInstance(alpha);
        install(new PlayerModule());
        install(new CellphoneModule());
    }
}
