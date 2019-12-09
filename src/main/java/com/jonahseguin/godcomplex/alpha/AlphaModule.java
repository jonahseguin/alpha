package com.jonahseguin.godcomplex.alpha;

import com.google.inject.AbstractModule;

public class AlphaModule extends AbstractModule {

    private final Alpha alpha;

    public AlphaModule(Alpha alpha) {
        this.alpha = alpha;
    }

    @Override
    protected void configure() {
        bind(Alpha.class).toInstance(alpha);
    }
}
