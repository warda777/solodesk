module com.warda.solodesk {
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires org.slf4j;
    requires de.jensd.fx.glyphs.fontawesome;
    requires javafx.controls;


    opens com.warda.solodesk to javafx.fxml;
    opens com.warda.solodesk.Controllers to javafx.fxml;
    opens com.warda.solodesk.Controllers.Main to javafx.fxml;
    opens com.warda.solodesk.Fxml to javafx.fxml;
    opens com.warda.solodesk.Models to javafx.base;

    exports com.warda.solodesk;
    exports com.warda.solodesk.Controllers;
    exports com.warda.solodesk.Views;
}