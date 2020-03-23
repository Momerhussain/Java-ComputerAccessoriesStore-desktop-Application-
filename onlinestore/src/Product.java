public class Product {
   private int id;
   private String name;
   private float price;
   private String addDate;
   private byte[] picture;
   
   public Product(int pid,String pname,float pprice,byte[] pimg){
       this.id=pid;
       this.name=pname;
       this.price=pprice;
       
       this.picture=pimg;
        }
 public int getId(){
      return id;
       }
 public String getname(){
     return name;
     }
 public float getPrice(){
     return price;
      }

 public byte[] getImage(){
     return picture;
 }        
}
