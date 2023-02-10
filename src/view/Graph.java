package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.layout.Background;
import javafx.scene.media.VideoTrack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class Graph extends JPanel {
	Model m ;
	
	//=========images========= 
	BufferedImage Background = null;
	BufferedImage TableBack = null;

	//========TextArea========

		JTextArea CodeArea = new JTextArea("", 25, 60);
		TextLineNumber t = new TextLineNumber(CodeArea);
		
		JTextArea LoadCycles   = new JTextArea("", 25, 60);
		JTextArea StoreCycles = new JTextArea("", 25, 60);
		JTextArea AddCycles    = new JTextArea("", 25, 60);
		JTextArea MulCycles   = new JTextArea("", 25, 60);
		JTextArea SubCycles   = new JTextArea("", 25, 60);
		JTextArea DivCycles   = new JTextArea("", 25, 60);

	// -------- ArrayLists ------------- 
		ArrayList<JTextArea> Cycles = new ArrayList<JTextArea>();
		
	//========Panel===========
		

	//========Buttons=========
	JButton Run = new JButton();


	
	
	JPanel AddPanel ;
	JButton[][] AddButtons = new JButton[3][9];
	JButton[][] AddButtonLocations = new JButton[3][9];
	JButton b = new JButton();

	JPanel MulPanel ;
	JButton[][] MulButtons = new JButton[2][9];
	JButton[][] MulButtonLocations = new JButton[2][9];
	JButton Mulb = new JButton();

	JPanel LoadPanel ;
	JButton[][] LoadButtons = new JButton[3][3];
	JButton[][] LoadButtonLocations = new JButton[3][3];
	JButton Loadb = new JButton();

	JPanel StorePanel ;
	JButton[][] StoreButtons = new JButton[3][5];
	JButton[][] StoreButtonLocations = new JButton[3][5];
	JButton Storeb = new JButton();
	
	JPanel RegPanel ;
	JButton[][] RegButtons = new JButton[12][3];
	JButton Regb = new JButton();

	JPanel ExecPanel ;
	JButton[][] ExecButtons = new JButton[9][1];
	JButton Execb = new JButton();

	
	
	//=========Labels =============
	

	//=========== Img Icons=========
	ImageIcon RunI = new ImageIcon("images\\Run.png");
	ImageIcon nextI = new ImageIcon("images\\next.jpg");
	ImageIcon prevI = new ImageIcon("images\\prev.jpg");

	Graph(Model m){
		this.m = m;
		Cycles.add(LoadCycles);
		Cycles.add(StoreCycles);
		Cycles.add(AddCycles);
		Cycles.add(MulCycles);
		Cycles.add(SubCycles);
		Cycles.add(DivCycles);
	
	
		// ===================== ADD
		for (int i = 0; i <3 ; i++) {
			for (int j = 0; j <9 ; j++) {
				b = new JButton();
				AddButtons[i][j]=b ;
				}
		}
		
		AddPanel = new JPanel();
		AddPanel.setLayout(new GridLayout(3, 9));
		AddPanel.setBounds(92,155,994,170);

		// ===================== Mul

		for (int i = 0; i <2 ; i++) {
			for (int j = 0; j <9 ; j++) {
				Mulb = new JButton();
				MulButtons[i][j]=Mulb ;
				}
		}
		
		MulPanel = new JPanel();
		MulPanel.setLayout(new GridLayout(2, 9));
		MulPanel.setBounds(91,403,994,115);
		
		// ===================== Load

		for (int i = 0; i <3 ; i++) {
			for (int j = 0; j <3 ; j++) {
				Loadb = new JButton();
				LoadButtons[i][j]=Loadb ;
				}
		}
		
		LoadPanel = new JPanel();
		LoadPanel.setLayout(new GridLayout(3, 3));
		LoadPanel.setBounds(95,665,335,170);
		
		// ===================== Load

		for (int i = 0; i <3 ; i++) {
			for (int j = 0; j <5 ; j++) {
				Storeb = new JButton();
				StoreButtons[i][j]=Storeb ;
				}
		}
		
		StorePanel = new JPanel();
		StorePanel.setLayout(new GridLayout(3, 5));
		StorePanel.setBounds(528,665,550,170);
		
		// ===================== Reg

		for (int i = 0; i <12 ; i++) {
			for (int j = 0; j <3 ; j++) {
				Regb = new JButton();
				RegButtons[i][j]=Regb ;
				}
		}
		
		RegPanel = new JPanel();
		RegPanel.setLayout(new GridLayout(2, 12));
		RegPanel.setBounds(1135,110,220,760);
		
		
		for (int i = 0; i <9 ; i++) {
			for (int j = 0; j <1 ; j++) {
				Execb = new JButton();
				ExecButtons[i][j]=Execb ;
				}
		}
		ExecPanel = new JPanel();
		ExecPanel.setLayout(new GridLayout(1, 9));
		ExecPanel.setBounds(1375,111,140,760);
		
		
		try {
			Background = ImageIO.read(new File("images\\Space.jpg"));
			TableBack = ImageIO.read(new File("images\\Space2.jpg"));
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setLayout(null);
		
		if(m.CodePage){
			g.drawImage(Background, 0, 0, this.getWidth(), this.getHeight(),this);

			CodeArea.setForeground(Color.cyan);
			CodeArea.setBackground(new Color(50,50,50));
			CodeArea.setCaretColor(Color.WHITE);
			CodeArea.setBounds(150,180,900,600);
			try{				
			CodeArea.setFont(m.Hack_Regular.deriveFont(Font.PLAIN,20));

			
			for (JTextArea jTextArea : Cycles) {
				jTextArea.setFont(m.Hack_Regular.deriveFont(Font.PLAIN,20));
			}
			
		

			}catch(Exception e){
				
			}
			CodeArea.setMargin(new Insets(0,40,10,10));
			t.setBounds(0,0,30,605);
			t.setForeground(Color.white);
			t.setBackground(new Color(50,50,50));
			CodeArea.add(t);
			this.add(CodeArea);
			
			Run.setActionCommand("Run");

			Run.setBounds(1290, 50, 218, 80);
			Run.addActionListener(m.l);
			Run.addMouseListener(m.l);
			Run.setBorderPainted(false);
			Run.setCursor(new Cursor(Cursor.HAND_CURSOR));
			Run.setIcon(RunI);
			this.add(Run);
			

			
			int width = 180;
			for (JTextArea jTextArea : Cycles) {
				jTextArea.setForeground(Color.cyan);
				jTextArea.setBackground(new Color(50,50,50));
				jTextArea.setCaretColor(Color.WHITE);
				jTextArea.setBounds(1300,width,200,50);
				jTextArea.setMargin(new Insets(10,50,10,10));
				this.add(jTextArea);
				width+=110;
				}
			
		
			
		}else if(m.CyclePage){
			g.drawImage(TableBack, 0, 0, this.getWidth(), this.getHeight(),this);
			JLabel Cycle = new JLabel("Cycle : ");
			Cycle.setBounds(700, 0, 200, 100);
			Cycle.setText("Cycle : " + m.l.CurrentCycle);
			Cycle.setBackground(new Color(26, 2, 50));
			Cycle.setFont(new Font("Arial",Font.BOLD,25));
			Cycle.setForeground(Color.WHITE);
			this.add(Cycle);
//			next.setContentAreaFilled(false);
		
			
			m.next.setBounds(1530, 400, 53, 90);
			m.next.setBorderPainted(false);
			m.next.setCursor(new Cursor(Cursor.HAND_CURSOR));
			m.next.setIcon(nextI);
			
			this.add(m.next);
//			System.out.println("+=================== "+next.getActionListeners().length);
//			prev.setContentAreaFilled(false);
			m.prev.setBounds(10, 400, 53, 90);
			m.prev.setBorderPainted(false);
			m.prev.setCursor(new Cursor(Cursor.HAND_CURSOR));
			m.prev.setIcon(prevI);
			this.add(m.prev);
			
			//  ========================  ADD  =================== 
			AddPanel.setOpaque(false);
			int x = 0 ; int y = 0 ;

			for (int i = 0; i < AddButtons.length; i++) {
				for (int j = 0; j < AddButtons[0].length; j++) {
				
					JButton b = AddButtons[i][j];
					b.setBounds(0+x,0+y,100,50);
					b.setSize(new Dimension(100, 50));
					b.setMargin(new Insets(0,0,0,0));
					b.setContentAreaFilled(false);
				
					b.setBorder(null);
					b.setFocusPainted(false);
					b.setForeground(Color.cyan);
					b.setFont(new Font("Arial", Font.BOLD, 24));
					AddPanel.add(b);
					x+=111;
				}
				x=0;
				y+=58;
			}
			AddPanel.setVisible(true);
			this.add(AddPanel);
			// =================== Mul ==================
			MulPanel.setOpaque(false);
			 x = 0 ;  y = 0 ;

			for (int i = 0; i < MulButtons.length; i++) {
				for (int j = 0; j < MulButtons[0].length; j++) {
				
					JButton b = MulButtons[i][j];
					b.setBounds(0+x,0+y,100,50);
					b.setSize(new Dimension(100, 50));
					b.setMargin(new Insets(0,0,0,0));
					b.setContentAreaFilled(false);
		
					b.setBorder(null);
					b.setFocusPainted(false);
					b.setForeground(Color.cyan);
					b.setFont(new Font("Arial", Font.BOLD, 24));
					MulPanel.add(b);
					x+=111;
				}
				x=0;
				y+=58;
			}
			MulPanel.setVisible(true);
			this.add(MulPanel);
			// =================== Load ==================
			LoadPanel.setOpaque(false);
			 x = 0 ;  y = 0 ;

			for (int i = 0; i < LoadButtons.length; i++) {
				for (int j = 0; j < LoadButtons[0].length; j++) {
				
					JButton b = LoadButtons[i][j];
					b.setBounds(0+x,0+y,100,50);
					b.setSize(new Dimension(100, 50));
					b.setMargin(new Insets(0,0,0,0));
					b.setContentAreaFilled(false);
			
					b.setBorder(null);
					b.setFocusPainted(false);
					b.setForeground(Color.cyan);
					b.setFont(new Font("Arial", Font.BOLD, 24));
					LoadPanel.add(b);
					x+=111;
				}
				x=0;
				y+=58;
			}
			LoadPanel.setVisible(true);
			this.add(LoadPanel);
			
			// =================== Store ==================
			StorePanel.setOpaque(false);
			 int xx = 0 ; int yy = 0 ;

			for (int i = 0; i < StoreButtons.length; i++) {
				for (int j = 0; j < StoreButtons[0].length; j++) {
				
					JButton b = StoreButtons[i][j];
					b.setBounds(0+xx,0+yy,100,50);
					b.setSize(new Dimension(100, 50));
					b.setMargin(new Insets(0,0,0,0));
					b.setContentAreaFilled(false);
			
					b.setBorder(null);
					b.setFocusPainted(false);
					b.setForeground(Color.cyan);
					b.setFont(new Font("Arial", Font.BOLD, 24));
					StorePanel.add(b);
					xx+=111;	
				}
				xx=0;
				yy+=58;
			}
			StorePanel.setVisible(true);
			this.add(StorePanel);
			
			// =================== Store ==================
			RegPanel.setOpaque(false);
			  xx = 0 ;  yy = 0 ;

			for (int i = 0; i < RegButtons.length; i++) {
				for (int j = 0; j < RegButtons[0].length; j++) {
				
					JButton b = RegButtons[i][j];
					b.setBounds(0+xx,0+yy,67,57);
					b.setSize(new Dimension(67, 57));
					b.setMargin(new Insets(0,0,0,0));
					b.setContentAreaFilled(false);
			
					b.setBorder(null);
					b.setFocusPainted(false);
					b.setForeground(Color.cyan);
					b.setFont(new Font("Arial", Font.BOLD, 24));
					RegPanel.add(b);
					xx+=70;
				}
				xx=0;
				yy+=60	;
			}
			RegPanel.setVisible(true);
			this.add(RegPanel);
			
			ExecPanel.setOpaque(false);
			  xx = 0 ;  yy = 0 ;

			for (int i = 0; i < ExecButtons.length; i++) {
				for (int j = 0; j < ExecButtons[0].length; j++) {
				
					JButton b = ExecButtons[i][j];
					b.setBounds(0+xx,0+yy,150,72);
					b.setSize(new Dimension(150, 72));
					b.setMargin(new Insets(0,0,0,0));
					b.setContentAreaFilled(false);
			
					b.setBorder(null);
					b.setFocusPainted(false);
					b.setForeground(Color.cyan);
					b.setFont(new Font("Arial", Font.BOLD, 18));
					ExecPanel.add(b);
					xx+=140;
				}
				xx=0;
				yy+=79	;
			}
			ExecPanel.setVisible(true);
			this.add(ExecPanel);
		}
		
	}
	
}
