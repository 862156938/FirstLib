package openstep1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OpenLottery  extends JFrame{
	JPanel  panal1=new JPanel();
	JPanel  panal2=new JPanel();
	//JScrollPane panal2 = new JScrollPane();
	JPanel  panal3=new JPanel();
	JPanel  panal4=new JPanel();
	JPanel  panal5=new JPanel();	
	
	public  OpenLottery()
	{
		this.setLayout(null);
		
		panal1.setBounds(20,20,600, 50);
		panal1.setBackground(Color.RED);
		JLabel jf=new JLabel("¿ª½±µ¹¼ÆÊ±");
		panal1.add(jf);
		
		panal2.setBounds(20,70,600, 200);
		panal2.setBackground(Color.GREEN);
		JLabel tz=new JLabel("Í¶×¢Çé¿ö");
		ScrollPane sPanel=new ScrollPane();
		sPanel.setSize(600, 200);
		JTextArea  ja=new JTextArea();
		ja.setSize(600, 580);
		sPanel.add(ja);
		panal2.add(tz);
		panal2.add(sPanel);
		
		panal3.setBounds(20,270,600, 50);
		panal3.setBackground(Color.BLACK);
		JLabel  kj=new JLabel("¿ª½±À²~~~(ÅäÍ¼Æ¬)");
		panal3.add(kj);
		
		panal4.setBounds(20,320,600, 100);
		panal4.setBackground(Color.BLUE);
		panal4.setLayout(new GridLayout(2,1));
		JLabel  label1=new JLabel("ºìÇò1        ºìÇò2          ºìÇò3           ºìÇò4         ºìÇò5           ºìÇò6                 À¶");
		JLabel  label2=new JLabel("12      15     17      20     25      26      18");
		panal4.add(label1);
		panal4.add(label2);
		
		panal5.setBounds(20,420,600, 200);
		panal5.setBackground(Color.GRAY);
		JLabel zj=new JLabel("ÖÐ½±Çé¿ö");
		ScrollPane sPanel2=new ScrollPane();
		sPanel2.setSize(600, 200);
		JTextArea  ja2=new JTextArea();
		ja.setSize(600, 580);
		sPanel2.add(ja2);
		panal5.add(zj);
		panal5.add(sPanel2);
		//panal1.setBounds(x, y, width, height);
		this.add(panal1);
		this.add(panal2);
		this.add(panal3);
		this.add(panal4);
		this.add(panal5);
		
		this.setVisible(true);
		
		//ScrollPane sPanel=new ScrollPane();
		//JTextArea  jt=new JTextArea();
		//sPanel.add(jt);
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		OpenLottery  olt=new  OpenLottery();
		olt.setTitle("Ë«É«Çò¿ª½±ÖÐÐÄ");

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		olt.setLocation((size.width - 600) / 2, (size.height - 700) / 2);
		olt.setSize(600, 700);
		olt.setVisible(true);
		
		

	}

}

