package main.quanlynet.Database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class MainConnection {
    private static final SQLServerDataSource ds = new SQLServerDataSource();
    static {
        ds.setUser("sa");
        ds.setPassword("12345");
        ds.setServerName("LAPTOP-R5G6HICH");
        ds.setPortNumber(1433);
        ds.setDatabaseName("QuanLyTiemNet");
        ds.setEncrypt(false);
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLServerException e) {
            e.printStackTrace();
            throw new RuntimeException("Kết nối không thành công!");
        }
    }
}
