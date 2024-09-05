/************************************************
 *Inventory Class<BR>
 *This class is for creating an inventory item for a shop overlay.
 *
 *
 * *********************************************/
public class InventoryItem{
    /** Class variables*/
    public int itemCode;
    public String description;
    public int quantity;
    public double price;

    /**Default constructor; sets item code to highest possible and sets description to "No description", with a quantity and price of 0*/
    public InventoryItem(){
        itemCode = 99999999;
        description = "No description";
        quantity = 0;
        price = 0.0;
    }
    /**Constructor that sets item code within the range of 0-99999999, a custom description, an integer with a minimum of zero,  and double forced to be nonnegative*/
    public InventoryItem(int itemCode, String description, int quantity, double price){
        this.itemCode = Math.abs(itemCode%100000000);
        this.description = description;
        if(quantity<0)
            this.quantity = 0;
        else
            this.quantity = quantity;
        this.price = Math.abs(price);
    }

    /**Getters*/
    public int getItemCode(){
        return itemCode;
    }
    public String getDescription(){
        return description;
    }
    public int getQuantity(){
        return quantity;
    }
    public double getPrice(){
        return price;
    }
    /** Gets total price by multiplying price of item by quantity */
    public double getTotalPrice(){
        return price*quantity;
    }
    //setters

    /** Sets itemcode and forces it to be between 0-999999999 */
    public void setItemCode(int itemCode){
        this.itemCode = Math.abs(itemCode%100000000);
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setQuantity(int quantity){
        if(quantity<0)
        this.quantity = 0;
    else
        this.quantity = quantity; }
    /**Sets price and forces it to be nonnegative*/
    public void setPrice(double price){
        this.price = Math.abs(price);
    }

    /** Prints out in the format of:
     *Itemcode - descripion @ $quantity = getTotalPrice()
     */
    public String toString(){
        return String.format("%d - %s %d @ $%.2f = $%.2f",itemCode, description, quantity, price, getTotalPrice());
    }

}