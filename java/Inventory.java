/**
 *Attempts to simulate an ArrayList inventory based off of InventoryItems
 *
 */
import java.util.ArrayList;
public class Inventory extends InventoryItem{
    String name;
    ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();


    /** Default constructor that sets name of inventory to "INVENTORY"*/
    public Inventory(){
        this.name = "INVENTORY";
    }

    public Inventory(String name){
        this.name = name;
    }

    /**Checks if item is in inventory with find helper method and adds desired quanity.*/
    public void add(InventoryItem item){
        //if item is in inventory edit quantity
        //else add item
        if (this.find(item) != -1){ //checks if the item is in the inventory
            InventoryItem ph = inventory.get(this.find(item));
            ph.quantity = Math.abs(ph.quantity + Math.abs(item.quantity)); //gets sum of different quantites
        }
        else
            inventory.add(item);  //adds the item at the last inxed
    }

    /**Checks if item is in inventory with find helper method and removes desired quanity. If the quantity would become negative it forces it to be zero*/
    public void remove(InventoryItem item){

        if (this.find(item) != -1) //checks if the item is in the inventory AND the item quantity is smaller than this.quantity
        {
            InventoryItem ph = inventory.get(this.find(item));
            int newQuantity = ph.quantity - (item.quantity);
             if (newQuantity > 0)
                 ph.quantity = newQuantity;//subtracts the quantity; if the quantity would become negative it sets it to zero.
            else
                ph.quantity = (0);
        }
    }

    /** Helper method to find index of InventoryItem based off of item code or description; if it finds the item, it returns the index. If the item is not in, returns -1.*/
    public int find(InventoryItem item){
        int index = -1; //NEEDS TO haVE INNDEX
        for (int i=0; i<this.inventory.size(); i++){
            InventoryItem currentItem = this.inventory.get(i); //gets item at ith index
            if((item.itemCode==currentItem.itemCode) ||
                    (item.description.equalsIgnoreCase(currentItem.description))){ //if the itemCode OR description of the ith item is equal to the found item
                index = i;
                break;
            }
            else
                index = -1;
        }
        return index;

    }
    /**calls find method to check for an InventoryItem. If the item exists, returns the item. If not, returns null */
    public InventoryItem checkInventory(InventoryItem item){

        if(this.find(item) != -1) //item is in inventory based on find method
            return inventory.get(this.find(item)); //returns item
        else
            return null;
    }
    /**Uses sort method to list items based on item code */
    public String list(){
        //list items based on itemCode
        sort();
        String list = "";
        for(int i = 0; i < inventory.size(); i++) {
            list += (inventory.get(i)) + "\n"; //creates a string in the format of: "{ITEM}\n"
        }
        return list;
    }
    /**Helper method that uses bubble sort algorithm to sort items based on item code*/
    private void sort(){ //bubble sort method
        int length = inventory.size();
        for (int i=0; i<length-1; i++){
            for (int j=0; j<length-i-1; j++){
                if( (inventory.get(j).itemCode) > (inventory.get(j+1).itemCode)) {//checks if left value is larger than right
                    InventoryItem ph = inventory.get(j);
                    inventory.set(j, inventory.get(j+1));
                    inventory.set(j+1, ph);
                }
            }
        }

    }
    /**Method to check if the Inventory is empty*/
    public boolean isEmpty(){
        return inventory.size()==0;
    }
    /**Gets the sum of all the items in the Inventory*/
    public double getTotal(){ //gets total of price
        double total = 0.0;
        for (int i=0; i<inventory.size(); i++){
            InventoryItem ph = inventory.get(i);
            total += (ph.getTotalPrice()); //get JUST the price and quantity, not the entire item
        }
        return total;
    }
    /** ToString method. Prints in the format of:
     * this.name
     *-----------
     *list()
     *-----------
     *getTotal()
     */
    public String toString(){
        return String.format("%s\n-----------\n%s \n-----------\n Total Value: $%.2f", this.name, list(), getTotal());
    }


}
