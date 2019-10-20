import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserLogin extends JApplet implements ActionListener {

	JLabel lbEPIC, Tag, pow, Image;
	JTextField txEPIC, txFName;
	JPanel P0, P1, P2, P3;
	JButton btnS, btnR;
	String VID, Name, FName, Status="No", str="No";
	
	public void init() {
		
		Image = new JLabel(new ImageIcon("ECI.jpg"));
		Image.getPreferredSize();
		Tag = new JLabel("ELECTION COMMISION OF INDIA");
		Tag.setFont(new Font("Arial", Font.BOLD, 20));
		Tag.setHorizontalAlignment(Tag.CENTER);
		
		P0 = new JPanel();
		P0.setBackground(Color.white);
		P0.add(Image);
		P0.add(Tag);
		
		setSize(450, 225);
		Font F = new Font("Arial", Font.PLAIN, 15);
		
		lbEPIC = new JLabel("EPIC NUMBER");
		lbEPIC.setFont(F);
		txEPIC = new JTextField(15);
		P1 = new JPanel();
		P1.setBackground(Color.white);
		P1.add(lbEPIC);
		P1.add(txEPIC);
		
		P2 = new JPanel();
		P2.setBackground(Color.white);
		btnS = new JButton("LogIn");
		btnS.setFont(F);
		btnR = new JButton("Result Day");
		P2.add(btnS);
		P2.add(btnR);
		
		pow = new JLabel("Powered by ECI      ");
		pow.setFont(new Font("Arial", Font.ITALIC, 10));
		pow.setHorizontalAlignment(pow.RIGHT);
		
		P3 = new JPanel();
		P3.setBackground(Color.white);
		P3.setLayout(new GridLayout(3, 1));
		P3.add(P1);
		P3.add(P2);
		P3.add(pow);
		
		setLayout(new BorderLayout());
		//this.getContentPane().setBackground(Color.white);
		add(P0, BorderLayout.NORTH);
		add(P3, BorderLayout.SOUTH);
		
		btnS.addActionListener(this);
		btnR.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		Object src = ae.getSource();
		if(src == btnS) {
		txFName = new JTextField(20);
		JOptionPane.showInputDialog(txFName, "Father's Name");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists ElectionDb");
			stmt.execute("use ElectionDb");
			PreparedStatement pstmt = con.prepareStatement("select Voter_ID, Voter_Name, Father_Name, Status from VoterTb where Voter_ID=?");
			pstmt.setString(1, txEPIC.getText());
			ResultSet rs = pstmt.executeQuery();
			PreparedStatement pstmt1 = con.prepareStatement("select Admin_Id from AdminTb where Admin_Id=?");
			pstmt1.setString(1, txEPIC.getText());
			
			ResultSet rs1 = pstmt1.executeQuery();
			while(rs1.next()) {
				String AdminID = rs1.getString(1);
				new AdminPage();
			}
			
			while(rs.next()) {
				VID = rs.getString(1);
				Name = rs.getString(2);
				FName = rs.getString(3);
				Status = rs.getString(4);
				
				if(Status.equalsIgnoreCase("No")) {
					new VoteCast(VID, Name, FName);
				}
				else {
					JOptionPane.showMessageDialog(null, "You had already voted.....");
				}
			}
						
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
		else {
			new ResultDay();
		}
	}
}
