/************************************************
 *User Interface utilizing Inventory and InventoryItem
 * Six different options; all typesafe
 * a: Print out inventory
 * b: Add item from user input
 * c: Adjust quantity of existing item
 * d: Import items from a formatted file
 * e: Ask uer to buy items at a 1.2x markup and exports their cart as a .txt file
 * q: Stops program
 *
 * *********************************************/
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
class Main {
    public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(System.in);
        /* TESTING QUANTITY VALUE
        InventoryItem test = new InventoryItem(1, "NAME", 20, -99.9);
        System.out.println(test.getQuantity());
        test.setQuantity(test.quantity-9);
        System.out.println(test.getQuantity());
        test.setQuantity(test.quantity-99);
        System.out.println(test.getQuantity());
        */

        Inventory shop = new Inventory();
        char menu = ' '; //for proper inpput

/**Menu interface that accepts 'a', 'b','c', 'd', 'e', and 'q' as input; if the input is different it tells the user and re-prompts*/
        System.out.println("Enter menu call\na: Show inventory\nb: add item\nc: Adjust quantity\nd: Import from text\ne: Create invoice\nq: Quit");
        while (!(menu=='a'|menu=='b'|menu=='c'|menu=='d'|menu=='q'|menu=='e')){ //loop goes on until proper input is put in, which in this case is a,b,c,d,e, or q
            System.out.println("Enter a, b, c, d, e, or q");
            menu = scan.next().toLowerCase().charAt(0); //just gets first letter lowercase

            if(!(menu=='a'|menu=='b'|menu=='c'|menu=='d'|menu=='q'|menu=='e')){ //if menu is invalid, it will tell user and prompt user to input in again
                System.out.printf("%s is not valid input.\n",menu);
            }
            scan.nextLine();//scanner bug


            //MENUS WILL ONLY RUN IF THE INPUT IS PROPER
/** Prints inventory based off of Inventory's toString method*/
            if (menu == 'a'){ //show inventory
                System.out.println(shop);
                menu = ' ';
            }


            /** Allows user to add item to shop in the order of item code, description, quantity, and price*/
            if (menu == 'b'){//add item
                System.out.println("Please put in an item code");
                while (!scan.hasNextInt()){ //checks if input is an int. if not, it tells the user it's not an int and demands another input
                    String bad = scan.nextLine(); //reads invalid input as string and prompts user to put in input again
                    System.out.printf("%s is invalid. Please enter an integer.\n", bad);
                }
                int itemCode = scan.nextInt();
                scan.nextLine();

                System.out.println("Description");
                String description = scan.nextLine();

                System.out.println("Quantity");
                while (!scan.hasNextInt()){ //recycled from before
                    String bad = scan.nextLine();
                    System.out.printf("%s is invalid. Please enter an integer.\n", bad);
                }
                int quantity = scan.nextInt();
                if (quantity<0)
                    System.out.printf("%d is negative; quantity set to zero.\n", quantity); //tells the user that if the input is negative quantity is set to zero
                scan.nextLine();

                System.out.println("Price");
                while (!scan.hasNextDouble()){ //recycled from before
                    String bad = scan.nextLine();
                    System.out.printf("%s is invalid. Please enter a double.\n", bad);
                }
                double price = scan.nextDouble();
                scan.nextLine();

                InventoryItem item = new InventoryItem(itemCode, description, quantity, price);
                shop.add(item);
                System.out.println(item);
                menu = ' ';
            }


            /**Changes quantity of items based on add and remove methods from Inventory class. Prompts user to check based on item code and if they want to add or remove*/
            if (menu == 'c'){ //adjust quantity
                System.out.println("Please put in an item code");
                while (!scan.hasNextInt()){
                    String bad = scan.nextLine();
                    System.out.printf("%s is invalid. Please enter an integer.\n", bad);
                }
                int itemCode = scan.nextInt();
                scan.nextLine();

                InventoryItem ph = new InventoryItem(itemCode, "", 0,0.0);
                InventoryItem temp = shop.checkInventory(ph); //stores the inventory item as a temporay full item
                if (temp != null){
                    char menuC = ' ';
                    while (!((menuC=='+')|(menuC=='-'))){ //prompts user to get typesafe add or subtrace
                        System.out.println("Do you want to add or remove? '+' for add, '-' for remove");
                        menuC = scan.next().charAt(0);
                        if ( (!((menuC=='+')|(menuC=='-')))){
                            System.out.printf("%s is not valid input.\n",menuC);
                        }
                        scan.nextLine();
                    }

                    System.out.println("Please put in a quantity"); //gets typesafe quantity
                    while (!scan.hasNextInt()){
                        String bad = scan.nextLine();
                        System.out.printf("%s is invalid. Please enter an integer.\n", bad);
                    }
                    int quant = Math.abs(scan.nextInt()); //gets the absolute value of the quantity

                    if (menuC=='+'){ //adds
                        ph.quantity = quant;
                        shop.add(ph);
                    }
                    if (menuC=='-'){ //subtracts, but does not go past zero
                        if (quant>temp.quantity)
                            System.out.printf(" %d more subtracted than there is; set quantity to 0\n", (quant-temp.quantity)); //displays if quant is larger than current quantity
                        ph.quantity = quant;
                        shop.remove(ph);
                    }
                    System.out.println(temp); //prints out new item's list
                }
                else
                {
                    System.out.println("Item not found");
                }
                menu = ' ';
            }


            /**Imports items from a text file*/
            if (menu == 'd'){ //import from text

        /* ASSUMPTIONS: FILE IS FORMATTED TO IGNORE FIRST TWO LINES AND TO READ an int, String, int, and double from each line. Sample:
          Name //DISCARDED
          ***** //DISCARDED
          int String int double
        */
                System.out.println("Please enter the name of the file you want to read, assuming the file is a .txt file.");
                String fileName = scan.nextLine() + ".txt"; //gets name from user; assumes that the file is a .txt file
                File readTo = new File (fileName);
                Scanner read = new Scanner(readTo);
                //ignores first two line, which do no contain items
                read.nextLine();
                read.nextLine();
                while (read.hasNext()){ //GETS INFO FROM LINE AND LOOPS FOR EACH TIME; NOT FINISHED. GOT IT TO FIND THE VALUES OF NUMBERS, BUT UNSIRE HOW TO GET STRINGS AND TO IGNORE FIRST TWO LINES

                    int itemCode = read.nextInt();
                    String description = read.next();
                    int quantity = read.nextInt();
                    double price = read.nextDouble();

                    InventoryItem item = new InventoryItem(itemCode, description, quantity, price);
                    shop.add(item);
                    System.out.println(item);
                }

                read.close();
                menu = ' ';
            }


            /**Simulates a shopping cart wit 1.2x markup and prints to a txt file. Tells the user to come back if the inventory is empty and able to exit at any time by typing "quit"*/
            if (menu == 'e'){ //creates invoice for item

                if(shop.isEmpty()) //nothing in shop
                    System.out.println("You have nothing in your inventory");
                else
                {
                    //gets name of user and creates file to write to
                    System.out.println("Please enter your name");
                    String name = scan.nextLine();
                    FileWriter writer = new FileWriter("buyer"+ name + "invoice.txt");
                    Inventory cart = new Inventory(name);
                    String itemDesc="";

                    while (!itemDesc.equalsIgnoreCase("quit")&&(!shop.isEmpty())){ //shop has something AND input is not "quit"
                        System.out.println("What is the name of the item you want to add? Or type 'quit' to exit");
                        itemDesc = scan.nextLine();

                        //creates InventoryItem for cart
                        InventoryItem ph = new InventoryItem(0, itemDesc, 0, 0.0);
                        InventoryItem temp = shop.checkInventory(ph);


                        if (temp != null){ //checks if the item exists and adds it to cart at 1.2x price
                            InventoryItem cartItem = new InventoryItem(temp.itemCode,temp.description, temp.quantity, temp.price);
                            cartItem.price =(temp.price*1.2);
                            cart.add(cartItem);
                            System.out.println(cartItem);
                        }
                        else if (!itemDesc.equalsIgnoreCase("quit")) //item does not exist and input is not "quit"
                            System.out.println(itemDesc + " is not in our inventory.");
                    }


                    System.out.println(cart);
                    writer.write(cart.toString()); //writes invoice to file
                    writer.close();
                }

                menu = ' ';
            }


            /**Exits program*/
            if (menu == 'q'){ //quit
                System.out.println("Goodbye!");
                scan.close();
                System.exit(0);
            }
        }
    }
}