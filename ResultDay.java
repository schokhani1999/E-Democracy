import java.awt.GridLayout;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ResultDay extends JFrame {

	JLabel Name, Votes, Vote_Percent, Res;
	JTextField txName, txVotes, txVote_Percent, txRes;
	JTable table;
	
	public ResultDay() {
		
		setVisible(true);
		setSize(600, 600);
	
		table = new JTable();
		Object []heads = {"Candidate Name", "Votes", "Percent"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(heads);
		table.setModel(model);
		Object [] row = new Object[4];
		
		int tot_vote = 0, vote;
		float VP;
		String cn;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists ElectionDb");
			stmt.execute("use ElectionDb");
			stmt.executeUpdate("create table if not exists ResultsDayTb(Candidate_Name varchar(50), Votes int, Percent float)");
				
			PreparedStatement pstmt1 = con.prepareStatement("select sum(Votes) from ResultsTb");
			ResultSet rs1 = pstmt1.executeQuery();
			while(rs1.next())
				tot_vote = rs1.getInt(1);
			
			PreparedStatement pstmt = con.prepareStatement("select Candidate_Name, Votes from ResultsTb");
			ResultSet rs = pstmt.executeQuery();
			
			PreparedStatement pst = con.prepareStatement("update ResultsDayTb set Votes=?, Percent=? where Candidate_Name=?");
			while(rs.next()) {
				cn = rs.getString("Candidate_Name");
				vote = rs.getInt("Votes");
				VP = (float)vote / tot_vote * 100;
				row[0] = cn;
				row[1] = vote;
				row[2] = VP;
				pst.setString(3, cn);
				pst.setInt(1, (int)vote);			
				pst.setFloat(2, VP);
				model.addRow(row);
				pst.execute();
			}
			
			con.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JScrollPane jsp = new JScrollPane(table);

		add(jsp);
	}
}
