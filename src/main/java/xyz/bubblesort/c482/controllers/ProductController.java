package xyz.bubblesort.c482.controllers;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import xyz.bubblesort.c482.models.*;
import xyz.bubblesort.c482.utils.IDGenerator;

import java.util.Objects;
import java.util.Optional;

/**
 * Controller for Product form views
 *
 * @author Nikita Kukshynsky
 */
public class ProductController extends ControllerHelper {
    /**
     * Text field for a product id
     */
    public TextField productID;
    /**
     * Text field for a product name
     */
    public TextField productName;
    /**
     * Text field for a product stock
     */
    public TextField productStock;
    /**
     * Text field for a product price
     */
    public TextField productPrice;
    /**
     * Text field for a product max
     */
    public TextField productMax;
    /**
     * Text field for a product min
     */
    public TextField productMin;
    /**
     * Table of all parts
     */
    public TableView<Part> partsTable;
    /**
     * Column for a part id
     */
    public TableColumn<Part, String> idColumnPart;
    /**
     * Column for a part name
     */
    public TableColumn<Part, String> nameColumnPart;
    /**
     * Column for a part stock
     */
    public TableColumn<Part, String> stockColumnPart;
    /**
     * Column for a part price
     */
    public TableColumn<Part, String> priceColumnPart;
    /**
     * Table of selected parts
     */
    public TableView<Part> partsTableSelected;
    /**
     * Column for a selected part id
     */
    public TableColumn<Part, String> idColumnPartSelected;
    /**
     * Column for a selected part name
     */
    public TableColumn<Part, String> nameColumnPartSelected;
    /**
     * Column for a selected part stock
     */
    public TableColumn<Part, String> stockColumnPartSelected;
    /**
     * Column for a selected part price
     */
    public TableColumn<Part, String> priceColumnPartSelected;
    /**
     * Text field for a part search
     */
    public TextField searchFieldPart;
    /**
     * Label for displaying errors
     */
    public Label errors;
    private int activeID;

