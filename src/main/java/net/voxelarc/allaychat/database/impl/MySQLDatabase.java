package net.voxelarc.allaychat.database.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.database.Queries;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class MySQLDatabase extends SQLiteDatabase {

    private DataSource dataSource;

    public MySQLDatabase(AllayChatPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("AllayChat Connection Pool");
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl(String.format("jdbc:mariadb://%s:%s/%s",
                plugin.getConfig().getString("database.host"),
                plugin.getConfig().getString("database.port"),
                plugin.getConfig().getString("database.database")));
        config.setConnectionTestQuery("SELECT 1");
        config.setPassword(plugin.getConfig().getString("database.password"));
        config.setUsername(plugin.getConfig().getString("database.user"));
        config.addDataSourceProperty("useUnicode", "true");
        config.addDataSourceProperty("characterEncoding", "utf8");

        config.setMaxLifetime(plugin.getConfig().getInt("database.max-lifetime", 30000));
        config.setIdleTimeout(plugin.getConfig().getInt("database.idle-timeout", 10000));
        config.setMaximumPoolSize(plugin.getConfig().getInt("database.pool-size", 20));
        config.setMinimumIdle(plugin.getConfig().getInt("database.min-idle", 5));
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("cacheCallableStmts", true);
        config.addDataSourceProperty("cacheResultSetMetadata", true);
        config.addDataSourceProperty("cacheServerConfiguration", true);
        config.addDataSourceProperty("useLocalSessionState", true);
        config.addDataSourceProperty("elideSetAutoCommits", true);
        config.addDataSourceProperty("alwaysSendSetIsolation", false);

        dataSource = new HikariDataSource(config);

        usersTable = plugin.getConfig().getString("database.users-table", "allaychat_users");
        ignoredTable = plugin.getConfig().getString("database.ignored-table", "allaychat_ignored");

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(Queries.CREATE_USER_TABLE.getQuery(usersTable));
            statement.executeUpdate(Queries.CREATE_IGNORE_TABLE.getQuery(ignoredTable, usersTable));
        } catch (SQLException e) {
            plugin.getLogger().log(Level.WARNING, "Database thrown an exception!", e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            plugin.getLogger().log(Level.WARNING, "Database thrown an exception!", e);
            throw new RuntimeException(e);
        }
    }

}
