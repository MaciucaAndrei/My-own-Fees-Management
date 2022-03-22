module project.licenta {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires deltaspike.jpa.module.api;
    requires java.annotation;
    requires cdi.api;
    requires javax.inject;
    requires java.persistence;
    requires deltaspike.cdictrl.api;
    requires deltaspike.data.module.api;

    opens project.licenta to javafx.fxml;
    opens project.licenta.producer;
    opens project.licenta.service;
    opens project.licenta.repository;
    exports project.licenta;
    exports project.licenta.dbThread;
    exports project.licenta.entity;
    exports project.licenta.service;
    exports project.licenta.repository;



}