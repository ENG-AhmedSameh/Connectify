module com.connectify.shared {
    requires java.sql;
    requires java.rmi;
    requires org.mapstruct;

    exports com.connectify.model.enums;
    exports com.connectify.model.entities;
    exports com.connectify.dto;
    exports com.connectify.Interfaces;
    exports com.connectify.mapper;
}