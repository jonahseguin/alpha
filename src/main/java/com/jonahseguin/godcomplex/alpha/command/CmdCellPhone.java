package com.jonahseguin.godcomplex.alpha.command;

import com.google.inject.Inject;
import com.jonahseguin.godcomplex.alpha.cellphone.CellphoneService;
import com.jonahseguin.godcomplex.alpha.player.AlphaPlayer;
import com.jonahseguin.godcomplex.alpha.player.PlayerService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdCellPhone implements CommandExecutor {

    public static final String CMD_NAME = "cell";

    private final PlayerService playerService;
    private final CellphoneService cellphoneService;

    @Inject
    public CmdCellPhone(PlayerService playerService, CellphoneService cellphoneService) {
        this.playerService = playerService;
        this.cellphoneService = cellphoneService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(CMD_NAME)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                AlphaPlayer alphaPlayer = playerService.get(player);
                if (alphaPlayer.getCellphone() == null) {
                    cellphoneService.get(player); // will also set their cellPhone property
                }
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("cancel")) {
                        alphaPlayer.setMessagingTarget(false);
                        alphaPlayer.setMessaging(null);
                        player.sendMessage(ChatColor.RED + "SMS messaging cancelled.");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Unknown argument.  Use /cellphone cancel to cancel SMS messaging.");
                    }
                }
                else {
                    alphaPlayer.getCellphone().home();
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "This is a player-only command.");
            }
            return true;
        }
        return false;
    }
}
