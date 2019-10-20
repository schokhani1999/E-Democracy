import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminPage extends JFrame implements ActionListener {

	JLabel Image, Tag, pow;
	JPanel P0, P1, p1, p2, p3, p4, p5, p6, p7, p8, p9;
	JTabbedPane jtp;
	JButton Logout;
	
	public AdminPage() {
		
		setVisible(true);
		setSize(850, 850);
		
		Logout = new JButton("LogOut");
		
		Image = new JLabel(new ImageIcon("ECI.jpg"));
		Image.getPreferredSize();
		Tag = new JLabel("ELECTION COMMISION OF INDIA");
		Tag.setFont(new Font("Arial", Font.BOLD, 20));
		Tag.setHorizontalAlignment(Tag.CENTER);
		
		P0 = new JPanel();
		P0.setBackground(Color.white);
		P0.add(Image);
		P0.add(Tag);
		
		p1 = new JPanel();
		p1.add(new Party_Table());
		p2 = new JPanel();
		p2.add(new Candidate_Table());
		p3 = new JPanel();
		p3.add(new State_Table());
		p4 = new JPanel();
		p4.add(new City_Table());
		p5 = new JPanel();
		p5.add(new Constituency_Table());
		p6 = new JPanel();
		p6.add(new Booth_Table());
		p7 = new JPanel();
		p7.add(new Area_Table());
		p8 = new JPanel();
		p8.add(new Re_votertable());
		
		pow = new JLabel("Powered by ECI      ");
		pow.setFont(new Font("Arial", Font.ITALIC, 10));
		pow.setHorizontalAlignment(pow.RIGHT);
		
		JPanel p9 = new JPanel();
		p9.add(pow);
		p9.add(Logout);

		jtp = new JTabbedPane();
		jtp.add("Add Party", p1);
		jtp.add("Add Candidate", p2);
		jtp.add("Add State", p3);
		jtp.add("Add City", p4);
		jtp.add("Add Constituency", p5);
		jtp.add("Add Area", p6);
		jtp.add("Add Booth", p7);
		jtp.add("Add Voter", p8);
		
		setLayout(new BorderLayout());
		add(P0, BorderLayout.NORTH);
		add(jtp, BorderLayout.CENTER);
		add(p9, BorderLayout.SOUTH);
		
		Logout.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		dispose();
	}
	
	public static void main(String[]args) {
		new AdminPage();
	}
}
