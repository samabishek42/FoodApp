package Model;

public class OrderItems {

      private String dish_name;

      private int quantity;

      private String date;

      private int subtotal;

      private String restaurantName;

      public OrderItems(String dish_name,int quantity,String date,int subtotal,String restaurantName) {
            this.dish_name = dish_name;
            this.date=date;
            this.quantity=quantity;
            this.subtotal=subtotal;
            this.restaurantName=restaurantName;
      }

      public OrderItems() {
      }

      public void setQuantity(int quantity) {
            this.quantity = quantity;
      }

      public void setDate(String date) {
            this.date = date;
      }

      public void setDish_name(String dish_name) {
            this.dish_name = dish_name;
      }

      public void setSubtotal(int subtotal) {
            this.subtotal = subtotal;
      }

      public int getQuantity() {
            return quantity;
      }

      public String getDish_name() {
            return dish_name;
      }


      public int getSubtotal() {
            return subtotal;
      }

      public String getDate() {
            return date;
      }

      public String getRestaurantName() {
            return restaurantName;
      }

      public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
      }
}
