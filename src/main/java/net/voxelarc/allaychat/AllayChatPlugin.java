package net.voxelarc.allaychat;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.bukkit.message.BukkitMessageKey;
import dev.triumphteam.cmd.core.exceptions.CommandRegistrationException;
import dev.triumphteam.cmd.core.message.MessageKey;
import dev.triumphteam.cmd.core.suggestion.SuggestionKey;
import lombok.Getter;
import lombok.Setter;
import net.voxelarc.allaychat.api.AllayChat;
import net.voxelarc.allaychat.api.chat.ChatManager;
import net.voxelarc.allaychat.api.database.Database;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.api.module.ModuleManager;
import net.voxelarc.allaychat.api.player.PlayerManager;
import net.voxelarc.allaychat.api.user.UserManager;
import net.voxelarc.allaychat.chat.LocalChatManager;
import net.voxelarc.allaychat.command.*;
import net.voxelarc.allaychat.config.YamlConfig;
import net.voxelarc.allaychat.database.impl.MySQLDatabase;
import net.voxelarc.allaychat.database.impl.SQLiteDatabase;
import net.voxelarc.allaychat.filter.*;
import net.voxelarc.allaychat.inventory.AllayInventoryHolder;
import net.voxelarc.allaychat.listener.ChatListener;
import net.voxelarc.allaychat.listener.ConnectionListener;
import net.voxelarc.allaychat.listener.InventoryListener;
import net.voxelarc.allaychat.player.LocalPlayerManager;
import net.voxelarc.allaychat.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Getter
@Setter
public final class AllayChatPlugin extends AllayChat {

    @Getter
    private static AllayChatPlugin instance;

    // can not be final due to module accessibility
    private PlayerManager playerManager = new LocalPlayerManager();
    private ChatManager chatManager = new LocalChatManager(this);
    private ModuleManager moduleManager = new ModuleManager(this);
    private UserManager userManager = new UserManager();
    private Database database = new SQLiteDatabase(this);

    private final YamlConfig config = new YamlConfig(this, "config.yml", true);
    private final YamlConfig filterConfig = new YamlConfig(this, "filter.yml", true);
    private final YamlConfig messagesConfig = new YamlConfig(this, "messages.yml", true);
    private final YamlConfig formatConfig = new YamlConfig(this, "format.yml", true);
    private final YamlConfig privateMessageConfig = new YamlConfig(this, "msg.yml", true);
    private final YamlConfig staffChatConfig = new YamlConfig(this, "staff-chat.yml", true);
    private final YamlConfig replacementConfig = new YamlConfig(this, "replacement.yml", true);

    private final Set<ChatFilter> filters = new HashSet<>();

    private BukkitCommandManager<CommandSender> commandManager;

    @Override
    public void onLoad() {
        instance = this;

        moduleManager.loadModules();
    }

    @Override
    public void onEnable() {
        config.create();
        filterConfig.create();
        messagesConfig.create();
        formatConfig.create();
        privateMessageConfig.create();
        staffChatConfig.create();
        replacementConfig.create();

        chatManager.onEnable();
        moduleManager.enableModules();

        if (this.config.getString("database.type", "sqlite").equalsIgnoreCase("sqlite"))
            database = new SQLiteDatabase(this);
        else
            database = new MySQLDatabase(this);

        database.onEnable();

        this.getServer().getPluginManager().registerEvents(new ConnectionListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        setupFilters();

        registerCommands();
    }

    @Override
    public void onDisable() {
        unregisterCommands();
        moduleManager.disableModules();
        database.onDisable();

        for (Player player : this.getServer().getOnlinePlayers()) {
            if (player.getOpenInventory().getTopInventory().getHolder() instanceof AllayInventoryHolder)
                player.closeInventory();
        }
    }

    private void setupFilters() {
        addFilter(new CapsFilter(this));
        addFilter(new CooldownFilter(this));
        addFilter(new DomainFilter(this));
        addFilter(new FloodFilter(this));
        addFilter(new IpFilter(this));
        addFilter(new PhoneFilter(this));
        addFilter(new RegexFilter(this));
        addFilter(new SimilarityFilter(this));

        for (ChatFilter filter : getFilters()) {
            filter.onEnable();
        }
    }

    @Override
    public void setFilters(Collection<ChatFilter> filters) {
        this.filters.clear();
        this.filters.addAll(filters);
    }

    @Override
    public void addFilter(ChatFilter filter) {
        this.filters.add(filter);
    }

    public void unregisterCommands() {
        List.of(
                "allay", "allaychat", "ignore", "spy", "msgspy", "msg",
                "pm", "tell", "whisper", "r", "reply", "respond", "staffchat", "sc"
        ).forEach(this::unregisterCommand);
    }

    @Override
    public void unregisterCommand(String name) {
        getBukkitCommands(getCommandMap()).remove(name);
    }

    // copied from triumph-cmd, credit goes to triumph-team
    @NotNull
    private CommandMap getCommandMap() {
        try {
            final Server server = Bukkit.getServer();
            final Method getCommandMap = server.getClass().getDeclaredMethod("getCommandMap");
            getCommandMap.setAccessible(true);

            return (CommandMap) getCommandMap.invoke(server);
        } catch (final Exception ignored) {
            throw new CommandRegistrationException("Unable get Command Map. Commands will not be registered!");
        }
    }

    @NotNull
    private Map<String, Command> getBukkitCommands(@NotNull final CommandMap commandMap) {
        try {
            final Field bukkitCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            bukkitCommands.setAccessible(true);
            //noinspection unchecked
            return (Map<String, org.bukkit.command.Command>) bukkitCommands.get(commandMap);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new CommandRegistrationException("Unable get Bukkit commands. Commands might not be registered correctly!");
        }
    }

    private void registerCommands() {
        commandManager = BukkitCommandManager.create(this);

        commandManager.registerSuggestion(SuggestionKey.of("online-players"),
                (_sender, _ctx) -> playerManager.getAllPlayers());

        commandManager.registerCommand(
                new ReloadCommand(this),
                new PrivateMessageCommand(this),
                new StaffChatCommand(this),
                new InventoryCommand(this),
                new IgnoreCommand(this),
                new SpyCommand(this),
                new ReplyCommand(this)
        );

        commandManager.registerMessage(MessageKey.INVALID_ARGUMENT, (sender, invalidArgumentContext) ->
                ChatUtils.sendMessage(sender, ChatUtils.format(messagesConfig.getString("messages.invalid-command"))));
        commandManager.registerMessage(MessageKey.UNKNOWN_COMMAND, (sender, invalidArgumentContext) ->
                ChatUtils.sendMessage(sender, ChatUtils.format(messagesConfig.getString("messages.invalid-command"))));
        commandManager.registerMessage(MessageKey.NOT_ENOUGH_ARGUMENTS, (sender, invalidArgumentContext) ->
                ChatUtils.sendMessage(sender, ChatUtils.format(messagesConfig.getString("messages.invalid-command"))));
        commandManager.registerMessage(MessageKey.TOO_MANY_ARGUMENTS, (sender, invalidArgumentContext) ->
                ChatUtils.sendMessage(sender, ChatUtils.format(messagesConfig.getString("messages.invalid-command"))));
        commandManager.registerMessage(BukkitMessageKey.NO_PERMISSION, (sender, invalidArgumentContext) ->
                ChatUtils.sendMessage(sender, ChatUtils.format(messagesConfig.getString("messages.no-permission"))));
    }

}
