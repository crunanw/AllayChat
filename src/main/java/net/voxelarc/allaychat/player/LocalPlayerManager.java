package net.voxelarc.allaychat.player;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.voxelarc.allaychat.api.player.PlayerManager;
import net.voxelarc.allaychat.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class LocalPlayerManager implements PlayerManager {

    @Override
    public List<String> getAllPlayers() {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .toList();
    }

    @Override
    public void playSound(String playerName, Sound sound) {
        Player player = Bukkit.getPlayerExact(playerName);
        if (player != null) {
            player.playSound(sound);
        }
    }

    @Override
    public void showTitle(String playerName, Title title) {
        Player player = Bukkit.getPlayerExact(playerName);
        if (player != null) {
            player.showTitle(title);
        }
    }

    @Override
    public void showActionBar(String playerName, Component component) {
        Player player = Bukkit.getPlayerExact(playerName);
        if (player != null) {
            player.sendActionBar(component);
        }
    }

    @Override
    public void sendMessage(String playerName, Component component) {
        Player player = Bukkit.getPlayerExact(playerName);
        if (player != null) {
            ChatUtils.sendMessage(player, component);
        }
    }

}
