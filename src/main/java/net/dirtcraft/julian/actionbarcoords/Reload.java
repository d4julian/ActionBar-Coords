package net.dirtcraft.julian.actionbarcoords;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Reload implements CommandExecutor {

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&dHey! &6I'm going to reload ActionBar Coords"));
        ConfigManager.load();
        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&aActionBar Coords was reloaded successfully!"));
        return CommandResult.success();
    }

}
