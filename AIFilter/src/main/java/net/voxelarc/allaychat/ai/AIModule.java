package net.voxelarc.allaychat.ai;

import lombok.Getter;
import net.voxelarc.allaychat.ai.filter.AIFilter;
import net.voxelarc.allaychat.api.module.Module;

import java.util.HashSet;
import java.util.Set;

@Getter
public class AIModule extends Module {

    private final Set<PlayerMessage> messagesToBeSent = new HashSet<>();

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.getPlugin().addFilter(new AIFilter(this));
    }

    @Override
    public void onDisable() {

    }

}
