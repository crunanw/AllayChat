package net.voxelarc.allaychat.api.database;

import net.voxelarc.allaychat.api.user.ChatUser;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface Database {

    void onEnable();

    void onDisable();

    CompletableFuture<@NotNull ChatUser> loadPlayerAsync(UUID uniqueId);

    CompletableFuture<Boolean> savePlayerAsync(ChatUser user);

    void saveAllPlayers();

    Connection getConnection();

}
