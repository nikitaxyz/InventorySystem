package xyz.bubblesort.c482.controllers;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import xyz.bubblesort.c482.models.Inventory;
import xyz.bubblesort.c482.models.Part;

/**
 * Helper abstract class that provides methods for initializing parts table with search capability and setter of sceneController
 *
 * @author Nikita Kukshynsky
 */
public abstract class ControllerHelper {
    /**
     * Keeps instance of sceneController to communicate between different controllers
     */
    protected SceneController sceneController;

    /**
     * Initialize TableView of <code>Part</code> with included search feature
     *
     * @param searchFieldPart a search TextField
     * @param partsTable      a TableView of parts
     * @param idColumnPart    a column of part id
     * @param nameColumnPart  a column of part name
     * @param stockColumnPart a column of part stock/inventory
     * @param priceColumnPart a column of part price
     */
    protected static void initializePartsWithSearch(TextField searchFieldPart, TableView<Part> partsTable, TableColumn<Part, String> idColumnPart, TableColumn<Part, String> nameColumnPart, TableColumn<Part, String> stockColumnPart, TableColumn<Part, String> priceColumnPart) {
        ObservableList<Part> parts = Inventory.getAllParts();
        FilteredList<Part> filteredParts = new FilteredList<>(parts, p -> true);

        searchFieldPart.textProperty().addListener((observable, oldText, newText) -> filteredParts.setPredicate(part -> {
            if (newText == null || newText.isEmpty()) {
                return true;
            }
            String lowerCaseText = newText.toLowerCase();
            return String.valueOf(part.getId()).contains(lowerCaseText) || part.getName().contains(lowerCaseText);
        }));

        SortedList<Part> sortedData = new SortedList<>(filteredParts);

        sortedData.comparatorProperty().bind(partsTable.comparatorProperty());

        Property<ObservableList<Part>> propertyParts = new SimpleObjectProperty<>(filteredParts);
        partsTable.itemsProperty().bind(propertyParts);

        PropertyValueFactory<Part, String> idColumnFactoryPart = new PropertyValueFactory<>("id");
        PropertyValueFactory<Part, String> nameColumnFactoryPart = new PropertyValueFactory<>("name");
        PropertyValueFactory<Part, String> stockColumnFactoryPart = new PropertyValueFactory<>("stock");
        PropertyValueFactory<Part, String> priceColumnFactoryPart = new PropertyValueFactory<>("price");

        idColumnPart.setCellValueFactory(idColumnFactoryPart);
        nameColumnPart.setCellValueFactory(nameColumnFactoryPart);
        stockColumnPart.setCellValueFactory(stockColumnFactoryPart);
        priceColumnPart.setCellValueFactory(priceColumnFactoryPart);
    }

    /**
     * Setter for sceneController
     *
     * @param sceneController a scene controller to operate all defined controllers
     */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

}
