package Model;

public class Admin {
      private int id;
      private String admin_name;
      private String admin_email;
      private String admin_password;

      public Admin(int id,String admin_email,String admin_name,String admin_password) {
            this.id = id;
            this.admin_name=admin_name;
            this.admin_email=admin_email;
            this.admin_password=admin_password;
      }

      public int getId() {
            return id;
      }

      public String getAdmin_email() {
            return admin_email;
      }

      public String getAdmin_name() {
            return admin_name;
      }

      public String getAdmin_password() {
            return admin_password;
      }

}
