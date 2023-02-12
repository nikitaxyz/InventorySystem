package xyz.bubblesort.c482.utils;

import java.util.HashSet;

/**
 * Provide static methods for generating unique ids for parts and products
 * Can include zero or more <code>Part</code>
 *
 * @author Nikita Kukshynsky
 */
public class IDGenerator {
    /**
     * last used part id
     */
    public static int partID = 0;
    /**
     * last used product id
     */
    public static int productID = 0;
    /**
     * set of all used ids of inHouse part type
     */
    public static HashSet<Integer> inHouseIDList = new HashSet<>();
    /**
     * set of all used ids of outsourced part type
     */
    public static HashSet<Integer> outsourcedIDList = new HashSet<>();
    /**
     * set of all used ids of products
     */
    public static HashSet<Integer> productsIDList = new HashSet<>();

    /**
     * Generates new unique part id
     *
     * @return integer id
     */
    public static int getNextPartID() {
        partID += 1;
        return partID;
    }

    /**
     * Generates new unique product id
     *
     * @return integer id
     */
    public static int getNextProductID() {
        productID += 1;
        return productID;
    }
}
