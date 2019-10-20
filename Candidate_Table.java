import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Candidate_Table extends JPanel implements ActionListener, ItemListener {

	JLabel lbCID,lbEType,lbName;
	JTextField txCID,txname;
	JComboBox<String> cbETypes,cbState,cbCity,cbConstituency;
	JButton btnS, btnC, btnR;
	JPanel P1, P2, P3, P4, P5, P6;
	
	public Candidate_Table() {
		
		lbCID = new JLabel("Candidate ID");
		lbEType = new JLabel("Election Type");
		lbName=new JLabel("Candidate Name");
		
		txCID = new JTextField(10);
		txname=new JTextField(20);
		
		cbETypes = new JComboBox<String>();
		cbETypes.addItem("---- Election Type ----");
		cbState=new JComboBox<String>();
		cbState.addItem("---- State ----");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists ElectionDb");
			stmt.execute("use ElectionDb");
			ResultSet rs = stmt.executeQuery("select distinct State_Name from StateTb");
			while(rs.next()) {
				cbState.addItem(rs.getString("State_Name"));
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		cbCity=new JComboBox<String>();
		cbCity.addItem("---- City ----");
		cbConstituency=new JComboBox<String>();
		cbConstituency.addItem("---- Constituency ----");
		cbETypes.addItem("Lok Sabha Elections");
		cbETypes.addItem("Vidhan Sabha Elections");
		
		btnS = new JButton("Submit");
		btnC = new JButton("Cancel");
		btnR = new JButton("Refresh");
		
		P1 = new JPanel();
		P2 = new JPanel();
		P3 = new JPanel();
		P4 = new JPanel();
		P5 = new JPanel();
		P6 = new JPanel();
		
		P1.add(lbCID);
		P1.add(txCID);
		P2.add(lbName);
		P2.add(txname);
		P3.add(cbState);
		P3.add(cbCity);
		P3.add(cbConstituency);
		P4.add(lbEType);
		P4.add(cbETypes);
		P5.add(btnS);
		P5.add(btnC);
		P5.add(btnR);
		
		P6.setLayout(new GridLayout(5, 1));
		P6.add(P1);
		P6.add(P2);
		P6.add(P3);
		P6.add(P4);
		P6.add(P5);
		
		setLayout(new GridLayout(1, 1));
		add(P6);
		
		btnS.addActionListener(this);
		btnC.addActionListener(this);
		btnR.addActionListener(this);
		this.cbState.addItemListener(this);
		this.cbCity.addItemListener(this);
		this.cbConstituency.addItemListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		Object src = ae.getSource();
		
		if(src == btnS) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists ElectionDb");
				stmt.execute("use ElectionDb");
				stmt.executeUpdate("create table if not exists CandidateTb(Candidate_name varchar(50),Candidate_ID varchar(50), Area_ID varchar(50), Election_Type varchar(100))");
				stmt.executeUpdate("create table if not exists ResultsTb(Candidate_Name varchar(50), Votes int)");
				PreparedStatement pstmt = con.prepareStatement("insert into CandidateTb(Candidate_ID, Area_ID, Election_Type,Candidate_name) values(?,?, ?, ?)");
				PreparedStatement pstmt1 = con.prepareStatement("insert into ResultsTb(Candidate_Name, Votes) values(?, ?)");
				int votes = 0;
				pstmt1.setString(1, txname.getText());
				pstmt1.setInt(2, votes);
				pstmt1.execute();
				
				pstmt.setString(1, txCID.getText());
				pstmt.setString(2, cbConstituency.getSelectedItem().toString());
				pstmt.setString(3, cbETypes.getSelectedItem().toString());
				pstmt.setString(4, txname.getText());
				pstmt.execute();
				
				stmt.executeUpdate("create table if not exists ResultsDayTb(Candidate_Name varchar(50), Votes int, Percent float)");
				PreparedStatement pst = con.prepareStatement("insert ResultsDayTb(Candidate_Name, Votes, Percent) values(?, ?, ?)");
				pst.setString(1, txname.getText());
				pst.setInt(2, 0);
				pst.setFloat(3, 0);
				pst.execute();
				
				con.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else if(src == btnR) {
			txCID.setText("");
			cbETypes.setSelectedIndex(0);
		}
		else if(src == btnC) {
			System.exit(-1);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		if(ie.getStateChange()== ItemEvent.DESELECTED)return;
		Object src = ie.getSource();
		
		if(src==cbState)
		{
			if(cbCity.getItemCount()>1)
			{
				cbCity.removeAllItems();
				cbCity.addItem("---- City ----");
			}
			if(cbState.getSelectedIndex()==0) return;
			String State=(String)cbState.getSelectedItem();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists ElectionDb");
				stmt.execute("use ElectionDb");
				ResultSet rs = stmt.executeQuery("select distinct City_Name from "+ State +"tb");
				while(rs.next()) {
					cbCity.addItem(rs.getString("City_Name"));
				}
				con.close();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		else if(src == cbCity)
		{
			if(cbConstituency.getItemCount()>1)
			{
				cbConstituency.removeAllItems();
				cbConstituency.addItem("---- Constituency ----");
			}
					
			if(cbCity.getSelectedIndex()==0 ) return;
			
			String City=(String)cbCity.getSelectedItem();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists ElectionDb");
				stmt.execute("use ElectionDb");
				ResultSet rs = stmt.executeQuery("select distinct Constituency_Name from "+ City +"tb");
				while(rs.next()) {
					cbConstituency.addItem(rs.getString("Constituency_Name"));
				}
				con.close();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
