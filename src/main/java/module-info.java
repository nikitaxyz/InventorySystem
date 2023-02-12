/**
 * C482 application
 * @author Nikita Kukshynsky
 */

module xyz.bubblesort.c482c {
    requires javafx.controls;
    requires javafx.fxml;

    exports xyz.bubblesort.c482.main;
    opens xyz.bubblesort.c482.main to javafx.fxml;
    exports xyz.bubblesort.c482.controllers;
    opens xyz.bubblesort.c482.controllers to javafx.fxml;
    exports xyz.bubblesort.c482.models;
    opens xyz.bubblesort.c482.models to javafx.fxml;
    exports xyz.bubblesort.c482.utils;
    opens xyz.bubblesort.c482.utils to javafx.fxml;
}