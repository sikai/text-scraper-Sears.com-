import java.io.Serializable;
public class Item implements Serializable {
    
    /** assigned serial number */
    private static final long serialVersionUID = 1000L;
    
    /** item name */
    private String name;
    
    /** item price */
    private String price;
    
    /** item vendor */
    private String vendor;
    
    /**
     * Constructor for Item
     * @param name item name.
     * @param price item price.
     * @param vendor item vendor.
     */
    public Item(String name, String price, String vendor) {
        this.name = name;
        this.price = price;
        this.vendor = vendor;
    }

    /**
     * Getter for field name.
     * @return Value of field name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Setter for field name.
     * @param name The value to be set
     * to field name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Getter for field price.
     * @return Value of field price.
     */
    public String getPrice() {
        return price;
    }
    
    /**
     * Setter for field price.
     * @param name The value to be set
     * to field price.
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Getter for field vendor.
     * @return Value of field vendor.
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Setter for field vendor.
     * @param name The value to be set
     * to field vendor.
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    
}
