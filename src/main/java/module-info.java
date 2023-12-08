module main.quanlynet {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires java.naming;
    requires javafx.base;

    opens main.quanlynet to javafx.fxml;
    opens main.quanlynet.Controller to javafx.fxml;
    opens main.quanlynet.Database to javafx.fxml;
    opens main.quanlynet.UseForTesting to javafx.fxml;
    exports main.quanlynet;
    exports main.quanlynet.Controller;
    exports main.quanlynet.UseForTesting;

}
