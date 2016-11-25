
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Spielmenu extends JPanel{

	private JButton gegenSpieler, gegenKi;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Spielmenu(){
		super();
		this.setBackground(Color.black);
		this.setBounds(100, 200, 400, 400);
		this.setVisible(true);
		
		
		gegenSpieler = new JButton("Mensch vs. Mensch");
		gegenKi		 = new JButton("Mensch vs. KI");
		
		this.add(gegenSpieler);
		this.add(gegenKi);
		
		gegenSpieler.setBounds(250, 300, 100, 30);
		gegenKi.setBounds(250, 350, 100, 30);
	}
	
}
