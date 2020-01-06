package step1;
/*
 * ����GUI
 * 
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;
/*
 *Ͷעվ�� 
 */
public class Betting extends JFrame {
	JPanel panel_info = new JPanel();
	JScrollPane panel_ballnum_s = new JScrollPane();
	JPanel panel_ballnum = new JPanel();
	JPanel panel_save = new JPanel();
	JPanel panel_chat = new JPanel();
	static final int DEFULTNUMS = 10; // Ĭ������ע��

	// ����ṹ�ļ�ֵ��
	HashMap<String, HashMap<String, JTextField>> allements = new HashMap<String, HashMap<String, JTextField>>();
	HashMap<String, HashMap<String, String>> allementsValues = new HashMap<String, HashMap<String, String>>();

	public Betting() {
		// ��ϢPanel
		// ��Ϣ���
		panel_info.setLayout(new FlowLayout());
		JLabel lblBetNum = new JLabel("ע����");
		JTextField txtBetNum = new JTextField(15);
		JLabel lblUserName = new JLabel("������");
		JTextField txtUserName = new JTextField(15);
		JLabel lblBetCount = new JLabel("Ͷע������20191220");
		JLabel lblBetWay = new JLabel("Ͷע��ʽ��");
		ButtonGroup radioBetWay = new ButtonGroup();
		JRadioButton radioBetWay_1 = new JRadioButton("��ѡ");
		JRadioButton radioBetWay_2 = new JRadioButton("�˹�");
		radioBetWay.add(radioBetWay_1);
		radioBetWay.add(radioBetWay_2);

		// ��ѡ
		radioBetWay_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int inums = Integer.parseInt(lblBetNum.getText());
					genNums(inums);
					setStatus(inums, false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "��������ȷ��ʽ��ע��", "��ʾ", JOptionPane.ERROR_MESSAGE);
					lblBetNum.requestFocus();
				}

			}
		});
		// �˹�
		radioBetWay_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int inums = Integer.parseInt(lblBetNum.getText());
					genNums(inums);
					setStatus(inums, true);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "��������ȷ��ʽ��ע��", "��ʾ", JOptionPane.ERROR_MESSAGE);
					lblBetNum.requestFocus();
				}

			}
		});

		panel_info.add(lblBetNum);
		panel_info.add(txtBetNum);
		panel_info.add(lblUserName);
		panel_info.add(txtUserName);
		panel_info.add(lblBetCount);
		panel_info.add(lblBetWay);
		panel_info.add(radioBetWay_1);
		panel_info.add(radioBetWay_2);
		panel_info.setBackground(Color.red);

		// ����Panel
		panel_save.setBounds(20, 400, 500, 50);
		JButton save = new JButton("����");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtUserName.getText();
				if ((name == null) || (name.trim().length() == 0)) {
					JOptionPane.showMessageDialog(null, "��������Ϊ��", "��ʾ", JOptionPane.ERROR_MESSAGE);
					txtUserName.requestFocus();
				}

			}
		});
		panel_save.add(save);

		panel_chat.setLayout(new BorderLayout());
		panel_chat.setBounds(20, 450, 500, 300);
		ScrollPane panel_chat_s = new ScrollPane();
		JTextArea panel_chat_show = new JTextArea();
		panel_chat_s.add(panel_chat_show);
		panel_chat_show.setSize(450, 300);
		JPanel panel_chat_1 = new JPanel();
		panel_chat_1.setLayout(new FlowLayout());
		JLabel panel_chat_news = new JLabel("��Ϣ");
		JTextField panel_chat_input = new JTextField(14);
		JButton panel_chat_talk = new JButton("����");

		panel_chat_1.add(panel_chat_news);
		panel_chat_1.add(panel_chat_input);
		panel_chat_1.add(panel_chat_talk);
		panel_chat.add(panel_chat_s, BorderLayout.NORTH);
		panel_chat.add(panel_chat_1, BorderLayout.CENTER);

		genNums(DEFULTNUMS);

		setLayout(null);
		panel_info.setBounds(20, 5, 500, 50);
		panel_info.setBackground(Color.red);
		panel_ballnum_s.setBounds(20, 70, 500, 300);
		add(panel_info);
		add(panel_ballnum_s);
		add(panel_save);
		add(panel_chat);

		setTitle("˫ɫ��Ͷעϵͳ");

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((size.width - 600) / 2, (size.height - 700) / 2);
		setSize(600, 700);
		setVisible(true);
	}

	public void genNums(int nums) {
		allements.clear();
		panel_ballnum.removeAll();
		panel_ballnum.setLayout(new GridLayout(nums + 1, 8));
		JLabel title0 = new JLabel("���");
		JLabel title1 = new JLabel("��1");
		JLabel title2 = new JLabel("��2");
		JLabel title3 = new JLabel("��3");
		JLabel title4 = new JLabel("��4");
		JLabel title5 = new JLabel("��5");
		JLabel title6 = new JLabel("��6");
		JLabel title7 = new JLabel("��1");
		panel_ballnum.add(title0);
		panel_ballnum.add(title1);
		panel_ballnum.add(title2);
		panel_ballnum.add(title3);
		panel_ballnum.add(title4);
		panel_ballnum.add(title5);
		panel_ballnum.add(title6);
		panel_ballnum.add(title7);

		allements = new HashMap<String, HashMap<String, JTextField>>();
		for (int i = 0; i < nums; i++) {
			HashMap<String, JTextField> hmmap = new HashMap<String, JTextField>();
			panel_ballnum.add(new JLabel(i + "."));
			for (int j = 0; j < 7; j++) {
				JTextField t = new JTextField();
				t.setSize(5, 200);
				hmmap.put("c" + j, t);
				panel_ballnum.add(t);
			}
			allements.put("l" + i, hmmap);
		}
		panel_ballnum.repaint();
		panel_ballnum_s.setViewportView(panel_ballnum);
	}

	public void setStatus(int nums, boolean status) {
		for (int i = 0; i < nums; i++) {
			// һ�е�����
			HashMap<String, JTextField> hm = allements.get("l" + i);
			for (int j = 0; j < 7; j++) {
				JTextField jf = hm.get("c" + j);
				jf.setEditable(status);
			}
		}
	}

	public static void main(String[] args){
		Betting mybet = new Betting();
	}

}
