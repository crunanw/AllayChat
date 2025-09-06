package net.voxelarc.allaychat.listener;

import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.user.ChatUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

@RequiredArgsConstructor
public class ConnectionListener implements Listener {

    private final AllayChatPlugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Delay the loading of user data to ensure that the player is fully connected
        plugin.getScheduler().runLaterAsync(() -> {
            plugin.getDatabase().loadPlayerAsync(player.getUniqueId()).whenComplete((user, throwable) -> {
                if (throwable != null) {
                    // We'll add a dummy user in case of a failure to load the user data
                    plugin.getUserManager().addUser(new ChatUser(player.getUniqueId()));
                    plugin.getLogger().log(Level.SEVERE, "Failed to load user data for " + player.getName(), throwable);
                    return;
                }

                if (!player.hasPermission("allaychat.staffchat")) {
                    user.setStaffEnabled(false);
                }

                if (!player.hasPermission("allaychat.spy")) {
                    user.setSpyEnabled(false);
                }

                plugin.getUserManager().addUser(user);
            });
        }, 20L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ChatUser chatUser = plugin.getUserManager().getUser(player.getUniqueId());
        if (chatUser == null) return;

        // Remove the user from the user manager
        plugin.getUserManager().removeUser(player.getUniqueId());

        // Save the user data when the player quits
        // This is done asynchronously to avoid blocking the main thread
        plugin.getDatabase().savePlayerAsync(chatUser).whenComplete((user, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().log(Level.SEVERE, "Failed to save user data for " + player.getName(), throwable);
            }
        });
    }

}
