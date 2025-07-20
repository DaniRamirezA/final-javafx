module org.example.finaljavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    exports org.example.finaljavafx;
    exports org.example.finaljavafx.controllers.admin;
    exports org.example.finaljavafx.controllers.auth;
    exports org.example.finaljavafx.controllers.trabajador;
    exports org.example.finaljavafx.models;
    exports org.example.finaljavafx.services;
    exports org.example.finaljavafx.dao;
    exports org.example.finaljavafx.dao.impl.IO;

    opens org.example.finaljavafx.controllers.admin to javafx.fxml;
    opens org.example.finaljavafx.controllers.auth to javafx.fxml;
    opens org.example.finaljavafx.controllers.trabajador to javafx.fxml;
    opens org.example.finaljavafx.models to javafx.fxml;
    opens org.example.finaljavafx.views.auth to javafx.fxml;
    opens org.example.finaljavafx.views.admin to javafx.fxml;
    opens org.example.finaljavafx.views.trabajador to javafx.fxml;
}