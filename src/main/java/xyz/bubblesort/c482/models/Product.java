
package xyz.bubblesort.c482.models;

import javafx.collections.ObservableList;

/**
 * A <code>Product</code> object represents a product from the inventory.
 * Can include zero or more <code>Part</code>
 *
 * @author Nikita Kukshynsky
 */
public class Product {
    private final ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructs <code>Product</code> object
     * @param associatedParts zero or more <code>Part</code>
     * @param id unique ID of a product
     * @param name name of a product
     * @param price price of a product
     * @param stock in stock of a product
     * @param min minimum number of a product
     * @param max maximum number of a product
     */
    public Product(ObservableList<Part> associatedParts, int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = associatedParts;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Setter for an id field
     * @param id integer id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Setter for a name field
     * @param name String name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Setter for a price field
     * @param price double price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Setter for a stock field
     * @param stock integer stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * Setter for a min field
     * @param min integer min to set
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * Setter for a max field
     * @param max integer max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Getter for an id field
     * @return integer id of a Product
     */
    public int getId() {
        return id;
    }
    /**
     * Getter for a name field
     * @return String name of a Product
     */
    public String getName() {
        return name;
    }
    /**
     * Getter for a price field
     * @return double price of a Product
     */
    public double getPrice() {
        return price;
    }
    /**
     * Getter for a stock field
     * @return integer stock of a Product
     */
    public int getStock() {
        return stock;
    }
    /**
     * Getter for a min field
     * @return integer min of a Product
     */
    public int getMin() {
        return min;
    }
    /**
     * Getter for a max field
     * @return integer max of a Product
     */
    public int getMax() {
        return max;
    }

    /**
     * Add new <code>Part</code> to a Product
     * @param part <code>Part</code> to be added
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    /**
     * Removes <code>Part</code> from a Product
     * @param selectedAssociatedPart <code>Part</code> to be removed
     * @return true if part has been deleted false otherwise
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Getter for all parts of a Product
     * @return parts of a Product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
