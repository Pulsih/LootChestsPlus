package me.pulsi_.lootchestsplus.commands;

import me.pulsi_.lootchestsplus.commands.list.EditCmd;
import me.pulsi_.lootchestsplus.commands.list.ListCmd;
import me.pulsi_.lootchestsplus.commands.list.ReloadCmd;

public class CmdRegisterer {

    public void registerCmds() {
        new EditCmd("edit").register();
        new ListCmd("list").register();
        new ReloadCmd("reload").register();
    }
}