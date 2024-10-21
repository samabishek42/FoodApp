package Model;

import java.util.Objects;

public class Dish {
      private int id;
      private String dish_name;
      private String dish_description;
      private int price;
      private byte[] photo;

     public Dish(int id, String dish_name,String dish_description,int price,byte[] photo){
           this.id=id;
           this.dish_name=dish_name;
           this.dish_description=dish_description;
           this.price=price;
           this.photo=photo;
     }
      public Dish(int id, String dish_name,String dish_description,int price){
            this.id=id;
            this.dish_name=dish_name;
            this.dish_description=dish_description;
            this.price=price;
      }


      public int getId() {
            return id;
      }

      public String getDish_name() {
            return dish_name;
      }

      public String getDish_description() {
            return dish_description;
      }

      public int getPrice() {
            return price;
      }

      public byte[] getPhoto() {
            return photo;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dish dish = (Dish) o;
            return id == dish.id;
      }
      @Override
      public int hashCode() {
            return Objects.hash(id);
      }
}
