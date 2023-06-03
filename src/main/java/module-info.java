module com.example.pj_oop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pj_oop to javafx.fxml;
    exports com.example.pj_oop;
    exports com.example.pj_oop.PageControllers;
    opens com.example.pj_oop.PageControllers to javafx.fxml;
}