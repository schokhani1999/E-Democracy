import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Re_votertable extends JPanel implements ActionListener, ItemListener {

	JLabel lbVID, lbPhoto, lbPh, lbName, lbFName, lbGen, lbDob, lbAdd,lbCity, lbState, lbDate, lbAID,lbCon;
	JTextField txVID, txName, txFName, txAdd, txAID;
	JComboBox<String> cbD, cbM, cbY, cbState, cbDD, cbMM, cbYY,cbCity,cbConstituency;
	JRadioButton rbM, rbF;
	ButtonGroup grpGen;
	JButton btnS, btnC, btnR, btnB;
	JPanel P0,P1,P2,P3,P4;
	
	public Re_votertable() {
		lbVID = new JLabel("Voter ID");
		lbPhoto = new JLabel("Photo");
		lbPh = new JLabel();
		lbPh.getPreferredSize();
		lbPh.setBorder(BorderFactory.createLineBorder(Color.GRAY, 20));
		lbName = new JLabel("Voter Name");
		lbFName = new JLabel("Father Name");
		lbGen = new JLabel("Gender");
		lbDob = new JLabel("Date of Birth");
		lbAdd = new JLabel("Address");
		lbState = new JLabel("State");
		lbDate = new JLabel("Date");
		lbAID = new JLabel("Area ID");
		lbCity=new JLabel("City");
		lbCon=new JLabel("Constituency");

		P0 = new JPanel();
		P1 = new JPanel();
		P2 = new JPanel();
		P3 = new JPanel();
		P4 = new JPanel();
	//	P0.setBackground(Color.white);
		/*P0.add(lbGen);
		P0.add(rbM);
		P0.add(rbF);*/
		
		txVID = new JTextField(20);
		txName = new JTextField(20);
		txFName = new JTextField(20);
		txAdd = new JTextField(20);
		txAID = new JTextField(20);
			
		cbD = new JComboBox<String>();
		cbD.addItem("---- Day ----");
		
		cbM = new JComboBox<String>();
		cbM.addItem("---- Month ----");
		for(int i = 1; i < 13; i++)
			cbM.addItem(i + "");
		
		cbY = new JComboBox<String>();
		cbY.addItem("---- Year ----");
		for(int i = 1950; i <= 2001; i++)
			cbY.addItem(i + "");
		cbConstituency=new JComboBox<String>();
		cbConstituency.addItem("---- Constituency ----");
		cbCity = new JComboBox<String>();
		cbCity.addItem("---- City ----");
		
		cbState = new JComboBox<String>();
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
		
		cbDD = new JComboBox<String>();
		cbDD.addItem("---- Day ----");
		
		cbMM = new JComboBox<String>();
		cbMM.addItem("---- Month ----");
		for(int i = 1; i < 13; i++)
			cbMM.addItem(i + "");
		
		cbYY = new JComboBox<String>();
		cbYY.addItem("---- Year ----");
		for(int i = 1950; i <= 2001; i++)
			cbYY.addItem(i + "");
		
		rbM = new JRadioButton("Male");
		rbF = new JRadioButton("Female");
		
		grpGen = new ButtonGroup();
		grpGen.add(rbM);
		grpGen.add(rbF);
		
		btnS = new JButton("Submit");
		btnC = new JButton("Cancel");
		btnR = new JButton("Refresh");
		btnB = new JButton("Browse");
		cbY.setSize(400, 400);
		
		P1.setLayout(new GridLayout(4,2));
		P0.add(lbGen);
		P0.add(rbM);
		P0.add(rbF);
		P0.setLayout(new GridLayout(1,4));
		P4.setLayout(new GridLayout(1,4));
		P2.setLayout(new GridLayout(5, 2));
		P1.add(lbVID);
		P1.add(txVID);
		P1.add(lbPhoto);
		P1.add(lbPh);
		P1.add(lbName);
		P1.add(txName);
		P1.add(lbFName);
		P1.add(txFName);
		add(P1);
		/*add(lbGen);
		add(rbM);
		add(rbF);*/
		add(P0);
		P4.add(lbDob);
		P4.add(cbY);
		P4.add(cbM);
		P4.add(cbD);
		
		
		add(P4);
		P2.add(lbAdd);
		P2.add(txAdd);
		P2.add(lbState);
		P2.add(cbState);
		P2.add(lbCity);
		P2.add(cbCity);
		P2.add(lbCon);
		P2.add(cbConstituency);
		//add(lbDate);
		//add(cbDD);
		//add(cbMM);
	//	add(cbYY);
		P2.	add(lbAID);
		P2.add(txAID);
		add(P2);
		P3.add(btnS);
		P3.add(btnR);
		P3.add(btnC);
		add(P3);
		setLayout(new GridLayout(5, 1));
		
		btnS.addActionListener(this);
		btnC.addActionListener(this);
		btnR.addActionListener(this);
		btnB.addActionListener(this);
		
		cbM.addItemListener(this);
		cbY.addItemListener(this);
		cbMM.addItemListener(this);
		cbYY.addItemListener(this);
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
				stmt.executeUpdate("create table if not exists VoterTb(Voter_ID varchar(50), Voter_Photo longblob, Voter_Name varchar(50), Father_Name varchar(80), Gender varchar(20), DoB date, Address varchar(250), State varchar(50), City varchar(50), Voter_ID_Date date, Area_ID varchar(50), Status varchar(10))");
				PreparedStatement pstmt = con.prepareStatement("insert into VoterTb(Voter_ID, Voter_Photo, Voter_Name, Father_Name, Gender, DoB, Address, State, City, Voter_ID_Date, Area_ID, Status) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, txVID.getText());
				pstmt.setString(2, "");
				pstmt.setString(3, txName.getText());
				pstmt.setString(4, txFName.getText());
				String g = "Male";
				if(rbF.isSelected())
					g = "Female";
				pstmt.setString(5, g);
				int dd = Integer.parseInt(cbD.getSelectedItem().toString());
				int mm = Integer.parseInt(cbM.getSelectedItem().toString());;
				int yyyy = Integer.parseInt(cbY.getSelectedItem().toString());;
				Date dob = new Date(yyyy - 1900, mm - 1, dd);
				pstmt.setDate(6, dob);
				pstmt.setString(7, txAdd.getText());
				pstmt.setString(8, cbState.getSelectedItem().toString());
				String City=(String)cbCity.getSelectedItem();
				pstmt.setString(9, City);
				dd = Integer.parseInt(cbD.getSelectedItem().toString());
				mm = Integer.parseInt(cbM.getSelectedItem().toString());;
				yyyy = Integer.parseInt(cbY.getSelectedItem().toString());;
				Date voter_date = new Date(yyyy - 1900, mm - 1, dd);
				pstmt.setDate(10, voter_date);
				pstmt.setString(11, txAID.getText());
				pstmt.setString(12, "No");
				
				pstmt.execute();
				
				con.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(src == btnC) {
			System.exit(-1);
		}
		else if(src == btnR) {
			txVID.setText("");
			txName.setText("");
			txFName.setText("");
			txAdd.setText("");
			txAID.setText("");
			
			lbPh.setIcon(new ImageIcon());
			
			cbD.setSelectedIndex(0);
			cbM.setSelectedIndex(0);
			cbY.setSelectedIndex(0);
			cbState.setSelectedIndex(0);
			cbDD.setSelectedIndex(0);
			cbMM.setSelectedIndex(0);
			cbYY.setSelectedIndex(0);
			
			grpGen.clearSelection();
		}
		else if(src == btnB) {
			
		}
	}	
	@Override
	public void itemStateChanged(ItemEvent ie) {
		// TODO Auto-generated method stub
		if(ie.getStateChange() == ItemEvent.DESELECTED) return;
				
		Object src = ie.getSource();
		
		if(cbY.getSelectedIndex() != 0 && cbM.getSelectedIndex() != 0) {
			int m = Integer.parseInt(cbM.getSelectedItem().toString()); 
			int y = Integer.parseInt(cbY.getSelectedItem().toString());
			int d = 0;
			
			if(m == 2) {
				if(y % 4 == 0) {
					d = 29;
				}
				else {
					d = 28;
				}
			}
			else if(m == 4 || m == 6 || m == 9 || m == 11) {
				d = 30;
			}
			else {
				d = 31;
			}
			
			for(int i = 1; i <= d; i++) {
				cbD.addItem(i + "");
			}
		}
		
		if(cbYY.getSelectedIndex() != 0 && cbMM.getSelectedIndex() != 0) {
			int m = Integer.parseInt(cbM.getSelectedItem().toString()); 
			int y = Integer.parseInt(cbY.getSelectedItem().toString());
			int d = 0;
			
			if(m == 2) {
				if(y % 4 == 0) {
					d = 29;
				}
				else {
					d = 28;
				}
			}
			else if(m == 4 || m == 6 || m == 9 || m == 11) {
				d = 30;
			}
			else {
				d = 31;
			}
			
			for(int i = 1; i <= d; i++) {
				cbDD.addItem(i + "");
			}
		}
			if(ie.getStateChange()== ItemEvent.DESELECTED)return;
					
					
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
					else
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

