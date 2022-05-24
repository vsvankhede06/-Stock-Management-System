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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
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

public class product extends JFrame {
    Connection con;
    PreparedStatement pst;
    DefaultTableModel df;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JTextField txtbarcode;
    private JTextField txtcprice;
    private JTextField txtdes;
    private JTextField txtlevel;
    private JTextField txtpname;
    private JTextField txtqty;
    private JTextField txtrprice;

    public product() {
        this.initComponents();
        this.Connect();
        this.load();
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

    public void load() {
        try {
            this.pst = this.con.prepareStatement("select * from product");
            ResultSet rs = this.pst.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            int a = rd.getColumnCount();
            this.df = (DefaultTableModel)this.jTable1.getModel();
            this.df.setRowCount(0);

            while(rs.next()) {
                Vector v2 = new Vector();

                for(int i = 1; i <= a; ++i) {
                    v2.add(rs.getString("id"));
                    v2.add(rs.getString("pname"));
                    v2.add(rs.getString("description"));
                    v2.add(rs.getString("barcode"));
                    v2.add(rs.getString("cprice"));
                    v2.add(rs.getString("rprice"));
                    v2.add(rs.getString("qty"));
                    v2.add(rs.getString("rlevel"));
                }

                this.df.addRow(v2);
            }
        } catch (SQLException var6) {
            Logger.getLogger(vendor.class.getName()).log(Level.SEVERE, (String)null, var6);
        }

    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jPanel1 = new JPanel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.txtpname = new JTextField();
        this.txtdes = new JTextField();
        this.txtbarcode = new JTextField();
        this.txtcprice = new JTextField();
        this.txtrprice = new JTextField();
        this.txtqty = new JTextField();
        this.txtlevel = new JTextField();
        this.jButton1 = new JButton();
        this.jButton2 = new JButton();
        this.jButton3 = new JButton();
        this.jButton4 = new JButton();
        this.jButton5 = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.setDefaultCloseOperation(3);
        this.jLabel1.setFont(new Font("Tahoma", 1, 24));
        this.jLabel1.setText("Product");
        this.jPanel1.setBorder(new SoftBevelBorder(0));
        this.jLabel2.setFont(new Font("Tahoma", 1, 12));
        this.jLabel2.setText("Product Name");
        this.jLabel3.setFont(new Font("Tahoma", 1, 12));
        this.jLabel3.setText("Description");
        this.jLabel4.setFont(new Font("Tahoma", 1, 12));
        this.jLabel4.setText("Barcode");
        this.jLabel5.setFont(new Font("Tahoma", 1, 12));
        this.jLabel5.setText("Cost Price");
        this.jLabel6.setFont(new Font("Tahoma", 1, 12));
        this.jLabel6.setText("Retail Price");
        this.jLabel7.setFont(new Font("Tahoma", 1, 12));
        this.jLabel7.setText("Qty");
        this.jLabel8.setFont(new Font("Tahoma", 1, 12));
        this.jLabel8.setText("ReOrderLevel");
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(27, 27, 27).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel4).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.jLabel7).addComponent(this.jLabel8)).addGap(38, 38, 38).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.txtlevel, -2, 159, -2).addComponent(this.txtqty, -2, 159, -2).addComponent(this.txtrprice, -2, 159, -2).addComponent(this.txtcprice, -2, 159, -2).addComponent(this.txtbarcode, -2, 159, -2).addComponent(this.txtdes, -2, 159, -2).addComponent(this.txtpname, -2, 159, -2)).addContainerGap(51, 32767)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(35, 35, 35).addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.txtpname, -2, -1, -2)).addGap(21, 21, 21).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.txtdes, -2, -1, -2)).addGap(24, 24, 24).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.txtbarcode, -2, -1, -2)).addGap(31, 31, 31).addComponent(this.jLabel5)).addComponent(this.txtcprice, -2, -1, -2)).addGap(29, 29, 29).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.txtrprice, -2, -1, -2)).addGap(24, 24, 24).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.txtqty, -2, -1, -2)).addGap(24, 24, 24).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.txtlevel, -2, -1, -2)).addContainerGap(-1, 32767)));
        this.jButton1.setText("Add");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                product.this.jButton1ActionPerformed(evt);
            }
        });
        this.jButton2.setText("Edit");
        this.jButton3.setText("Clear");
        this.jButton4.setText("Delete");
        this.jButton5.setText("Close");
        this.jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                product.this.jButton5ActionPerformed(evt);
            }
        });
        this.jTable1.setModel(new DefaultTableModel(new Object[0][], new String[]{"ProductID", "ProductName", "Description", "Barcode", "CostPrice", "RetailPrice", "Qty", "RLevel"}));
        this.jScrollPane1.setViewportView(this.jTable1);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(361, 361, 361).addComponent(this.jLabel1)).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel1, -2, -1, -2).addGap(33, 33, 33).addComponent(this.jScrollPane1, -1, 513, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.jButton1, -2, 93, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.jButton2, -2, 93, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.jButton4, -2, 93, -2).addGap(24, 24, 24).addComponent(this.jButton3, -2, 93, -2).addGap(18, 18, 18).addComponent(this.jButton5, -2, 93, -2))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(26, 26, 26).addComponent(this.jLabel1).addGap(18, 18, 18).addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 27, 32767).addComponent(this.jPanel1, -2, -1, -2)).addComponent(this.jScrollPane1, -2, 0, 32767)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jButton1, -2, 41, -2).addComponent(this.jButton2, -2, 41, -2).addComponent(this.jButton3, -2, 41, -2).addComponent(this.jButton4, -2, 41, -2).addComponent(this.jButton5, -2, 41, -2)).addGap(35, 35, 35)));
        this.pack();
        this.setLocationRelativeTo((Component)null);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        try {
            String pname = this.txtpname.getText();
            String des = this.txtdes.getText();
            String barcode = this.txtbarcode.getText();
            String costprice = this.txtcprice.getText();
            String retailprice = this.txtrprice.getText();
            String qty = this.txtqty.getText();
            String rlevel = this.txtlevel.getText();
            this.pst = this.con.prepareStatement("insert into product(pname,description,barcode,cprice,rprice,qty,rlevel)values(?,?,?,?,?,?,?)");
            this.pst.setString(1, pname);
            this.pst.setString(2, des);
            this.pst.setString(3, barcode);
            this.pst.setString(4, costprice);
            this.pst.setString(5, retailprice);
            this.pst.setString(6, qty);
            this.pst.setString(7, rlevel);
            this.pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product Addeddddd");
            this.txtpname.setText("");
            this.txtdes.setText("");
            this.txtbarcode.setText("");
            this.txtcprice.setText("");
            this.txtrprice.setText("");
            this.txtqty.setText("");
            this.txtlevel.setText("");
            this.txtpname.requestFocus();
        } catch (SQLException var9) {
            Logger.getLogger(vendor.class.getName()).log(Level.SEVERE, (String)null, var9);
        }

    }

    private void jButton5ActionPerformed(ActionEvent evt) {
        this.setVisible(false);
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
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, (String)null, var5);
        } catch (InstantiationException var6) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, (String)null, var6);
        } catch (IllegalAccessException var7) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, (String)null, var7);
        } catch (UnsupportedLookAndFeelException var8) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, (String)null, var8);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new product()).setVisible(true);
            }
        });
    }
}
