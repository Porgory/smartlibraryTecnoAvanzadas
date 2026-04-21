package infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

private static final String URL =
"jdbc:mysql://monorail.proxy.rlwy.net:30631/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

private static final String USER = "root";
    private static final String PASSWORD = "YtGGrvwjhiVrYJsXVaVncrmTMDMkvthA";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}