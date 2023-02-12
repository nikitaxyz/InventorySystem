package xyz.bubblesort.c482.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Util static class that helps to keep all <code>Part</code> and <code>Products</code> created in the program
 *
 * @author Nikita Kukshynsky
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds new part to all parts
     *
     * @param newPart the part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds new product to all products
     *
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Checks if partId exists among all parts
     *
     * @param partId the id to find
     * @return part matching id
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Checks if productId exists among all products
     *
     * @param productId the id to find
     * @return product matching id
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Finds part by part name
     *
     * @param partName the part name to find
     * @return part matching name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchedParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                matchedParts.add(part);
            }
        }
        return matchedParts;
    }

    /**
     * Finds product by product name
     *
     * @param productName the product name to find
     * @return product matching name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchedProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                matchedProducts.add(product);
            }
        }
        return matchedProducts;
    }

    /**
     * Updates part at the specified index
     *
     * @param index        the index in allParts
     * @param selectedPart the part to update
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates product at the specified index
     *
     * @param index           the index in allProducts
     * @param selectedProduct the product to update
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Removes selected part from allParts
     *
     * @param selectedPart the part to remove
     * @return true if part has been deleted otherwise false
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes selected product from allProducts
     *
     * @param selectedProduct the product to remove
     * @return true if product has been deleted otherwise false
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Getter for all parts in the inventory
     *
     * @return Observable list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Getter for all products in the inventory
     *
     * @return Observable list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
