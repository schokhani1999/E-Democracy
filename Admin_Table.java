import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Admin_Table extends JApplet implements ActionListener {

	JLabel AdminId, Mobile;
	JTextField txId, txMobile;
	JButton btnSub, btnCan;
	JPanel P1, P2, P3, P4;
	
	public void init() {
		AdminId = new JLabel("Admin Id");
		Mobile = new JLabel("Mobile");
		
		txId = new JTextField(20);
		txMobile = new JTextField(20);
		
		btnSub = new JButton("Submit");
		btnCan = new JButton("Cancel");
		
		P1 = new JPanel();
		P1.add(AdminId);
		P1.add(txId);
		
		P2 = new JPanel();
		P2.add(Mobile);
		P2.add(txMobile);
		
		P3 = new JPanel();
		P3.add(btnSub);
		P3.add(btnCan);
		
		P4 = new JPanel();
		P4.setLayout(new GridLayout(3, 1));
		P4.add(P1);
		P4.add(P2);
		P4.add(P3);
		
		add(P4);
		
		btnSub.addActionListener(this);
		btnCan.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		Object src = ae.getSource();
		
		if(src == btnSub) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists ElectionDb");
				stmt.execute("use ElectionDb");
				stmt.executeUpdate("create table if not exists AdminTb(Admin_Id varchar(50), Mobile varchar(20))");
				PreparedStatement pstmt = con.prepareStatement("insert into AdminTb(Admin_Id, Mobile) values(?, ?)");
				pstmt.setString(1, txId.getText());
				pstmt.setString(2, txMobile.getText());
				
				pstmt.executeUpdate();
				
				con.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(src == btnCan) {
			System.exit(-1);
		}
	}
}
