package view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import Engine.Main;

public class Model extends JFrame{
	Graph g ; 
	Listener l ; 
	boolean CodePage = true; 
	boolean CyclePage = false; 

	Font SpaceMono_Bold;
	Font SpaceMono_Regular;
	Font Hack_Regular;
	Main main ;
	JButton next = new JButton();
	JButton prev = new JButton();
	
	
	Model(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1600, 900);
		g = new Graph(this);
		l = new Listener(this);
		main = new Main();
		
		next.addActionListener(l);
		next.setActionCommand("Next");
		prev.setActionCommand("Prev");
		prev.addActionListener(l);

		this.setTitle("Micro Processors");
		this.setVisible(true);
		g.setBounds(0,0,1600,880);
		this.setResizable(false);
		this.setLayout(null);
		this.add(g);
		this.addMouseListener(l);
		this.repaint();


		
		try {
			  
			SpaceMono_Bold = Font.createFont(Font.TRUETYPE_FONT,
					   	new File("./Fonts/SpaceMono-Bold.ttf"));
			SpaceMono_Regular= Font.createFont(Font.TRUETYPE_FONT,
				   	new File("./Fonts/SpaceMono-Regular.ttf"));
			
			Hack_Regular = Font.createFont(Font.TRUETYPE_FONT,
				   	new File("./Fonts/Hack-Regular.ttf"));
			  
			} catch (FontFormatException | IOException e) {
			  e.printStackTrace();
			}
	}
	public static void main(String[] args) {
		Model m = new Model();

	}
}
