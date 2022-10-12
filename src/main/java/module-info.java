module se.kth.estebanmm.lab4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.kth.estebanmm.lab4 to javafx.fxml;
    exports se.kth.estebanmm.lab4;
}