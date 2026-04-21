package infrastructure;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try {
            Connection conn = DatabaseConnection.getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conectado a Railway correctamente");
            }

        } catch (Exception e) {
            System.out.println("❌ Error al conectar:");
            e.printStackTrace();
        }
    }
}