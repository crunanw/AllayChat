package net.voxelarc.allaychat.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

@RequiredArgsConstructor
@Command(value = "allay", alias = {"allaychat"})
public class InventoryCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @SubCommand("inventory")
    @Permission("allaychat.command.inventory")
    public void onCommand(Player player, String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Inventory inventory = plugin.getChatManager().getInventory(uuid);
            if (inventory == null) {
                ChatUtils.sendMessage(player, ChatUtils.format(plugin.getMessagesConfig().getString("messages.inventory-not-found")));
                return;
            }

            player.openInventory(inventory);
        } catch (IllegalArgumentException e) {
            ChatUtils.sendMessage(player, ChatUtils.format(plugin.getMessagesConfig().getString("messages.inventory-not-found")));
        }
    }

}
