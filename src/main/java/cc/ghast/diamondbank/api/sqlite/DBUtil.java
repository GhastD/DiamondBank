package cc.ghast.diamondbank.api.sqlite;

import java.io.Closeable;

public class DBUtil {

    /**
     * @param closeables Closeable object you wish to close
     */
    public static void close(Closeable... closeables) {
        try {
            for (Closeable closeable : closeables) if (closeable != null) closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param closeables Closeable object you wish to close
     */
    public static void close(AutoCloseable... closeables) {
        try {
            for (AutoCloseable closeable : closeables) if (closeable != null) closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
