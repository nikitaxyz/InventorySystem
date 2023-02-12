package xyz.bubblesort.c482.controllers;


import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import xyz.bubblesort.c482.models.*;
import xyz.bubblesort.c482.utils.IDGenerator;

import java.util.Optional;

/**
 * Controller for the Main form (main-form-view.fxml) view
 * <i><strong>RUNTIME ERROR:</strong></i> java.lang.NullPointerException: Cannot invoke "javafx.scene.control.TextField.textProperty()" because "searchFieldPart" is null
 * It was caused by using protected access specifier for the searchFieldPart. Making it public fixed the issue.
 *
 * @author Nikita Kukshynsky
 */
public class MainController extends ControllerHelper {
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
     * Label for displaying errors
     */
    public Label errors;
    /**
     * Table of all products
     */
    public TableView<Product> productsTable;
    /**
     * Column for a product id
     */
    public TableColumn<Product, String> idColumnProduct;
    /**
     * Column for a product name
     */
    public TableColumn<Product, String> nameColumnProduct;
    /**
     * Column for a product stock
     */
    public TableColumn<Product, String> stockColumnProduct;
    /**
     * Column for a product price
     */
    public TableColumn<Product, String> priceColumnProduct;
    /**
     * Text field for a part search
     */
    public TextField searchFieldPart;
    /**
     * Text field for a product search
     */
    public TextField searchFieldProduct;

    /**
     * Initializes parts table
     */
    private void initializeParts() {
        initializePartsWithSearch(searchFieldPart, partsTable, idColumnPart, nameColumnPart, stockColumnPart, priceColumnPart);
    }

    /**
     * Initializes products table
     */
    private void initializeProducts() {
        ObservableList<Product> products = Inventory.getAllProducts();
        FilteredList<Product> filteredProducts = new FilteredList<>(products, p -> true);

        searchFieldProduct.textProperty().addListener((observable, oldText, newText) -> filteredProducts.setPredicate(product -> {
            if (newText == null || newText.isEmpty()) {
                return true;
            }
            String lowerCaseText = newText.toLowerCase();
            return String.valueOf(product.getId()).contains(lowerCaseText) || product.getName().contains(lowerCaseText);
        }));

        SortedList<Product> sortedData = new SortedList<>(filteredProducts);

        sortedData.comparatorProperty().bind(productsTable.comparatorProperty());


        Property<ObservableList<Product>> propertyProducts = new SimpleObjectProperty<>(filteredProducts);
        productsTable.itemsProperty().bind(propertyProducts);

        PropertyValueFactory<Product, String> idColumnFactoryProduct = new PropertyValueFactory<>("id");
        PropertyValueFactory<Product, String> nameColumnFactoryProduct = new PropertyValueFactory<>("name");
        PropertyValueFactory<Product, String> stockColumnFactoryProduct = new PropertyValueFactory<>("stock");
        PropertyValueFactory<Product, String> priceColumnFactoryProduct = new PropertyValueFactory<>("price");

        idColumnProduct.setCellValueFactory(idColumnFactoryProduct);
        nameColumnProduct.setCellValueFactory(nameColumnFactoryProduct);
        stockColumnProduct.setCellValueFactory(stockColumnFactoryProduct);
        priceColumnProduct.setCellValueFactory(priceColumnFactoryProduct);
    }

    /**
     * Initializes parts and products tables
     */
    @FXML
    private void initialize() {
        initializeParts();
        initializeProducts();
    }

    /**
     * Exists the program after Exit button is clicked
     */
    @FXML
    protected void onExit() {
        sceneController.getStage().close();
    }

    /**
     * Changes scene to Add Part
     */
    @FXML
    protected void onAddPart() {
        sceneController.setScene("part");
        PartController pc = (PartController) sceneController.getController("part");
        pc.setPartID();
    }

    /**
     * Changes view to Modify Part
     */
    @FXML
    protected void onModifyPart() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            errors.setText("The part must be selected");
        } else {
            sceneController.setScene("part-modify");
            PartController pc = (PartController) sceneController.getController("part-modify");
            if (IDGenerator.inHouseIDList.contains(selectedPart.getId())) {
                InHouse part = (InHouse) selectedPart;
                pc.modifyPart(part);
            } else {
                Outsourced part = (Outsourced) selectedPart;
                pc.modifyPart(part);
            }
        }

    }

    /**
     * Clears view from all produced errors
     */
    protected void clearErrors() {
        errors.setText("");
    }

    /**
     * Delete a part from the Inventory
     */
    @FXML
    protected void onDeletePart() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            errors.setText("The part must be selected");
        } else {
            ObservableList<Product> allProducts = Inventory.getAllProducts();
            for (Product p : allProducts) {
                if (p.getAllAssociatedParts().contains(selectedPart)) {
                    errors.setText("Part can not be removed because it is included in the " + p.getName() + " product");
                    return;
                }
            }

            Stage stage = sceneController.getStage();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);
            alert.setTitle("Parts");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
                IDGenerator.inHouseIDList.remove(selectedPart.getId());
                IDGenerator.outsourcedIDList.remove(selectedPart.getId());
            }
            partsTable.refresh();
            clearErrors();
        }
    }

    /**
     * Changes scene to Add Product
     */
    @FXML
    protected void onAddProduct() {
        clearErrors();
        sceneController.setScene("product");
        ProductController pc = (ProductController) sceneController.getController("product");
        pc.setProductID();
    }

    /**
     * Changes scene to Modify Product
     */
    @FXML
    protected void onModifyProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            errors.setText("The product must be selected");
        } else {
            clearErrors();
            sceneController.setScene("product-modify");
            ProductController pc = (ProductController) sceneController.getController("product-modify");
            pc.modifyProduct(selectedProduct);
        }
    }

    /**
     * Deletes a product from the Inventory
     */
    @FXML
    protected void onDeleteProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            errors.setText("The product must be selected");
        } else {
            if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
                errors.setText("Product can not be removed because it includes parts. Remove associated parts first");
                return;
            }
            Stage stage = sceneController.getStage();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);
            alert.setTitle("Products");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
                IDGenerator.productsIDList.remove(selectedProduct.getId());
            }
            clearErrors();
            productsTable.refresh();
        }
    }
}
