package Model;

public class User {
      private int id;
      private String name;
      private String email;
      private String mobile;
      private String password;

      public User(){}
      public User(String name,String email,String mobile,String password){
            this.name=name;
            this.email=email;
            this.mobile=mobile;
            this.password=password;
      }

      public int getId() {
            return id;
      }

      public void setName(String name) {
            this.name = name;
      }

      public String getName() {
            return name;
      }

      public void setMobile(String mobile) {
            this.mobile = mobile;
      }

      @Override
      public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", password='" + password + '\'' +
                    '}';
      }

      public String getMobile() {
            return mobile;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public String getPassword() {
            return password;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public String getEmail() {
            return email;
      }
}
