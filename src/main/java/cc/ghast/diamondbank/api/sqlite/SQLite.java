package cc.ghast.diamondbank.api.sqlite;


import cc.ghast.diamondbank.utils.Chat;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SQLite {

    private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setPriority(3).setNameFormat("SQL Executor Thread").build());

    /**
     * @Example
     * SQLiteType database = new SQLite();
     *
     * Connection sqlite = database.connect(this, "MyTable");
     *
     * int banSize = database.getSize(sqlite, "select * from alerts where uuid=" + player.getUniqueId + " order by timestamp");
     */


    private Connection conn;
    private Plugin plugin;



    
    public boolean isConnected() {
        return conn != null;
    }




    
    public void stop() {
        SERVICE.shutdownNow().forEach(Runnable::run);
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    
    public void execute(Runnable runnable) {
        SERVICE.execute(runnable);
    }



    
    public Connection connect(Plugin plugin) {
        this.plugin = plugin;
        try {
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath()
                    + File.separator + "db.sqlite";
            conn = DriverManager.getConnection(url);
            Query.use(conn);
            try {
                Schema.createServer().execute();
                Schema.createTransactions().execute();
            } catch (Exception e) {
                plugin.getServer().getConsoleSender().sendMessage(Chat.translate("&4Failed to create SQLite table: " + e.getMessage()));
                e.printStackTrace();
            }

            plugin.getServer().getConsoleSender().sendMessage(Chat.translate("&aConnection to SQLite has been established."));
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage(Chat.translate("&4Failed to load SQLite: " + e.getMessage()));
        }
        return conn;
    }


    
    public int getSize(Connection conn, String query) {
        Query.use(conn);
        ResultSet resultSet = Query.prepare(query).executeQuery();
        try {
            return resultSet.getFetchSize();
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage(Chat.translate("&4Error while retrieving fetch size"));
        }
        return 0;
    }

    
    public void runQuery(Connection conn, ExecutableStatement query) {
        Query.use(conn);
        query.executeQuery();
    }


    
    public ResultSet getQueryResult(Connection conn, ExecutableStatement query) {
        Query.use(conn);
        return query.executeQuery();
    }
}

