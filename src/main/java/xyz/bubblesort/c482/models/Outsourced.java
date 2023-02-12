package xyz.bubblesort.c482.models;

/**
 * <code>Outsourced</code> object represents type of part. Extends abstract class <code>Part</code>.
 * @author Nikita Kukshynsky
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructs <code>Outsourced</code> class
     * @param id unique ID of a part
     * @param name name of a part
     * @param price price of a part
     * @param stock in stock of a part
     * @param min minimum number of a part
     * @param max maximum number of a part
     * @param companyName name of the company that produced a part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter for a name of the company that produced a part
     * @param companyName String company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter for a company name field
     *
     * @return String company name
     */
    public String getCompanyName() {
        return companyName;
    }
}