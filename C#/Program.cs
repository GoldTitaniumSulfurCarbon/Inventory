using System;
using System.Numerics;
using System.IO;
using static System.Console;
using System.Text;

namespace Inventory
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Inventory shop = new Inventory();
            char menu = ' ';

            WriteLine("Enter menu call\na: Show inventory\nb: add item\nc: Adjust quantity\nd: Import from text\ne: Create invoice\nq: Quit");
            while (!(menu == 'a' | menu == 'b' | menu == 'c' | menu == 'd' | menu == 'q' | menu == 'e'))
            { //loop goes on until proper input is put in, which in this case is a,b,c,d,e, or q
                WriteLine("Enter a, b, c, d, e, or q\n");
                menu = Char.ToLower(ReadKey().KeyChar);
                WriteLine("\n");
                if (!(menu == 'a' | menu == 'b' | menu == 'c' | menu == 'd' | menu == 'q' | menu == 'e'))
                { //if menu is invalid, it will tell user and prompt user to input in again
                    WriteLine($"{menu} is not valid input.");
                }
                if (menu == 'a')
                { //show inventory
                    WriteLine(shop);
                    menu = ' ';
                }
                if (menu == 'b')
                {


                    int itemCode =0 , quantity =0;
                    decimal price = 0;
                    bool nonString = false;
                    string error = "";
                    //Typesafe input for all the inputs of the user. For ItemCode, while the constructor does give an error, the main forces the user to keep within bounds of sufficient itemCode
                    while (!nonString)
                    {
                       
                        WriteLine("Please put in an item code"+ error);
                        string input = ReadLine();
                        nonString = int.TryParse(input, out itemCode);
                        if (!nonString)
                        {
                            Write($"{input} is not a number.\n");
                            error = "";
                        }
                        else if ((itemCode > 99999999) || (itemCode < 0))
                        {
                            Write($"{input} is out of bounds.\n");
                            nonString = false;
                            error = " that's within the range of 0-99999999";
                        }
                        if (nonString)
                        {
                            InventoryItem ph = new InventoryItem(itemCode, "", 0, 0M);
                            if (shop.Find(ph) != -1)
                            {
                                WriteLine("Item code is already taken");
                                nonString = false;
                                error = " that's not already taken";
                            }
                        }
                        
                    }

                    WriteLine("Description");
                    string description = ReadLine();

                    nonString = false;
                    while (!nonString)
                    {
                        WriteLine("Quantity");
                        string input = ReadLine();
                        nonString = int.TryParse(input, out quantity);
                        if (!nonString)
                            Write($"{input} is not an integer.\n");
                        else
                            nonString = true;
                        if (quantity < 0)
                            Write($"{quantity} is negative; setting quantity to zero\n");
                    }

                    nonString = false;
                    while (!nonString)
                    {
                        WriteLine("Price");
                        string input = ReadLine();
                        nonString = Decimal.TryParse(input, out price);
                        if (!nonString)
                            Write($"{input} is not a number.\n");
                        else
                            nonString = true;
                        if (price < 0)
                            Write($"{price} is negative; setting price to its absolute value\n");
                    }
                    InventoryItem newItem = new InventoryItem(itemCode, description, quantity, price);
                    WriteLine(newItem);
                    shop.Add(newItem);
                    menu = ' ';
                }

                if (menu == 'c')
                {
                    int itemCode =0;
                    bool nonString = false;
                    //Checks for a valid itemcode
                    while (!nonString)
                    {
                        WriteLine("Please put in an item code\n");
                        string input = ReadLine();
                        nonString = int.TryParse(input, out itemCode);
                        if (!nonString)
                            Write($"\n{input} is not a number.\n");
                        else if ((itemCode > 99999999) || (itemCode < 0))
                        {
                            Write($"\n{input} is out of bounds.\n");
                            nonString = false;
                        }
                    }
                    //Creates two different items to help change desired quantity
                    InventoryItem ph = new InventoryItem(itemCode, "", 0, 0M);
                    InventoryItem temp = shop.CheckInventory(ph);
                    if (temp != null) //If the item is in inventory
                    {
                        char menuC = ' ';
                        while (!((menuC == '+') | (menuC == '-')))
                        { //prompts user to get typesafe add or subtrace
                            WriteLine("Do you want to add or remove? '+' for add, '-' for remove\n");
                            menuC = Char.ToLower(ReadKey().KeyChar);
                            if ((!((menuC == '+') | (menuC == '-'))))
                            {
                                WriteLine($"\n{menuC} is not valid input.\n");
                            }
                        }

                       
                        int quantity = 0;
                        nonString = false;
                        while (!nonString)
                        {
                            WriteLine("\nPlease put in a quantity\n");
                            string input = ReadLine();
                            nonString = int.TryParse(input, out quantity);
                            if (!nonString)
                                Write($"\n{input} is not an integer.\n");
                            else
                                nonString = true;

                            if (quantity < 0)
                                Write($"\n{quantity} is negative; setting quantity to absolute\n");
                        }
                        if (menuC == '+')
                        { //adds
                            ph.Quantity = Math.Abs(quantity);
                            shop.Add(ph);
                        }
                        if (menuC == '-')
                        { //subtracts, but does not go past zero
                            if (quantity > temp.Quantity)
                                WriteLine("\n{0} more subtracted than there is; set quantity to 0\n", (quantity - temp.Quantity)); //displays if quant is larger than current quantity
                            ph.Quantity = quantity;
                            shop.remove(ph);
                        }
                        WriteLine(temp);
                    }
                    else //Item is not in inventory
                    {
                        WriteLine("Item not found");
                    }

                    menu = ' ';
                }

                if (menu == 'd')
                {
                    WriteLine("Please put in the pathway of the file you want to import.\nExample: C:\\Users\\Owner\\File\\shipment.txt");
                    string path = ReadLine(); //can access file, but how to extract int for each one with formatting?
                    if (File.Exists(path))
                    {
                        StreamReader reader = new StreamReader(path);
                        using (reader)
                        {
                           
                            reader.ReadLine();
                            reader.ReadLine();
                            string line = reader.ReadLine();
                            //ignores first two lines
                            while (line != null)
                            {
                                string[] split = line.Split(' ', StringSplitOptions.RemoveEmptyEntries);

                                int itemCode = int.Parse(split[0]); //itemcode is 1st item, or 0th index
                                string description = split[1].Replace('_', ' '); //2nd item, 1st index; replaces underscores which represent spaces in the file with actual spaces
                                int quantity = int.Parse(split[2]); //3rd item, 2nd index
                                decimal price = decimal.Parse(split[3]); //4th item, 3rd index
                                line = reader.ReadLine();
                                InventoryItem item = new InventoryItem(itemCode, description, quantity, price);
                                WriteLine(item);
                                shop.Add(item);
                            }
                        }

                    }
                    else
                        WriteLine("Path does not exist");
                    menu = ' ';
                    

                }

                if (menu == 'e')
                {
                    if (shop.IsEmpty())
                        WriteLine("You have nothing in the shop");
                    else
                    {
                        WriteLine("Please enter your name\n");
                        string name = ReadLine();
                        string path = "E:\\\\c# src\\\\Inventory\\\\Inventory\\\\Buyer Invoice\\\\buyer" + name + "invoice.txt";
                        StreamWriter writer = new StreamWriter(path);

                        Inventory cart = new Inventory(name);
                        string itemDesc = "";
                        using (writer)
                        {
                            while (!(string.Equals(itemDesc, "quit", StringComparison.OrdinalIgnoreCase)) && (!shop.IsEmpty())) //input is either NOT equal to "quit", ignoring case differences, or shop has at least one item in
                            { //shop has something AND input is not "quit"
                                WriteLine("What is the name of the item you want to add? Or type 'quit' to exit");
                                itemDesc = ReadLine();

                                InventoryItem ph = new InventoryItem(0, itemDesc, 0, 0.0M);
                                InventoryItem temp = shop.CheckInventoryString(ph);


                                if (temp != null)
                                { //checks if the item exists and adds it to cart at 1.2x price
                                    InventoryItem cartItem = new InventoryItem(temp.ItemCode, temp.Description, temp.Quantity, temp.Price);
                                    cartItem.Price = (temp.Price * 1.2M);
                                    cart.Add(cartItem);
                                    WriteLine(cartItem);
                                }
                                else if (!(string.Equals(itemDesc, "quit", StringComparison.OrdinalIgnoreCase)))
                                    WriteLine(itemDesc + " is not in our inventory.");

                            }
                            writer.Write(cart.ToString());
                        }
                        WriteLine("Invoice has been printed to: \"E:\\\\c# src\\\\Inventory\\\\Inventory\\\\Buyer Invoice\\\\buyer\\" + name + "\\invoice.txt\"");
                        WriteLine(cart);
                    }
                    menu = ' ';
                }

                if (menu == 'q')
                {
                    WriteLine("Goodbye!");
                    Environment.Exit(0);
                }

            }
        }
    }
}