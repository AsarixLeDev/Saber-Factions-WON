package com.massivecraft.factions.cmd.econ;

import com.massivecraft.factions.Conf;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.CommandRequirements;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.util.Logger;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.entity.Player;

public class CmdMoneyGiveF extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdMoneyGiveF() {
        this.aliases.add("ecogivef");

        this.requiredArgs.add("amount");
        this.requiredArgs.add("faction");

        this.requirements = new CommandRequirements.Builder(Permission.MONEY_F2F).build();
    }

    @Override
    public void perform(CommandContext context) {
        double amount = context.argAsDouble(0, 0d);

        if (amount <= 0) {
            return;
        }

        Faction to = context.argAsFaction(1);
        if (to == null) {
            return;
        }

        boolean success = Econ.depositFactionBalance(to, amount);

        if (success && Conf.logMoneyTransactions) {
            String name = context.sender instanceof Player ? context.fPlayer.getName() : context.sender.getName();
            Logger.printArgs(TL.COMMAND_MONEYTRANSFERFF_TRANSFER.toString(), Logger.PrefixType.DEFAULT, name, Econ.moneyString(amount), "Console", to.describeTo(null));
        }
    }


    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MONEYTRANSFERFF_DESCRIPTION;
    }
}
