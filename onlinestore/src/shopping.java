
import com.sun.xml.internal.bind.v2.model.core.ID;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pc
 */
public class shopping extends javax.swing.JFrame {

    /**
     * Creates new form shopping
     */
    public shopping() {
        initComponents();
         Show_Product_In_JTable();
         Show_Product_In_JTable2();
       //  Show_Product_In_JTable3();
         hold();
        // jLabel6.setText(Integer.toString(getSum()));
         
    }

    ResultSet rs=null;
    String ImgPath=null;
int pos=0;
static double holdsum=0;
static double dis=0;
    

void hold(){
        
          cutomer obj=new cutomer();  
          int a=obj.c_id;
          // jLabel9.setText(c_id);
          jLabel9.setText(""+a);
     }

    
    
public Connection getConnection(){
        Connection con =null;      
        try{
        Class.forName("org.apache.derby.jdbc.ClientDriver");
         con=DriverManager.getConnection("jdbc:derby://localhost:1527/store","omer","omer");
            // JOptionPane.showMessageDialog(null ,"connected");
             return con;
                }
                catch(Exception d){
                    System.out.println("error");
                return null;   
                }
    
    }
        
        public boolean checkInputs(){
        if(name.getText()==null || price.getText()==null)
            
        
        {
            return false;
        }
        else{
                try{
                Float.parseFloat(price.getText());
                return true;
                }
                catch(Exception d){
                return false;
                }
    }
            
    }
        
