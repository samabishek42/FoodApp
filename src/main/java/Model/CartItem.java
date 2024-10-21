package Model;

public class CartItem {

      private int itemId;

      private int restaurantId;

      private String name;

      private int quantity;

      private double price;

      public CartItem(int itemId,int restaurantId,String name,int quantity,double price){
            this.itemId=itemId;
            this.restaurantId=restaurantId;
            this.name=name;
            this.quantity=quantity;
            this.price=price;
      }

      public String getName() {
            return name;
      }

      public double getPrice() {
            return price;
      }

      public int getItemId() {
            return itemId;
      }

      public int getQuantity() {
            return quantity;
      }

      public int getRestaurantId() {
            return restaurantId;
      }

      public void setName(String name) {
            this.name = name;
      }

      public void setItemId(int itemId) {
            this.itemId = itemId;
      }

      public void setPrice(double price) {
            this.price = price;
      }

      public void setQuantity(int quantity) {
            this.quantity = quantity;
      }

      public void setRestaurantId(int restaurantId) {
            this.restaurantId = restaurantId;
      }
}
