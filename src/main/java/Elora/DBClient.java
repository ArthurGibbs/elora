package Elora;

import java.sql.*;

public class DBClient {
    private Connection connection;

    public DBClient(String host, String port, String schema, String username, String password) {
        String connectionUrl = "jdbc:mysql://"+host+":"+port+"/"+schema+"?serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String query) {
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int update(String query) {
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
