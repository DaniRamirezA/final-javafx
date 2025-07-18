module org.example.finaljavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.finaljavafx to javafx.fxml;
    opens org.example.finaljavafx.ADaniela.controllers.admin to javafx.fxml;
    opens org.example.finaljavafx.ADaniela.controllers.auth to javafx.fxml;
    opens org.example.finaljavafx.ADaniela.models to javafx.fxml;

    exports org.example.finaljavafx;
    exports org.example.finaljavafx.ADaniela;
    exports org.example.finaljavafx.ADaniela.controllers.admin;
    exports org.example.finaljavafx.ADaniela.controllers.auth;
    exports org.example.finaljavafx.ADaniela.models;
    exports org.example.finaljavafx.ADaniela.services;
}