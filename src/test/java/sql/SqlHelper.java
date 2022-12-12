package sql;



import org.apache.commons.dbutils.QueryRunner;

import java.sql.*;

public class SqlHelper {

    public static Connection getConnection() throws SQLException {
        String url = System.getProperty("db.url");
        String username = System.getProperty("db.user");
        String password = System.getProperty("db.password");
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return null;
    }

    public static void cleanDb() {
        String creditRequest = "DELETE FROM credit_request_entity";
        String order = "DELETE FROM order_entity";
        String payment = "DELETE FROM payment_entity";
        QueryRunner runner = new QueryRunner();
        try (Connection conn = getConnection();
        ) {
            runner.update(conn, creditRequest);
            runner.update(conn, order);
            runner.update(conn, payment);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static String getStatusCreditRequestEntity() {
        try (Connection conn = getConnection();
             Statement countStmt = conn.createStatement()) {
            String sql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
            ResultSet resultSet = countStmt.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return null;
    }

    public static String getStatusPaymentEntity() {
        try (Connection conn = getConnection();
             Statement countStmt = conn.createStatement()) {
            String sql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
            ResultSet resultSet = countStmt.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return null;
    }
}
