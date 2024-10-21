package Model;

import java.util.Objects;

public class Restaurant {
      private int id;
      private String name;
      private String cusionType;
      private int deliveryTime;
      private int ratings;
      private String is_active;
      private byte[] photo;

      public Restaurant(int id, String name, String cusionType, int deliveryTime, int ratings, String is_active) {
            this.id = id;
            this.name = name;
            this.cusionType = cusionType;
            this.deliveryTime = deliveryTime;
            this.ratings = ratings;
            this.is_active = is_active;
      }

      public Restaurant(int id, String name, String cusionType, int deliveryTime, int ratings, String is_active, byte[] photo) {
            this.id = id;
            this.name = name;
            this.cusionType = cusionType;
            this.deliveryTime = deliveryTime;
            this.ratings = ratings;
            this.is_active = is_active;
            this.photo = photo;
      }

      public int getId() {
            return id;
      }

      public String getName() {
            return name;
      }

      public int getDeliveryTime() {
            return deliveryTime;
      }

      public int getRatings() {
            return ratings;
      }

      public String getCusionType() {
            return cusionType;
      }

      public String getIs_active() {
            return is_active;
      }

      public byte[] getPhoto() {
            return photo;
      }

      public void setName(String name) {
            this.name = name;
      }

      public void setCusionType(String cusionType) {
            this.cusionType = cusionType;
      }

      public void setDeliveryTime(int deliveryTime) {
            this.deliveryTime = deliveryTime;
      }

      public void setIs_active(String is_active) {
            this.is_active = is_active;
      }

      public void setRatings(int ratings) {
            this.ratings = ratings;
      }

      public void setPhoto(byte[] photo) {
            this.photo = photo;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Restaurant that = (Restaurant) o;
            return id == that.id;
      }

      @Override
      public int hashCode() {
            return Objects.hash(id);
      }
}
