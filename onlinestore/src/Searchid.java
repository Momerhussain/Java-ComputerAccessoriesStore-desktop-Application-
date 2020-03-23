
public class Searchid {
   private String name;
   private float price;
   private int quantity;
   private int c_id;
   
   public Searchid(String name,float price,int quantity,int c_id){
       
       this.name=name;
       this.price=price;
       this.quantity=quantity;
       this.c_id=c_id;
       
        }

 public String getname(){
     return name;
     }
 public float getPrice(){
     return price;
      }

 public int getQuantity(){
     return quantity;
 }
 
  public int getC_ID(){
     return c_id;
 }
}
