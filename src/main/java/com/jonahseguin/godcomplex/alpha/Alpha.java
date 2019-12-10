package com.jonahseguin.godcomplex.alpha;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.jonahseguin.godcomplex.alpha.cellphone.CellphoneService;
import com.jonahseguin.godcomplex.alpha.command.CmdCellPhone;
import com.jonahseguin.godcomplex.alpha.player.PlayerService;
import com.jonahseguin.godcomplex.alpha.util.ServiceHelper;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Alpha extends JavaPlugin {

    private Injector injector;
    @Inject private ServiceHelper service;
    @Inject private PlayerService playerService;
    @Inject private CellphoneService cellphoneService;

    @Override
    public void onEnable() {
        injector = Guice.createInjector(Stage.PRODUCTION, new AlphaModule(this));
        injector.injectMembers(this);

        if (!service.start(playerService)) return;
        if (!service.start(cellphoneService)) return;

        PluginCommand cellphoneCmd = getCommand("cell");
        if (cellphoneCmd != null) {
            cellphoneCmd.setExecutor(injector.getInstance(CmdCellPhone.class));
        }
        else {
            getLogger().severe("Failed to register cell-phone command.  Was not defined.");
        }
    }

    @Override
    public void onDisable() {
        service.shutdown(cellphoneService);
        service.shutdown(playerService);
    }

}
