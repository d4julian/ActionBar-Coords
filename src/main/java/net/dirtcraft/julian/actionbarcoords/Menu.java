package net.dirtcraft.julian.actionbarcoords;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.User;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

public class Menu implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException {
        Player player = (Player) source;

        if(player.hasPermission("actionbarcoords.enabled")) {

            enablePagination(player);

        } else if(!player.hasPermission("actionbarcoords.enabled")) {

            disabledPagination(player);

        }


        return CommandResult.success();
    }

    private void enablePagination(Player player) {
        String togglePadding = ConfigManager.getConfNode("Pagination", "padding").getString();
        Text msgtogglePadding = TextSerializers.FORMATTING_CODE.deserialize(togglePadding);
        String toggleTitle = ConfigManager.getConfNode("Toggle-Menu", "title").getString();
        String toggleContents = ConfigManager.getConfNode("Toggle-Menu", "message").getString();
        String toggleHover = ConfigManager.getConfNode("Toggle-Menu", "hover").getString();
        String lpserverContext = ConfigManager.getConfNode("LuckPerms", "server-context").getString();


        PaginationList.builder()
                .title(TextSerializers.FORMATTING_CODE.deserialize(toggleTitle
                        .replace("{toggle}", ConfigManager.getConfNode("Toggle-Menu", "enabled").getString())))
                .padding(msgtogglePadding)
                .contents(

                        Text.builder()
                                .append(Text.builder()
                                        .append(TextSerializers.FORMATTING_CODE.deserialize(
                                                toggleContents.replace("{toggle}",
                                                        ConfigManager.getConfNode("Toggle-Menu", "enabled").getString())
                                                        .replace("{playername}", player.getName())))
                                        .build())
                                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize(toggleHover.replace("{toggle}",
                                        ConfigManager.getConfNode("Toggle-Menu", "enabled").getString()))))
                                .onClick(TextActions.executeCallback(disable -> {

                                    Optional<User> user = LuckPerms.getApi().getUserSafe(player.getUniqueId());
                                    Node permission = LuckPerms.getApi().buildNode("actionbarcoords.enabled")
                                            .setValue(false)
                                            .setServer(lpserverContext)
                                            .build();

                                    user.get().setPermission(permission);
                                    LuckPerms.getApi().getUserManager().saveUser(user.get());

                                    disabledPagination(player);


                                }))
                                .build()

                )
                .build()
                .sendTo(player);


    }

    private void disabledPagination(Player player) {
        String togglePadding = ConfigManager.getConfNode("Pagination", "padding").getString();
        Text msgtogglePadding = TextSerializers.FORMATTING_CODE.deserialize(togglePadding);
        String toggleTitle = ConfigManager.getConfNode("Toggle-Menu", "title").getString();
        String toggleContents = ConfigManager.getConfNode("Toggle-Menu", "message").getString();
        String toggleHover = ConfigManager.getConfNode("Toggle-Menu", "hover").getString();
        String lpserverContext = ConfigManager.getConfNode("LuckPerms", "server-context").getString();

        PaginationList.builder()
                .title(TextSerializers.FORMATTING_CODE.deserialize(toggleTitle
                        .replace("{toggle}", ConfigManager.getConfNode("Toggle-Menu", "disabled").getString())))
                .padding(msgtogglePadding)
                .contents(

                        Text.builder()
                                .append(Text.builder()
                                        .append(TextSerializers.FORMATTING_CODE.deserialize(
                                                toggleContents.replace("{toggle}",
                                                        ConfigManager.getConfNode("Toggle-Menu", "disabled").getString())
                                                        .replace("{playername}", player.getName())))
                                        .build())
                                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize(toggleHover.replace("{toggle}",
                                        ConfigManager.getConfNode("Toggle-Menu", "disabled").getString()))))
                                .onClick(TextActions.executeCallback(enable->{

                                    Optional<User> user = LuckPerms.getApi().getUserSafe(player.getUniqueId());
                                    Node permission = LuckPerms.getApi().buildNode("actionbarcoords.enabled")
                                            .setValue(true)
                                            .setServer(lpserverContext)
                                            .build();

                                    user.get().setPermission(permission);
                                    LuckPerms.getApi().getUserManager().saveUser(user.get());

                                    enablePagination(player);

                                }))
                                .build()

                )
                .build()
                .sendTo(player);

    }

}