    private final ObservableList<Part> partsSelected = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        initializeParts();
    }

    private void initializeParts() {
        initializePartsWithSearch(searchFieldPart, partsTable, idColumnPart, nameColumnPart, stockColumnPart, priceColumnPart);

        Property<ObservableList<Part>> propertyPartsSelected = new SimpleObjectProperty<>(partsSelected);
        partsTableSelected.itemsProperty().bind(propertyPartsSelected);

        PropertyValueFactory<Part, String> idColumnFactoryPartSelected = new PropertyValueFactory<>("id");
        PropertyValueFactory<Part, String> nameColumnFactoryPartSelected = new PropertyValueFactory<>("name");
        PropertyValueFactory<Part, String> stockColumnFactoryPartSelected = new PropertyValueFactory<>("stock");
        PropertyValueFactory<Part, String> priceColumnFactoryPartSelected = new PropertyValueFactory<>("price");

        idColumnPartSelected.setCellValueFactory(idColumnFactoryPartSelected);
        nameColumnPartSelected.setCellValueFactory(nameColumnFactoryPartSelected);
        stockColumnPartSelected.setCellValueFactory(stockColumnFactoryPartSelected);
        priceColumnPartSelected.setCellValueFactory(priceColumnFactoryPartSelected);
    }

    /**
     * Changes to Main form on Cancel button click
     */
    @FXML
    protected void onCancel() {
        sceneController.setScene("main");
        MainController mc = (MainController) sceneController.getController("main");
        mc.clearErrors();
        clear();
    }

    /**
     * Sets generated id for a product
     */
    @FXML
    public void setProductID() {
        clear();
        int newID = IDGenerator.getNextProductID();
        setActiveID(newID);
        productID.setText("Auto Gen - Disabled");
    }

    private void setActiveID(int newID) {
        activeID = newID;
    }

    /**
     * Adds part to the product.
     * <i><strong>RUNTIME ERROR:</strong></i> java.lang.NullPointerException.
     * The error was due to assigning the part to a not existing product.
     * It was corrected by adding a condition to see if activeID represents an already created product or not.
     */
    public void onAddPart() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            errors.setText("The part must be selected");
        } else {
            if (!partsSelected.contains(selectedPart)) {
                partsSelected.add(selectedPart);
                if (IDGenerator.productsIDList.contains(activeID)) {
                    Objects.requireNonNull(Inventory.lookupProduct(activeID)).addAssociatedPart(selectedPart);
                }
            } else {
                errors.setText("This part is already selected");
            }
        }

    }

    /**
     * Removes part to the product
     */
    public void onRemovePart() {
        Part selectedPart = partsTableSelected.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            errors.setText("The part must be selected");
        } else {
            Stage stage = sceneController.getStage();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);
            alert.setTitle("Products");
            alert.setHeaderText("Remove");
            alert.setContentText("Do you want to remove this associated part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                partsSelected.remove(selectedPart);
                if (IDGenerator.productsIDList.contains(activeID)) {
                    Objects.requireNonNull(Inventory.lookupProduct(activeID)).deleteAssociatedPart(selectedPart);
                }
            }
            partsTableSelected.refresh();
        }
    }

    /**
     * Saves a new product if all fields are valid
     */
    private void save() {
        StringBuilder errorsText = new StringBuilder("Exceptions:\n");
        boolean isError = false;

        int ID = activeID;

        String name = "";
        if (productName.getText().isEmpty()) {
            errorsText.append("No data in Name field\n");
            isError = true;
        } else {
            name = productName.getText();
        }

        int inv = -1;
        try {
            inv = Integer.parseInt(productStock.getText());
        } catch (NumberFormatException nfe) {
            errorsText.append("Inventory is not an integer\n");
            isError = true;
        }

        double price = 0;
        try {
            price = Double.parseDouble(productPrice.getText());
        } catch (NumberFormatException nfe) {
            errorsText.append("Price is not a double\n");
            isError = true;
        }

        int max = -1;
        try {
            max = Integer.parseInt(productMax.getText());
        } catch (NumberFormatException nfe) {
            errorsText.append("Max is not an integer\n");
            isError = true;
        }

        int min = -1;
        try {
            min = Integer.parseInt(productMin.getText());
        } catch (NumberFormatException nfe) {
            errorsText.append("Min is not an integer\n");
            isError = true;
        }

        if (max != -1 && min != -1 && inv != -1 && (inv < min || inv > max)) {
            errorsText.append("Inventory must be between Min and Max\n");
            isError = true;
        }

        if (max != -1 && min != -1 && min >= max) {
            errorsText.append("Max must be greater than Min\n");
            isError = true;
        }

        if (!isError) {
            if (IDGenerator.productsIDList.contains(ID)) {
                Product product = Inventory.lookupProduct(ID);
                assert product != null;
                product.setName(name);
                product.setStock(inv);
                product.setPrice(price);
                product.setMax(max);
                product.setMin(min);
            } else {
                ObservableList<Part> partsList = FXCollections.observableArrayList();
                partsList.addAll(partsSelected);
                Product product = new Product(partsList, ID, name, price, inv, min, max);
                Inventory.addProduct(product);
                IDGenerator.productsIDList.add(ID);
            }
            clear();
            sceneController.setScene("main");
        } else {
            errors.setText(errorsText.toString());
        }
    }

    /**
     * Saves a product and refresh parts table
     */
    public void onSave() {
        save();
        MainController mc = (MainController) sceneController.getController("main");
        mc.productsTable.refresh();
    }

    /**
     * clears form from old data
     * <i><strong>RUNTIME ERROR:</strong></i> java.lang.NullPointerException: Cannot invoke "javafx.collections.ObservableList.clear()" because "this.partsSelected" is null
     * <i><strong>HOW CORRECTED:</strong></i>
     */
    private void clear() {
        productName.setText("");
        productMin.setText("");
        productMax.setText("");
        productPrice.setText("");
        productStock.setText("");
        errors.setText("");
        partsSelected.clear();
    }

    /**
     * Modify passed product
     *
     * @param product product to modify
     */
    public void modifyProduct(Product product) {
        clear();
        productID.setText(String.valueOf(product.getId()));
        productName.setText(product.getName());
        productStock.setText(String.valueOf(product.getStock()));
        productPrice.setText(String.valueOf(product.getPrice()));
        productMin.setText(String.valueOf(product.getMin()));
        productMax.setText(String.valueOf(product.getMax()));
        partsSelected.addAll(product.getAllAssociatedParts());
        setActiveID(product.getId());
        partsTableSelected.refresh();
    }
}
