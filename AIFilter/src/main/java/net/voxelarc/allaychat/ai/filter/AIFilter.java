package net.voxelarc.allaychat.ai.filter;

import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.ai.AIModule;
import net.voxelarc.allaychat.ai.PlayerMessage;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class AIFilter implements ChatFilter {

    private final AIModule module;

    private String apiKey;
    private String model;

    @Override
    public void onEnable() {
        apiKey = module.getConfig().getString("ai.api-key");
        model = module.getConfig().getString("ai.model");
    }

    @Override
    public boolean checkMessage(Player player, String message) {
        module.getMessagesToBeSent().add(new PlayerMessage(player.getName(), message));
        return false;
    }

}
