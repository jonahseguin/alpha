package com.jonahseguin.godcomplex.alpha.cellphone;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.jonahseguin.godcomplex.alpha.cellphone.internal.RuntimeCellphone;
import com.jonahseguin.godcomplex.alpha.cellphone.internal.RuntimeCellphoneService;

public class CellphoneModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CellphoneService.class).to(RuntimeCellphoneService.class).in(Singleton.class);
        bind(Cellphone.class).to(RuntimeCellphone.class);
    }
}
