
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class storedata {
      public storedata() throws SQLException{
       try{    
//          Class.forName("org.apache.derby.jdbc.ClientDriver");
          Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/store","omer","omer");
              System.out.println("successful");
          }
       catch(Exception d){
               System.out.println("error");
       }
       }
   public static void main(String[] args) throws SQLException {
        storedata a1=new storedata();
    }
     
}
