module Project8 {
    requires javafx.controls;
    requires javafx.fxml;
    opens client.app to javafx.fxml;
    exports client.app ;
    exports server.app;
    opens server.app to javafx.fxml;
    requires java.rmi;
    requires java.desktop;

}