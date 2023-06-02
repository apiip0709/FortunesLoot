package fortunesloot.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fortunesloot.models.DataRegist;

public class Registdb {
    private static final String DB_URL = "jdbc:sqlite:db_user.db";
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public static Connection connection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Registdb() {
        connection = connection();
        setupTable();
    }

    private void setupTable() {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getTables(null, null, "regist", null);
            if (!rs.next()) {
                Statement statement = connection.createStatement();
                String sql = "CREATE TABLE regist " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " namaLengkap TEXT NOT NULL, " +
                        " umur INTEGER NOT NULL, " +
                        " username TEXT NOT NULL, " +
                        " password TEXT NOT NULL)";
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DataRegist> getAll() throws SQLException {
        try {
            List<DataRegist> listUser = new ArrayList<>();
            // Prepare the SQL statement
            String sql = "SELECT * FROM regist";
            preparedStatement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Iterate over the result set and populate the dataList
            while (resultSet.next()) {
                String namaLengkapData = resultSet.getString("namaLengkap");
                int umur = resultSet.getInt("umur");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                // Create a loginData object and add it to the dataList
                DataRegist data = new DataRegist(namaLengkapData, umur, username, password);
                listUser.add(data);
            }
            return listUser;
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public static void saveData(String namaLengkap, int umur, String username, String password) {
        try {
            // Prepare the SQL statement
            String sql = "INSERT INTO regist (namaLengkap, umur, username, password) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            // Set the parameter values
            preparedStatement.setString(1, namaLengkap);
            preparedStatement.setInt(2, umur);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);

            // Execute the statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
