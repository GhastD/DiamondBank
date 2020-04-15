package cc.ghast.diamondbank.api.sqlite;

import lombok.Getter;
import lombok.SneakyThrows;

import java.sql.Connection;

public class Query {
    @Getter private static Connection conn;

    public static void use(Connection conn) {
        Query.conn = conn;
    }

    @SneakyThrows
    public static ExecutableStatement prepare(@Language("MySQL") String query) {
        return new ExecutableStatement(conn.prepareStatement(query));
    }

    @SneakyThrows
    public static ExecutableStatement prepare(@Language("MySQL") String query, Connection con) {
        return new ExecutableStatement(con.prepareStatement(query));
    }

}
