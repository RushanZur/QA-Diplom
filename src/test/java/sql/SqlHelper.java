package sql;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    @SneakyThrows
    public static Connection getConnection() throws SQLException {
        String url = System.getProperty("db.url");
        String username = System.getProperty("db.user");
        String password = System.getProperty("db.password");
        return null;
    }

    @SneakyThrows
    public static void cleanDb() {
        val creditRequest = "DELETE FROM credit_request_entity";
        val order = "DELETE FROM order_entity";
        val payment = "DELETE FROM payment_entity";
        val runner = new QueryRunner();
    }

    @SneakyThrows
    public static String getStatusCreditRequestEntity() {
        val conn = getConnection();
        val countStmt = conn.createStatement();
        {
            val sql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
            val resultSet = countStmt.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
            return null;
        }
    }

    @SneakyThrows
    public static String getStatusPaymentEntity() {
        val conn = getConnection();
        val countStmt = conn.createStatement();{
            val sql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
            val resultSet = countStmt.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
            return null;
        }
    }
}
