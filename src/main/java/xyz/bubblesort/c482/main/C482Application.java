package xyz.bubblesort.c482.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.bubblesort.c482.controllers.MainController;
import xyz.bubblesort.c482.controllers.PartController;
import xyz.bubblesort.c482.controllers.ProductController;
import xyz.bubblesort.c482.controllers.SceneController;

import java.io.IOException;

/**
 * The main class of the program: JavaFX application for handling inventory with products and parts.
 * <i><strong>FUTURE ENHANCEMENTS:</strong></i><br>
 * <ul>
 *     <li>Create dedicated .css file for all styles used. It will help with redundant styles and make adjusting styles easier</li>
 *     <li>Make UI adaptive for different screen sizes</li>
 *     <li>Elaborate idea of MVC and decrease coupling</li>
 *     <li>Add database to the application so data can be saved and restored between different sessions</li>
 * </ul>
 *
 * @author Nikita Kukshysnky
 */
public class C482Application extends Application {

    /**
     * Starts the JavaFX application.
     * <i><strong>RUNTIME ERROR:</strong></i> java.lang.IllegalStateException: Location is not set.
     * It was caused by mistakes in filenames for fxml files. It was corrected by creating the right folder structure for resources.
     *
     * @param stage the main stage of JavaFX application
     */
    @Override
    public void start(Stage stage) throws IOException {
        SceneController sceneController = new SceneController(stage);

        FXMLLoader fxmlLoader = new FXMLLoader(C482Application.class.getResource("main-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        sceneController.addScene("main", scene);
        MainController mainController = fxmlLoader.getController();
        sceneController.addController("main", mainController);
        mainController.setSceneController(sceneController);

        fxmlLoader = new FXMLLoader(C482Application.class.getResource("part-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        sceneController.addScene("part", scene);
        PartController partController = fxmlLoader.getController();
        sceneController.addController("part", partController);
        partController.setSceneController(sceneController);

        fxmlLoader = new FXMLLoader(C482Application.class.getResource("part-modify-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        sceneController.addScene("part-modify", scene);
        PartController partControllerModify = fxmlLoader.getController();
        sceneController.addController("part-modify", partControllerModify);
        partControllerModify.setSceneController(sceneController);

        fxmlLoader = new FXMLLoader(C482Application.class.getResource("product-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        sceneController.addScene("product", scene);
        ProductController productController = fxmlLoader.getController();
        sceneController.addController("product", productController);
        productController.setSceneController(sceneController);

        fxmlLoader = new FXMLLoader(C482Application.class.getResource("product-modify-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        sceneController.addScene("product-modify", scene);
        ProductController productControllerModify = fxmlLoader.getController();
        sceneController.addController("product-modify", productControllerModify);
        productControllerModify.setSceneController(sceneController);

        sceneController.setScene("main");
        stage.sizeToScene();
        stage.setTitle("Inventory Management System");
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    /**
     * Entrance of the application
     * Javadoc documentation located at the "Documentation" folder
     * @param args unused program arguments
     */
    public static void main(String[] args) {
        launch();
    }
}