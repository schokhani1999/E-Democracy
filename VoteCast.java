import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VoteCast extends JFrame implements ActionListener {
	
	JLabel lbName, lbEPIC, lbCast, Image, Tag, pow;
	JTextField txName, txEPIC;
	JComboBox<String> cbCast;
	JButton btnCast;
	JPanel P0, P1, P2, P3, P4, P5;
	int Vote, votes, V;
	
	public VoteCast(String VID, String Name, String FName) {
		
		setVisible(true);
		setSize(600, 600);
		
		Image = new JLabel(new ImageIcon("ECI.jpg"));
		Image.getPreferredSize();
		Tag = new JLabel("ELECTION COMMISION OF INDIA");
		Tag.setFont(new Font("Arial", Font.BOLD, 20));
		Tag.setHorizontalAlignment(Tag.CENTER);
		
		P0 = new JPanel();
		P0.setBackground(Color.white);
		P0.add(Image);
		P0.add(Tag);
		
		lbName = new JLabel("Voter's Name");
		txName = new JTextField(Name);
		txName.setEditable(false);
		P1 = new JPanel();
		P1.setBackground(Color.white);
		P1.add(lbName);
		P1.add(txName);
		
		lbEPIC = new JLabel("EPIC Number");
		txEPIC = new JTextField(VID);
		txEPIC.setEditable(false);
		P2 = new JPanel();
		P2.setBackground(Color.white);
		P2.add(lbEPIC);
		P2.add(txEPIC);
		
		lbCast = new JLabel("Cast Vote For");
		cbCast = new JComboBox<String>();
		cbCast.addItem("----Select Candidate----");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists ElectionDb");
			stmt.execute("use ElectionDb");
			ResultSet rs = stmt.executeQuery("select distinct Candidate_name from CandidateTb");
			while(rs.next()) {
				cbCast.addItem(rs.getString("Candidate_name"));
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		P3 = new JPanel();
		P3.setBackground(Color.white);
		P3.add(lbCast);
		P3.add(cbCast);
		
		btnCast = new JButton("Cast My Vote !!!");
		
		pow = new JLabel("Powered by ECI      ");
		pow.setFont(new Font("Arial", Font.ITALIC, 10));
		pow.setHorizontalAlignment(pow.RIGHT);
		
		P4 = new JPanel();
		P4.setBackground(Color.white);
		P4.add(btnCast);
		
		P5 = new JPanel();
		P5.setBackground(Color.white);
		P5.setLayout(new GridLayout(11, 1));
		P5.add(P1);
		P5.add(P2);
		P5.add(new JLabel());
		P5.add(P3);
		P5.add(new JLabel());
		P5.add(P4);
		P5.add(new JLabel());
		P5.add(new JLabel());
		P5.add(new JLabel());
		P5.add(new JLabel());
		P5.add(pow);
		
		this.getContentPane().setBackground(Color.white);		
		setLayout(new BorderLayout());
		add(P0, BorderLayout.NORTH);
		add(P5, BorderLayout.SOUTH);
		
		btnCast.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		JOptionPane.showMessageDialog(null, "Your Vote has been Recorded");
		JOptionPane.showMessageDialog(null, "Thank you for Vote");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists ElectionDb");
			stmt.execute("use ElectionDb");
			PreparedStatement pstmt1 = con.prepareStatement("update VoterTb set Status = ? where Voter_ID = ?");
			pstmt1.setString(2, txEPIC.getText());
			pstmt1.setString(1, "Yes");
			pstmt1.execute();			
			stmt.executeUpdate("create table if not exists ResultsTb(Candidate_Name varchar(50), Votes int)");
			PreparedStatement pstmt2 = con.prepareStatement("select Votes from ResultsTb where Candidate_Name = ?");
			pstmt2.setString(1, cbCast.getSelectedItem().toString());
			ResultSet rs2 = pstmt2.executeQuery();
			while(rs2.next()) {
				votes = rs2.getInt("votes");
				Vote = votes;
			}
			PreparedStatement pstmt = con.prepareStatement("update ResultsTb set votes=? where Candidate_Name=?");
			pstmt.setString(2, cbCast.getSelectedItem().toString());
			votes++;
			pstmt.setInt(1, votes);
			pstmt.execute();
			
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		dispose();
	}
}