    public ImageIcon ResizeImage(String imagePath,byte[] pic){
        ImageIcon myImage =null;
        if(imagePath!=null){
            myImage=new ImageIcon(imagePath);
        }
        else{
            myImage=new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image img2=img.getScaledInstance(image.getWidth(),image.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon image=new ImageIcon(img2); 
        return image;
    }
      
    /////for jtable products
   public ArrayList<Product> getProductList(){
        ArrayList<Product> productList = new ArrayList<Product>();
        Connection con = getConnection();
        String query = "Select * FROM product";
        Statement st;
        ResultSet rs;
        
        try{
  
        st = con.createStatement();
        rs = st.executeQuery(query);
        Product product;
        while(rs.next()){
            product = new Product(rs.getInt("id"),rs.getString("name"),Float.parseFloat(rs.getString("price")),rs.getBytes("image"));
            productList.add(product);
        }
        
    }
        catch(Exception e){
                System.out.println("sql error");
                }
    
        return productList;
        
    } 
    
        
    public void Show_Product_In_JTable(){
    ArrayList<Product> list = getProductList();
     DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    //clear jtable content
    model.setRowCount(0);
    Object[] row = new Object[3];
    for(int i=0; i<list.size();i++){
        row[0]=list.get(i).getId();
        row[1]=list.get(i).getname();
        row[2]=list.get(i).getPrice();
        
        model.addRow(row);
    }
    
    }     
   public void ShowItem(int index ){
 
       txt_ID.setText(Integer.toString(getProductList().get(index).getId()));
       name.setText(getProductList().get(index).getname());
       price.setText(Float.toString(getProductList().get(index).getPrice()));
      image.setIcon(ResizeImage(null,getProductList().get(index).getImage()));
   }
   
   
   
   ////////////////////////////////////////////////////////////////////
   //display data in Jtable
    //fill the ArrayList with data
   ///Invoice Table
    public ArrayList<Cart> getCartList(){
        ArrayList<Cart> cartList = new ArrayList<Cart>();
        Connection con = getConnection();
        String query = "Select * FROM cart where customerid=(select max(customerid) from cart)";
       // ps.setString(4, jLabel9.getText());
        Statement st;
        ResultSet rs;
        
        try{
  
        st = con.createStatement();
        rs = st.executeQuery(query);
        Cart cart;
        while(rs.next()){
            cart = new Cart(rs.getString("name"),Float.parseFloat(rs.getString("price")),
                    rs.getInt("quantity"),rs.getInt("customerid"));
            cartList.add(cart);
        }
        
    }
        catch(Exception e){
                System.out.println("sql error");
                }
    
        return cartList;
        
    } 
  
    //2 populate the Jtable

    public void Show_Product_In_JTable2(){
    ArrayList<Cart> list = getCartList();
  //  ArrayList<Customer> list1 = getCustomerList();
   // DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
    //clear jtable content
    model2.setRowCount(0);
  // model.setRowCount(0);
    Object[] row = new Object[4];
    //Object[] row2 = new Object[4];
    for(int i=0; i<list.size();i++){
        
        row[0]=list.get(i).getname();
        row[1]=list.get(i).getPrice();
        row[2]=list.get(i).getQuantity();
        row[3]=list.get(i).getC_ID();
    //    row[3]=list1.get(i).getID();
        model2.addRow(row);
        //model.addRow(row);
    }
    
    } 
    
  
    
    //////////////////////////////////////////////////////////////
    
  
   public void ShowItem2(int index ){
 
      
       name.setText(getCartList().get(index).getname());
       price.setText(Float.toString(getCartList().get(index).getPrice()));
       quantity.setText(Integer.toString(getCartList().get(index).getQuantity()));
      
   }
   ////////////////////////////////////////////////////////////
  
  
   
   /////////////////////////////////////////////////////////////////////////////
///calculation
      public double getSum(){
       int rowsCount=jTable2.getRowCount();
       double sum=0;
       for(int i=0;i<rowsCount;i++){
           sum=sum+Double.parseDouble(jTable2.getValueAt(i,1).toString());
            holdsum=sum*0.85;
             jLabel11.setText(""+holdsum);
           //  dis=(holdsum*(0.86));
       }
       return sum;
   }
      public void dis(){
        holdsum=Integer.parseInt(jLabel6.getText());
      //  dis=(holdsum*(0.86));
       
      }
   ///////////////////////////////////////////////////////////////
      ///testing
      /* public ArrayList<Customer> getCustomerList(){
        ArrayList<Customer> productList = new ArrayList<Customer>();
        Connection con = getConnection();
        String query = "Select * FROM customer";
        Statement st;
        ResultSet rs;
        
        try{
  
        st = con.createStatement();
        rs = st.executeQuery(query);
        Customer customer;
        while(rs.next()){
            customer = new Customer(rs.getInt("id"));
            productList.add(customer);
           
        }
        
    }
        catch(Exception e){
                System.out.println("sql error");
                }
    
        return productList;
        
    } 
      
    public void Show_Product_In_JTable3(){
    ArrayList<Customer> list = getCustomerList();
     DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
    //clear jtable content
    model.setRowCount(0);
    Object[] row = new Object[1];
    for(int i=0; i<list.size();i++){
        row[0]=list.get(i).getID();
        
        
        model.addRow(row);
    }
    
    }       
      
      */
      
   //////////////////////////////////////////////

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_ID = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        image = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        quantity = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID:");

        jLabel2.setText("NAME:");

        jLabel3.setText("PRICE:");

        jLabel4.setText("IMAGE:");

        txt_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_IDActionPerformed(evt);
            }
        });

        image.setText("jLabel5");

        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("DELETE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("CHOSEIMAGE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "PRICE"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setText("QUANTITY:");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "name", "price", "quantity", "C_ID"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel7.setText("TOTAL PRICE:");

        jButton4.setText("TOTAL PRICE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel8.setText("C_ID");

        jButton5.setText("DONE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel10.setText("DISCOUNT :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton2)
                                    .addComponent(jButton3)
                                    .addComponent(jButton5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))
                            .addComponent(jLabel8))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_ID)
                            .addComponent(name)
                            .addComponent(price)
                            .addComponent(quantity, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(312, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton4))
                        .addGap(18, 18, 18)
                        .addComponent(jButton5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(194, 194, 194))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 85, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jTable2.setModel(new DefaultTableModel(null,new Object[]{"name", "price", "quantity", "C_ID"}));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        getSum();
        //dis();
        jLabel6.setText(Double.toString(getSum()));
        
       
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int index = jTable1.getSelectedRow();
        ShowItem(index);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JFileChooser file=new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images","jpg","png");
        file.addChoosableFileFilter(filter);
        int result=file.showSaveDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            File selectedFile=file.getSelectedFile();
            String path=selectedFile.getAbsolutePath();
            image.setIcon(ResizeImage(path,null));
            ImgPath=path;
        }
        else{
            System.out.println("No file selected");
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(!txt_ID.getText().equals("")){
            PreparedStatement ps= null;
            Connection con =getConnection();
            try {

                ps = con.prepareStatement("DELETE FROM product where id = ?");
                int id = Integer.parseInt(txt_ID.getText());
                ps.setInt(1, id);
                ps.executeUpdate();
                Show_Product_In_JTable();
                JOptionPane.showMessageDialog(null, "product deleted");
            }
            catch (SQLException ex) {
                Logger.getLogger(frame2.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "product deleted");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Product not deleted : no id to delete");
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //if(checkInputs() && ImgPath != null){
            try {
                Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/store","omer","omer");
                PreparedStatement ps=con.prepareStatement("insert into cart(name,price,Quantity,customerid) values(?,?,?,?)");
                ps.setString(1, name.getText());
                ps.setString(2, price.getText());
                //InputStream img = new FileInputStream(new File(ImgPath));
                // ps.setBlob(3, img );
                ps.setString(3, quantity.getText());
                ps.setString(4, jLabel9.getText());
                ps.executeUpdate();
                Show_Product_In_JTable2();
                JOptionPane.showMessageDialog(null, "data insert");
            }

            catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            // catch (FileNotFoundException ex) {
                //    Logger.getLogger(frame2.class.getName()).log(Level.SEVERE, null, ex);
                //}

            // }else{
            // JOptionPane.showMessageDialog(null, "one or more field are empty");

            //}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(shopping.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(shopping.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(shopping.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(shopping.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new shopping().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel image;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField name;
    private javax.swing.JTextField price;
    private javax.swing.JTextField quantity;
    private javax.swing.JTextField txt_ID;
    // End of variables declaration//GEN-END:variables
}
