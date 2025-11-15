package net.voxelarc.allaychat.player;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.player.PlayerManager;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LocalPlayerManager implements PlayerManager {

    private final AllayChatPlugin plugin;

    @Override
    public Set<String> getAllPlayers() {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public void sendMessage(String playerName, Component component) {
        Player player = Bukkit.getPlayerExact(playerName);
        if (player != null) {
            ChatUtils.sendMessage(player, component);
        }
    }

    @Override
    public void broadcast(Component component) {
        plugin.getComponentLogger().info(component);
        Bukkit.broadcast(component);
    }

    @Override
    public void broadcast(Component component, String permission) {
        plugin.getComponentLogger().info(component);
        Bukkit.broadcast(component, permission);
    }

}
