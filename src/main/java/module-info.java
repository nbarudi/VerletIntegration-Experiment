module com.example.verletintegrationsym {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.bungo.physics.verletintegrationsym to javafx.fxml;
    exports ca.bungo.physics.verletintegrationsym;
}