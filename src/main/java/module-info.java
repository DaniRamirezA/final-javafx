module org.example.finaljavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    exports org.example.finaljavafx;
    exports org.example.finaljavafx.ADaniela;
    exports org.example.finaljavafx.ADaniela.controllers.admin;
    exports org.example.finaljavafx.ADaniela.controllers.auth;
    exports org.example.finaljavafx.ADaniela.controllers.trabajador;
    exports org.example.finaljavafx.ADaniela.models;
    exports org.example.finaljavafx.ADaniela.services;
    exports org.example.finaljavafx.ADaniela.dao;
    exports org.example.finaljavafx.ADaniela.dao.impl.IO;
    exports org.example.finaljavafx.ADaniela.dao.impl.List;

    opens org.example.finaljavafx to javafx.fxml;
    opens org.example.finaljavafx.ADaniela.controllers.admin to javafx.fxml;
    opens org.example.finaljavafx.ADaniela.controllers.auth to javafx.fxml;
    opens org.example.finaljavafx.ADaniela.controllers.trabajador to javafx.fxml;
    opens org.example.finaljavafx.ADaniela.models to javafx.fxml;
    opens org.example.finaljavafx.views.admin to javafx.fxml;
    opens org.example.finaljavafx.views.auth to javafx.fxml;
    opens org.example.finaljavafx.views.trabajador to javafx.fxml;
}