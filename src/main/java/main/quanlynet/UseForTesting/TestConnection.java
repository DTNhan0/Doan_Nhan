package main.quanlynet.UseForTesting;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) throws SQLException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("12345");
        ds.setServerName("LAPTOP-R5G6HICH");
        ds.setPortNumber(1433);
        ds.setDatabaseName("QuanLyTiemNet");
        ds.setEncrypt(false);

        try(Connection con = ds.getConnection()){
            System.out.println("Ket noi thanh cong!");
            System.out.println(con.getMetaData());
        } catch (SQLServerException throwables){
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Connection c = ds.getConnection();
        Statement stmt = c.createStatement();
        String sql = "SELECT * FROM PHONGMAY";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(String.format("ID: %s - Fulname: %s",
                    rs.getString("MAPHONG"), rs.getString("TENPHONG")));
        }
        rs.close();
        stmt.close();
        c.close();
    }
}
