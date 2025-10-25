package net.voxelarc.allaychat.api.player;

import net.kyori.adventure.text.Component;

import java.util.Set;

/**
 * PlayerManager is an interface that provides methods to manage player interactions.
 * It includes methods to get all players, play sounds, show titles, action bars, and send messages.
 * Whole point of this interface is to do player actions without having to deal with
 * Bukkit API directly. So we can interact with players even if they are on a different server.
 */
public interface PlayerManager {

    Set<String> getAllPlayers();

    void sendMessage(String playerName, Component component);

    void broadcast(Component component);

    void broadcast(Component component, String permission);

}
