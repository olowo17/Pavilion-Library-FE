module com.pavilion.libraryui {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.web;
    requires spring.context;
    requires static lombok;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires java.logging;
    requires java.desktop;


    opens com.pavilion.libraryui to javafx.fxml;
    exports com.pavilion.libraryui;
    exports com.pavilion.libraryui.model;
    opens com.pavilion.libraryui.model to javafx.fxml;
    exports com.pavilion.libraryui.service;
    opens com.pavilion.libraryui.service to javafx.fxml;
    exports com.pavilion.libraryui.controller;
    opens com.pavilion.libraryui.controller to javafx.fxml;
}