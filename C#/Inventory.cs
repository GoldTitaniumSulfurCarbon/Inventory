using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Metadata.Ecma335;
using System.Text;
using System.Threading.Tasks;

namespace Inventory
{
    internal class Inventory
    {
        
        public string Name { get; private set; }
        List<InventoryItem> inventory = new List<InventoryItem>();

        public Inventory()
        {
            this.Name = "INVENTORY";
        }
        public Inventory(String name)
        {
            this.Name = name;
        }

        public void Add(InventoryItem item) 
        {
            if (this.Find(item) != -1)
            {
                InventoryItem ph = inventory[this.Find(item)];
                ph.Quantity = Math.Abs(ph.Quantity + Math.Abs(item.Quantity));
            }
            else
                inventory.Add(item);
        }

        public void remove(InventoryItem item)
        {
            if (this.Find(item) != -1)
            {
                InventoryItem ph = inventory[this.Find(item)];
                int newQuantity = ph.Quantity-item.Quantity;
                if (newQuantity > 0)
                    ph.Quantity = newQuantity;
                else
                    ph.Quantity = 0;
            }
        }


        public int Find(InventoryItem target)
        {
            int index = -1;
           foreach (InventoryItem item in inventory) {
                if (item.ItemCode == target.ItemCode)
                {
                    index = inventory.IndexOf(item);
                    break;
                }
                else
                    index = -1;

            }
            return index;
        }

        public int FindName(InventoryItem target)
        {
            int index = -1;
            foreach (InventoryItem item in inventory)
            {
                if (string.Equals(item.Description, target.Description, StringComparison.OrdinalIgnoreCase))
                {
                    index = inventory.IndexOf(item);
                    break;
                }
                else
                    index = -1;

            }
            return index;
        }

        public InventoryItem CheckInventory(InventoryItem target)
        {
            int targetIndex = this.Find(target);
            if (targetIndex != -1)
                return inventory[targetIndex];
            else
                return null;
        }
        public InventoryItem CheckInventoryString(InventoryItem target)
        {
            int targetIndex = this.FindName(target);
            if (targetIndex != -1)
                return inventory[targetIndex];
            else
                return null;
        }

        public string List()
        {
            Sort();
            StringBuilder sb = new StringBuilder();
            foreach (InventoryItem item in inventory)
            {
                sb.Append(inventory[inventory.IndexOf(item)] + "\n");
            }
            return sb.ToString();

        }


        private void Sort()
        {
            int length = inventory.Count;
            for (int i=0; i<length-1; i++) 
            {
                for (int j=0; j<length-i-1; j++)
                {
                    if ((inventory[j].ItemCode) > inventory[j + 1].ItemCode)
                    {
                        InventoryItem ph = inventory[j];
                        inventory[j] = inventory[j + 1];
                        inventory[j + 1] = ph;
                    }
                }
            }
        }
        public bool IsEmpty() { return inventory.Count == 0; }

        public decimal GetTotal()
        {
            decimal total = 0;
            foreach  (InventoryItem item in inventory)
            {
                InventoryItem ph = item;
                total += ph.GetTotalPrice();
            }
            return total;
        }


        public override string ToString()
        {
            return string.Format($"{this.Name}\n-----------\n{List()} \n-----------\n Total Value: ${GetTotal()}");
        }

    }
}
