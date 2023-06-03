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

import fortunesloot.models.DataUser;

public class Datadb {
    private static final String DB_URL = "jdbc:sqlite:db_data.db";
    
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

    public static void setupTable() {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs1 = meta.getTables(null, null, "dataPenghasilan", null);
            ResultSet rs2 = meta.getTables(null, null, "dataPengeluaran", null);
            // Table dataPenghasilan
            if (!rs1.next()) {
                Statement statement = connection.createStatement();
                String sql = "CREATE TABLE dataPenghasilan " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " jenis TEXT NOT NULL, " +
                        " jumlah INTEGER NOT NULL, " +
                        " tanggal TEXT NOT NULL)";
                statement.executeUpdate(sql);
            }

            // Tabel dataPengeluaran
            if (!rs2.next()) {
                Statement statement = connection.createStatement();
                String sql = "CREATE TABLE dataPengeluaran " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " jenis TEXT NOT NULL, " +
                        " jumlah INTEGER NOT NULL, " +
                        " tanggal TEXT NOT NULL)";
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<DataUser> getAll(String tableName) {
        List<DataUser> dataList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + tableName;
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                String jenis = resultSet.getString("jenis");
                int jumlah = resultSet.getInt("jumlah");
                String tanggal = resultSet.getString("tanggal");
    
                DataUser data;
                if (tableName.equals("dataPenghasilan")) {
                    data = new DataUser(jenis, jumlah, tanggal);
                } else if (tableName.equals("dataPengeluaran")) {
                    data = new DataUser(jenis, jumlah, tanggal);
                } else {
                    // Handle unsupported table name
                    continue;
                }
                dataList.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }    

    public static void saveData(String tableName, String jenis, int jumlah, String tanggal) {
        try {
            String insertQuery = "INSERT INTO " + tableName + " (jenis, jumlah, tanggal) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            
            preparedStatement.setString(1, jenis);
            preparedStatement.setInt(2, jumlah);
            preparedStatement.setString(3, tanggal);
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteData(String tableName, Object data) {
         try {
            String deleteQuery = "DELETE FROM " + tableName + " WHERE id = (SELECT MIN(id) FROM " + tableName + " WHERE jenis=? AND jumlah=? AND tanggal=?)";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
    
            if (data instanceof DataUser) {
                DataUser dataObject = (DataUser) data;
                preparedStatement.setString(1, dataObject.getJenis());
                preparedStatement.setInt(2, dataObject.getJumlah());
                preparedStatement.setString(3, dataObject.getTanggal());
            } else if (data instanceof DataUser) {
                DataUser data1Object = (DataUser) data;
                preparedStatement.setString(1, data1Object.getJenis());
                preparedStatement.setInt(2, data1Object.getJumlah());
                preparedStatement.setString(3, data1Object.getTanggal());
            }
    
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
