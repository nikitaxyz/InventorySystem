package xyz.bubblesort.c482.models;

/**
 * <code>InHouse</code> object represents type of part. Extends abstract class <code>Part</code>.
 * @author Nikita Kukshynsky
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructs <code>InHouse</code> class
     * @param id unique ID of a part
     * @param name name of a part
     * @param price price of a part
     * @param stock in stock of a part
     * @param min minimum number of a part
     * @param max maximum number of a part
     * @param machineId machine id of a part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * Setter for a machine id of a part
     * @param machineId integer machine id to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    /**
     * Getter for a machine id field
     * @return integer machine id
     */
    public int getMachineId() {
        return machineId;
    }
}
