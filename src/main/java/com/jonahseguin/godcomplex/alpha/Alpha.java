package com.jonahseguin.godcomplex.alpha;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import org.bukkit.plugin.java.JavaPlugin;

public class Alpha extends JavaPlugin {

    private Injector injector;

    @Override
    public void onEnable() {
        injector = Guice.createInjector(Stage.PRODUCTION, new AlphaModule(this));
        injector.injectMembers(this);
    }

    @Override
    public void onDisable() {

    }

}
