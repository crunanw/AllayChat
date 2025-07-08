package net.voxelarc.allaychat.api.event;

import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DetectionEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    @Getter private final String playerName;
    @Getter private final float point;
    @Getter private final Category category;
    @Getter private final String message;

    public DetectionEvent(String playerName, float point, Category category, String message) {
        super(true);

        this.playerName = playerName;
        this.point = point;
        this.category = category;
        this.message = message;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum Category {
        VIOLENT,
        SELF_HARM,
        HARASSMENT,
        HATE,
        SEXUAL,
        OTHER
    }

}
