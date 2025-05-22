package net.voxelarc.allaychat.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.logging.Level;

@RequiredArgsConstructor
@Command(value = "allay", alias = {"allaychat"})
public class InventoryCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @SubCommand("inventory")
    @Permission("allaychat.command.inventory")
    public void onCommand(Player player, String id) {
        try {
            UUID uuid = UUID.fromString(id);
            plugin.getChatManager().getInventory(uuid).whenComplete((inventory, throwable) -> {
                if (throwable != null) {
                    plugin.getLogger().log(
                            Level.SEVERE,
                            "Failed to load inventory for " + uuid,
                            throwable
                    );
                    ChatUtils.sendMessage(player, ChatUtils.format(plugin.getMessagesConfig().getString("messages.inventory-load-failed")));
                    return;
                }

                if (inventory == null) {
                    ChatUtils.sendMessage(player, ChatUtils.format(plugin.getMessagesConfig().getString("messages.inventory-not-found")));
                    return;
                }

                Bukkit.getScheduler().runTask(plugin, () -> {
                    player.openInventory(inventory);
                });
            });
        } catch (IllegalArgumentException e) {
            ChatUtils.sendMessage(player, ChatUtils.format(plugin.getMessagesConfig().getString("messages.inventory-not-found")));
        }
    }

}
