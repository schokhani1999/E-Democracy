import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoadPage extends JFrame {

	JPanel P0;
	JLabel Image, Tag;
	JProgressBar pb;
	int i;
	
	public LoadPage() {
		
		Image = new JLabel(new ImageIcon("ECI.jpg"));
		Image.getPreferredSize();
		Tag = new JLabel("ELECTION COMMISION OF INDIA");
		Tag.setFont(new Font("Arial", Font.BOLD, 20));
		Tag.setHorizontalAlignment(Tag.CENTER);
		
		P0 = new JPanel();
		P0.setBackground(Color.white);
		P0.add(Image);
		P0.add(Tag);
		
		pb = new JProgressBar(0, 100);
		pb.setValue(0);
		pb.setStringPainted(true);    
		
		setLayout(new BorderLayout());
		add(P0, BorderLayout.NORTH);
		add(pb, BorderLayout.SOUTH);
	}
	
	public void Load() {
		while(i<=2000) {    
			pb.setValue(i);    
			i=i+2;    
			try {
				Thread.sleep(150);
			} catch(Exception e) {  
			}
		}
	}
	
	public static void main(String[]args) {
		LoadPage lp = new LoadPage();    
	    lp.setVisible(true); 
	    lp.setSize(600, 600);
	    lp.Load();    
	}
}