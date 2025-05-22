package net.voxelarc.allaychat.api;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import net.voxelarc.allaychat.api.chat.ChatManager;
import net.voxelarc.allaychat.api.database.Database;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.api.module.ModuleManager;
import net.voxelarc.allaychat.api.player.PlayerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public abstract class AllayChat extends JavaPlugin {

    public abstract PlayerManager getPlayerManager();

    public abstract void setPlayerManager(PlayerManager playerManager);

    public abstract void setChatManager(ChatManager chatManager);

    public abstract ChatManager getChatManager();

    public abstract Collection<ChatFilter> getFilters();

    public abstract void setFilters(Collection<ChatFilter> filters);

    public abstract void addFilter(ChatFilter filter);

    public abstract void unregisterCommand(String command);

    public abstract BukkitCommandManager<CommandSender> getCommandManager();

    public abstract ModuleManager getModuleManager();

    public abstract Database getDatabase();

}
