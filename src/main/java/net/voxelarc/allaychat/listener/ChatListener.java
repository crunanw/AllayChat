package net.voxelarc.allaychat.listener;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.user.ChatUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class ChatListener implements Listener {

    private final AllayChatPlugin plugin;

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onChat(AsyncChatEvent event) {
        String message = PlainTextComponentSerializer.plainText().serialize(event.message());
        event.setCancelled(plugin.getChatManager().handleMessage(event.getPlayer(), message));
        event.renderer(ChatRenderer.viewerUnaware(plugin.getChatManager().getChatRenderer()));

        event.viewers().removeIf(viewer -> {
            if (!(viewer instanceof Player player)) return false;

            ChatUser user = plugin.getUserManager().getUser(player.getUniqueId());
            // Data not loaded yet
            if (user == null) return false;

            return user.getIgnoredPlayers().contains(event.getPlayer().getName());
        });
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChatStaff(AsyncChatEvent event) {
        Player player = event.getPlayer();
        ChatUser chatUser = plugin.getUserManager().getUser(player.getUniqueId());
        if (chatUser == null) return;

        if (chatUser.isStaffEnabled()) {
            if (!player.hasPermission("allaychat.staffchat")) {
                chatUser.setStaffEnabled(false);
                return;
            }

            event.setCancelled(true);

            String message = PlainTextComponentSerializer.plainText().serialize(event.message());
            plugin.getChatManager().handleStaffChatMessage(player, message);
        }
    }

}
