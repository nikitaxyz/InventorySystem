package xyz.bubblesort.c482.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Helps to switch between scenes and access stage and controllers
 *
 * @author Nikita Kukshynsky
 */
public class SceneController {
    private final Stage stage;
    private final HashMap<String, Scene> scenes;
    private final HashMap<String, ControllerHelper> controllers;

    /**
     * Constructs a new SceneController object
     *
     * @param stage main stage of the JavaFX application
     */
    public SceneController(Stage stage) {
        this.stage = stage;
        scenes = new HashMap<>();
        controllers = new HashMap<>();
    }

    /**
     * Adds a new scene to all scenes
     *
     * @param name  name reference for a scene
     * @param scene scene object
     */
    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    /**
     * Adds a new controller to all controllers
     *
     * @param name name reference for a controller
     * @param controller controller to add
     */
    public void addController(String name, ControllerHelper controller) {
        controllers.put(name, controller);
    }

    /**
     * Changes current scene of the JavaFX application
     *
     * @param name name reference for a scene
     */
    public void setScene(String name) {
        stage.setScene(scenes.get(name));
    }

    /**
     * Gets a controller by a reference name
     *
     * @param name name reference for a controller
     * @return ControllerHelper object
     */
    public ControllerHelper getController(String name) {
        return controllers.get(name);
    }

    /**
     * Return main stage of the JavaFX application
     *
     * @return Stage object
     */
    public Stage getStage() {
        return stage;
    }
}
