package net.voxelarc.allaychat.api.filter;

import org.bukkit.entity.Player;

public interface ChatFilter {

    void onEnable();

    /**
     * @param message the message to check
     * @return true if event should be cancelled, false otherwise
     */
    boolean checkMessage(Player player, String message);

}
