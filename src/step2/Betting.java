package step2;
/*
 * ��������֤�ʹ������ݿ�
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
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
	JTextField tfullname = new JTextField(15);

	public Betting() {
		// ��ϢPanel
		// ��Ϣ���
		panel_info.setLayout(new FlowLayout());
		JLabel lblBetNum = new JLabel("ע����");
		JTextField txtBetNum = new JTextField(15);
		JLabel lblUserName = new JLabel("������");
		JLabel lblBetSeqs = new JLabel("Ͷע������");
		JLabel seqs = new JLabel(getSeqno());
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
					int inums = Integer.parseInt(txtBetNum.getText());
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
					int inums = Integer.parseInt(txtBetNum.getText());
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
		panel_info.add(tfullname);
		panel_info.add(lblBetSeqs);
		panel_info.add(seqs);
		
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
				// У������
				String name = tfullname.getText();
				if ((name == null) || (name.trim().length() == 0)) {
					JOptionPane.showMessageDialog(null, "��������Ϊ��", "��ʾ", JOptionPane.ERROR_MESSAGE);
					tfullname.requestFocus();
					return;
				}

				// У�����
				int nums = Integer.parseInt(txtBetNum.getText());
				if (isInteger(nums)) {
					if (isEnableNum(nums)) {
						if(isRepeatNum(nums)) {
							if(insertToData(nums)) {
								JOptionPane.showMessageDialog(null, "���ݱ���ɹ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
							}else {
								JOptionPane.showMessageDialog(null, "���ݱ���ɹ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
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

	// ���󴴽�����
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
				// һ�е�����
				JTextField jf = hm.get("c" + j);
				jf.setEditable(status);
			}
		}
	}

	// У������Ƿ�Ϊ����
	public boolean isInteger(int nums) {
		boolean flag = true;
		for (int i = 0; i < nums; i++) {
			// һ�е�����
			HashMap<String, JTextField> hm = allements.get("l" + i);
			for (int j = 0; j < 7; j++) {
				// һ�е�����
				JTextField jf = hm.get("c" + j);
				try {
					int temp = Integer.parseInt(jf.getText());
					jf.setBackground(Color.white);
				} catch (Exception e1) {
					flag = false;
					jf.setBackground(Color.RED);
				}
			}
		}
		if (flag == false) {
			JOptionPane.showMessageDialog(null, "���������ֺ���", "��ʾ", JOptionPane.ERROR_MESSAGE);
		}
		return flag;
	}

	// У����ŵķ�Χ
	public boolean isEnableNum(int nums) {
		boolean flag = true;
		for (int i = 0; i < nums; i++) {
			// һ�е�����
			HashMap<String, JTextField> hm = allements.get("l" + i);
			for (int j = 0; j < 7; j++) {
				// һ�е�����
				JTextField jf = hm.get("c" + j);
				jf.setBackground(Color.white);
				int temp = Integer.parseInt(jf.getText());
				if (j < 6) {
					if (temp < 1 || temp > 31) {
						jf.setBackground(Color.RED);
						flag = false;
					}
				} else {
					if (temp < 1 || temp > 16) {
						jf.setBackground(Color.RED);
						flag = false;
					}
				}
				
			}
		}
		if (flag == false) {
			JOptionPane.showMessageDialog(null, "��������ȷ�����ֺ���", "��ʾ", JOptionPane.ERROR_MESSAGE);
		}
		return flag;
	}

	// У���ظ������
	public boolean isRepeatNum(int nums) {
		boolean flag = true;
		for (int i = 0; i < nums; i++) {
			// һ�е�����
			HashMap<String, JTextField> hm = allements.get("l" + i);
			HashMap<String, JTextField> hmCompare = allements.get("l" + i);
			for (int j = 0; j < 6; j++) {
				// һ�е�����
				JTextField jf = hm.get("c" + j);
				jf.setBackground(Color.white);
				try {
					int temp = Integer.parseInt(jf.getText());
					for (int k = 0; k < 6; k++) {
						JTextField jfCompare = hmCompare.get("c" + k);
						if (j != k) {
							int knum = Integer.parseInt(jfCompare.getText());
							if (temp == knum) {
								flag = false;
								jf.setBackground(Color.RED);
							}
						}
					}
				} catch (Exception e) {
					flag = false;
					jf.setBackground(Color.RED);
				}
			}
		}
		if (flag == false) {
			JOptionPane.showMessageDialog(null, "��Ʊ���ݴ����ظ�������������", "��ʾ", JOptionPane.ERROR_MESSAGE);
		}
		return flag;
	}

	// �ѺϷ����ݱ��浽���ݿ�
	public boolean insertToData(int nums) {
		boolean flag = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test";
			String username = "root";
			String password = "123456";
			Connection conn = DriverManager.getConnection(url, username, password);

			for (int i = 0; i < nums; i++) {
				// һ�е�����
				HashMap<String, JTextField> hm = allements.get("l" + i);
				String sql = "insert into t_lot(stuname,red1,red2,red3,red4,red5,red6,blue,seqno)"
						+ "values(?,?,?,?,?,?,?,?,?)";

				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, tfullname.getText());
				pstmt.setString(2, hm.get("c0").getText());
				pstmt.setString(3, hm.get("c0").getText());
				pstmt.setString(4, hm.get("c0").getText());
				pstmt.setString(5, hm.get("c0").getText());
				pstmt.setString(6, hm.get("c0").getText());
				pstmt.setString(7, hm.get("c0").getText());
				pstmt.setString(8, hm.get("c0").getText());
				pstmt.setString(9, getSeqno());
				pstmt.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	// ��ȡͶע����
	public String getSeqno() {
		GregorianCalendar defaultdate = new GregorianCalendar();
		int year = defaultdate.get(Calendar.YEAR);
		int month = defaultdate.get(Calendar.MONTH);
		int day = defaultdate.get(Calendar.DAY_OF_MONTH);

		String seqno = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
		return seqno;
	}

	public static void main(String[] args) {
		Betting mybet = new Betting();
	}

}
