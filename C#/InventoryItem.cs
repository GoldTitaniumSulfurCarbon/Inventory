using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Inventory
{
    internal class InventoryItem
    {
        private int itemCode;
        private string description;
        private int quantity;
        private decimal price;

        public int ItemCode
        { get; set; } 
        public string Description { get; set; }


        public int Quantity  { get { return this.quantity; }
            set
            {
                if (quantity >= 0)
                    this.quantity = value;
                else
                    this.quantity = 0;
            }
        }
            
        
        public decimal Price
        {
            get { return this.price; }
            set { this.price = Math.Abs(value); }
        }
        public InventoryItem() //default
        {
            ItemCode = 99999999;
            Description = "No description";
            Quantity = 0;
            Price = 0.0M;
        }
        public InventoryItem(int ItemCode, string Description, int Quantity, decimal Price)
        {
            if ((ItemCode > 99999999)||(ItemCode<0))
                throw new ArgumentOutOfRangeException(nameof(ItemCode), $"{ItemCode} is out of bounds for valid code. It must be between 0-99999999");


            this.ItemCode = ItemCode;
            this.Description = Description;
            if (Quantity > 0)
                this.Quantity = Quantity;
            else
                this.Quantity = 0;
            this.Price = Math.Abs(Price);
        }

        public decimal GetTotalPrice()
        {
            return price * quantity;
        }

        public override string ToString()
        {
            return string.Format($"{ItemCode} - {Description} {Quantity} @ ${Price:F2} = ${GetTotalPrice():F2}");
        }
    }
}
