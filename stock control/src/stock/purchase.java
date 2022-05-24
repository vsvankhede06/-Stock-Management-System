//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package stock;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

public class purchase extends JFrame {
    Connection con;
    PreparedStatement pst;
    PreparedStatement pst1;
    PreparedStatement pst2;
    DefaultTableModel df;
    ResultSet rs;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JTextField txtbal;
    private JTextField txtpay;
    private JTextField txtpcode;
    private JTextField txtpname;
    private JTextField txtprice;
    private JTextField txtqty;
    private JTextField txttcost;
    private JComboBox<String> txtvendor;

    public purchase() {
        this.initComponents();
        this.Connect();
        this.Vendor();
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection("jdbc:mysql://localhost/stockmanagement", "root", "");
        } catch (ClassNotFoundException var2) {
            Logger.getLogger(vendor.class.getName()).log(Level.SEVERE, (String)null, var2);
        } catch (SQLException var3) {
            Logger.getLogger(vendor.class.getName()).log(Level.SEVERE, (String)null, var3);
        }

    }

    public void Vendor() {
        try {
            this.pst = this.con.prepareStatement("select Distinct name from vendor");
            this.rs = this.pst.executeQuery();
            this.txtvendor.removeAllItems();

            while(this.rs.next()) {
                this.txtvendor.addItem(this.rs.getString("name"));
            }
        } catch (SQLException var2) {
            Logger.getLogger(purchase.class.getName()).log(Level.SEVERE, (String)null, var2);
        }

    }

    public void barcode() {
        try {
            String pcode = this.txtpcode.getText();
            this.pst = this.con.prepareStatement("select * from product where barcode = ?");
            this.pst.setString(1, pcode);
            this.rs = this.pst.executeQuery();
            if (!this.rs.next()) {
                JOptionPane.showMessageDialog(this, "BarCode Not Found");
                this.txtpcode.setText("");
            } else {
                String pname = this.rs.getString("pname");
                String price = this.rs.getString("rprice");
                this.txtpname.setText(pname.trim());
                this.txtprice.setText(price.trim());
                this.txtqty.requestFocus();
            }
        } catch (SQLException var4) {
            Logger.getLogger(purchase.class.getName()).log(Level.SEVERE, (String)null, var4);
        }

    }

    public void purchase() {
        int price = Integer.parseInt(this.txtprice.getText());
        int qty = Integer.parseInt(this.txtqty.getText());
        int tot = price * qty;
        this.df = (DefaultTableModel)this.jTable1.getModel();
        this.df.addRow(new Object[]{this.txtpcode.getText(), this.txtpname.getText(), this.txtprice.getText(), this.txtqty.getText(), tot});
        int sum = 0;

        for(int i = 0; i < this.jTable1.getRowCount(); ++i) {
            sum += Integer.parseInt(this.jTable1.getValueAt(i, 4).toString());
        }

        this.txttcost.setText(String.valueOf(sum));
        this.txtpcode.setText("");
        this.txtpname.setText("");
        this.txtprice.setText("");
        this.txtqty.setText("");
    }

    public void add() {
        try {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            String date = dt.format(now);
            String vendor = this.txtvendor.getSelectedItem().toString();
            String subtotal = this.txttcost.getText();
            String pay = this.txtpay.getText();
            String bal = this.txtbal.getText();
            int lastid = 0;
            String query1 = "insert into purchase(date,vendor,subtotal,pay,bal)values(?,?,?,?,?)";
            this.pst = this.con.prepareStatement(query1, 1);
            this.pst.setString(1, date);
            this.pst.setString(2, vendor);
            this.pst.setString(3, subtotal);
            this.pst.setString(4, pay);
            this.pst.setString(5, bal);
            this.pst.executeUpdate();
            this.rs = this.pst.getGeneratedKeys();
            if (this.rs.next()) {
                lastid = this.rs.getInt(1);
            }

            String query2 = "insert into purchaseitem(purid,pid,rprice,qty,total)values(?,?,?,?,?) ";
            this.pst1 = this.con.prepareStatement(query2);
            int total = false;

            String productid;
            String qty;
            for(int i = 0; i < this.jTable1.getRowCount(); ++i) {
                productid = (String)this.jTable1.getValueAt(i, 0);
                String price = (String)this.jTable1.getValueAt(i, 2);
                qty = (String)this.jTable1.getValueAt(i, 3);
                int total = (Integer)this.jTable1.getValueAt(i, 4);
                this.pst1.setInt(1, lastid);
                this.pst1.setString(2, productid);
                this.pst1.setString(3, price);
                this.pst1.setString(4, qty);
                this.pst1.setInt(5, total);
                this.pst1.executeUpdate();
            }

            String query3 = "update product set qty = qty+ ?   where barcode = ?";
            this.pst2 = this.con.prepareStatement(query3);

            for(int i = 0; i < this.jTable1.getRowCount(); ++i) {
                productid = (String)this.jTable1.getValueAt(i, 0);
                qty = (String)this.jTable1.getValueAt(i, 3);
                this.pst2.setString(1, qty);
                this.pst2.setString(2, productid);
                this.pst2.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Purchase Completedddddd!!!!!!!!!!!!!!!!!!!!");
        } catch (SQLException var17) {
            Logger.getLogger(purchase.class.getName()).log(Level.SEVERE, (String)null, var17);
        }

    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jPanel1 = new JPanel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel5 = new JLabel();
        this.txtpcode = new JTextField();
        this.txtpname = new JTextField();
        this.txtprice = new JTextField();
        this.jButton1 = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.jLabel7 = new JLabel();
        this.txtqty = new JTextField();
        this.jLabel8 = new JLabel();
        this.txttcost = new JTextField();
        this.jLabel9 = new JLabel();
        this.txtbal = new JTextField();
        this.jLabel10 = new JLabel();
        this.txtpay = new JTextField();
        this.jButton2 = new JButton();
        this.jButton3 = new JButton();
        this.jLabel4 = new JLabel();
        this.txtvendor = new JComboBox();
        this.setDefaultCloseOperation(3);
        this.jLabel1.setFont(new Font("Tahoma", 1, 24));
        this.jLabel1.setText("Purchase");
        this.jPanel1.setBorder(new SoftBevelBorder(0));
        this.jLabel2.setFont(new Font("Tahoma", 1, 14));
        this.jLabel2.setText("Product Code");
        this.jLabel3.setFont(new Font("Tahoma", 1, 14));
        this.jLabel3.setText("Productname");
        this.jLabel5.setFont(new Font("Tahoma", 1, 14));
        this.jLabel5.setText("Price");
        this.txtpcode.setFont(new Font("Tahoma", 1, 14));
        this.txtpcode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                purchase.this.txtpcodeActionPerformed(evt);
            }
        });
        this.txtpcode.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                purchase.this.txtpcodeKeyPressed(evt);
            }
        });
        this.txtpname.setFont(new Font("Tahoma", 1, 14));
        this.txtprice.setFont(new Font("Tahoma", 1, 14));
        this.jButton1.setText("Add");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                purchase.this.jButton1ActionPerformed(evt);
            }
        });
        this.jTable1.setModel(new DefaultTableModel(new Object[0][], new String[]{"Product Code", "Product Name", "Price", "Qty", "Total"}));
        this.jScrollPane1.setViewportView(this.jTable1);
        this.jLabel7.setFont(new Font("Tahoma", 1, 14));
        this.jLabel7.setText("Qty");
        this.txtqty.setFont(new Font("Tahoma", 1, 14));
        this.jLabel8.setFont(new Font("Tahoma", 1, 14));
        this.jLabel8.setText("Total Cost");
        this.txttcost.setFont(new Font("Tahoma", 1, 14));
        this.jLabel9.setFont(new Font("Tahoma", 1, 14));
        this.jLabel9.setText("Payment");
        this.txtbal.setFont(new Font("Tahoma", 1, 14));
        this.jLabel10.setFont(new Font("Tahoma", 1, 14));
        this.jLabel10.setText("Balance");
        this.txtpay.setFont(new Font("Tahoma", 1, 14));
        this.jButton2.setText("Close");
        this.jButton3.setText("Add");
        this.jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                purchase.this.jButton3ActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(29, 29, 29).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.txtpcode, -2, 147, -2)).addGap(38, 38, 38).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3).addGap(112, 112, 112).addComponent(this.jLabel5).addGap(85, 85, 85).addComponent(this.jLabel7).addGap(0, 0, 32767)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.txtpname, -2, 147, -2).addGap(28, 28, 28).addComponent(this.txtprice, -2, 107, -2).addPreferredGap(ComponentPlacement.RELATED, 15, 32767).addComponent(this.txtqty, -2, 95, -2).addGap(34, 34, 34).addComponent(this.jButton1, -2, 101, -2).addGap(118, 118, 118)))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jScrollPane1, -2, 634, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(this.jLabel8).addGap(68, 68, 68)).addComponent(this.txttcost, -2, 149, -2).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING).addComponent(this.jLabel9).addComponent(this.jLabel10)).addGap(70, 70, 70))).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING).addComponent(this.txtbal, -2, 149, -2).addComponent(this.txtpay, -2, 149, -2)).addGap(9, 9, 9))))).addGap(29, 29, 29)).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.jButton2, -2, 129, -2).addGap(305, 305, 305)).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(302, 302, 302).addComponent(this.jButton3, -2, 129, -2).addContainerGap(457, 32767))));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(32, 32, 32).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel5).addComponent(this.jLabel7)).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(25, 25, 25).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.txtpcode, -2, 30, -2).addComponent(this.txtpname, -2, 30, -2).addComponent(this.txtprice, -2, 30, -2).addComponent(this.txtqty, -2, 30, -2)).addGap(36, 36, 36).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel8).addGap(18, 18, 18).addComponent(this.txttcost, -2, 31, -2).addGap(18, 18, 18).addComponent(this.jLabel9).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.txtpay, -2, 31, -2).addGap(18, 18, 18).addComponent(this.jLabel10).addGap(18, 18, 18).addComponent(this.txtbal, -2, 31, -2)).addComponent(this.jScrollPane1, -2, 232, -2)).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jButton2, -1, 41, 32767).addGap(23, 23, 23)).addGroup(jPanel1Layout.createSequentialGroup().addGap(15, 15, 15).addComponent(this.jButton1, -2, 36, -2).addContainerGap(-1, 32767)))).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(380, 380, 380).addComponent(this.jButton3, -1, 41, 32767).addGap(21, 21, 21))));
        this.jLabel4.setFont(new Font("Tahoma", 1, 14));
        this.jLabel4.setText("Vendor");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addComponent(this.jPanel1, -2, -1, -2).addContainerGap(-1, 32767)).addGroup(layout.createSequentialGroup().addGap(37, 37, 37).addComponent(this.jLabel1).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.jLabel4).addGap(32, 32, 32).addComponent(this.txtvendor, -2, 141, -2).addGap(128, 128, 128)));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(22, 22, 22).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jLabel4).addComponent(this.txtvendor, -2, 29, -2)).addGap(30, 30, 30).addComponent(this.jPanel1, -2, -1, -2).addContainerGap(-1, 32767)));
        this.pack();
        this.setLocationRelativeTo((Component)null);
    }

    private void txtpcodeKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.barcode();
        }

    }

    private void txtpcodeActionPerformed(ActionEvent evt) {
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.purchase();
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        int pay = Integer.parseInt(this.txtpay.getText());
        int subtotal = Integer.parseInt(this.txttcost.getText());
        int bal = subtotal - pay;
        this.txtbal.setText(String.valueOf(bal));
        this.add();
    }

    public static void main(String[] args) {
        try {
            LookAndFeelInfo[] var1 = UIManager.getInstalledLookAndFeels();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                LookAndFeelInfo info = var1[var3];
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException var5) {
            Logger.getLogger(purchase.class.getName()).log(Level.SEVERE, (String)null, var5);
        } catch (InstantiationException var6) {
            Logger.getLogger(purchase.class.getName()).log(Level.SEVERE, (String)null, var6);
        } catch (IllegalAccessException var7) {
            Logger.getLogger(purchase.class.getName()).log(Level.SEVERE, (String)null, var7);
        } catch (UnsupportedLookAndFeelException var8) {
            Logger.getLogger(purchase.class.getName()).log(Level.SEVERE, (String)null, var8);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new purchase()).setVisible(true);
            }
        });
    }
}

