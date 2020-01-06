package finished;

/**
  *Ͷעģ��
  *Ͷע����GUI
  *�����������ŵ�У��
  *��Ŵ������ݿ� 
 *UDP������
 * @author 8621
 */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.*;
import java.util.*;
import javax.swing.*;


public class Betting extends JFrame {
	JPanel panel_info = new JPanel();
	JScrollPane panel_ballnum_s = new JScrollPane();
	JPanel panel_ballnum = new JPanel();
	JPanel panel_save = new JPanel();
	JPanel panel_chat = new JPanel();
	static final int DEFULTNUMS = 10; // Ĭ������ע��

	// ����ṹ�ļ�ֵ��
	HashMap<String, HashMap<String, JTextField>> allements = new HashMap<String, HashMap<String, JTextField>>();
	JTextField tfullname = new JTextField(15);

	// ��������Ϣ
	DatagramSocket ds; // �˿��׽���
	JTextArea panel_chat_show = new JTextArea(); // ������
	
	public Betting() {
		try {
			ds = new DatagramSocket();
			// ���������ݳ�ʼ��
			try {
				new ReceiveThread().start();
				byte[] bs = "����һ������ң� ��Ҵ���к� ��".getBytes();
				DatagramPacket dp = new DatagramPacket(bs, bs.length, InetAddress.getByName("localhost"), 10086);
				ds.send(dp);
			} catch (Exception e) {
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
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
					int flag = 0;
					genNums(inums, flag);
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
					int flag = 1;
					genNums(inums, flag);
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
						if (isRepeatNum(nums)) {
							if (insertToData(nums)) {
								JOptionPane.showMessageDialog(null, "���ݱ���ɹ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "���ݱ���ʧ��", "��ʾ", JOptionPane.ERROR_MESSAGE);
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
		panel_chat_s.add(panel_chat_show);
		panel_chat_show.setSize(450, 300);
		JPanel panel_chat_1 = new JPanel();
		panel_chat_1.setLayout(new FlowLayout());
		JLabel panel_chat_news = new JLabel("��Ϣ");
		JTextField panel_chat_input = new JTextField(14);
		JButton panel_chat_talk = new JButton("����");
		panel_chat_talk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				byte[] bs = panel_chat_input.getText().getBytes();
				DatagramPacket dp;
				try {
					dp = new DatagramPacket(bs, bs.length, InetAddress.getByName("localhost"), 10086);
					ds.send(dp);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		panel_chat_1.add(panel_chat_news);
		panel_chat_1.add(panel_chat_input);
		panel_chat_1.add(panel_chat_talk);
		panel_chat.add(panel_chat_s, BorderLayout.NORTH);
		panel_chat.add(panel_chat_1, BorderLayout.CENTER);

		genNums(DEFULTNUMS, 1);

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

	/**
	 * ��������Ͷע�ľ���
	 * ���Ի�ѡ�Զ�������ţ�Ҳ�����ֶ��������
	 * @param nums����
	 * @param flagͶעģʽ
	 */
	public void genNums(int nums, int flag) {
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

		if (flag == 0) {
			allements = new HashMap<String, HashMap<String, JTextField>>();
			for (int i = 0; i < nums; i++) {
				HashMap<String, JTextField> hmmap = new HashMap<String, JTextField>();
				panel_ballnum.add(new JLabel(i + "."));
				String[] redBallNum = setRedBall();
				for (int j = 0; j < 7; j++) {
					if (j < 6) {
						JTextField t = new JTextField(redBallNum[j]);
						t.setSize(5, 100);
						hmmap.put("c" + j, t);
						panel_ballnum.add(t);
					}
					if (j == 6) {
						int blueBallNum = (int) (Math.random() * 15 + 1);
						String s = Integer.toString(blueBallNum);
						JTextField t = new JTextField(s);
						t.setSize(5, 100);
						hmmap.put("c" + j, t);
						panel_ballnum.add(t);
					}
				}
				allements.put("l" + i, hmmap);
			}
		} else if (flag == 1) {
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
		}
		panel_ballnum.repaint();
		panel_ballnum_s.setViewportView(panel_ballnum);
	}

	/**
	 * �����ı����״̬���ڻ�ѡģʽ�£��ı��򽫲���ѡ
	 * @param nums����
	 * @param status״̬
	 */
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

	/**
	 * ���ɲ��ظ����������1-31����
	 * @return String[]�������
	 */
	public String[] setRedBall() {
		Set<Integer> set = new HashSet<Integer>();
		Random ran = new Random();
		String[] s = new String[6];
		int k = 0;
		while (true) {
			int i = ran.nextInt(30) + 1;
			set.add(i);
			if (set.size() >= 6) {
				break;
			}
		}
		for(int j:set) {
			String ss = Integer.toString(j);
			s[k] = ss;
			k++;
		}
		return s;
	}
	
	/**
	 * �ж����������Ƿ�Ϊ����
	 * @param nums����
	 * @return boolean
	 */
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

	/**
	 * �ж�����Ƿ��ڷ�Χ��
	 * @param nums����
	 * @return boolean
	 */
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

	/**
	 * �������Ƿ��ظ�
	 * @param nums����
	 * @return boolean
	 */
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

	/**
	 * ����Ŵ������ݿ�
	 * @param nums
	 * @return boolean
	 */
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
				pstmt.setString(3, hm.get("c1").getText());
				pstmt.setString(4, hm.get("c2").getText());
				pstmt.setString(5, hm.get("c3").getText());
				pstmt.setString(6, hm.get("c4").getText());
				pstmt.setString(7, hm.get("c5").getText());
				pstmt.setString(8, hm.get("c6").getText());
				pstmt.setString(9, getSeqno());
				pstmt.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * ��ȡͶע����
	 * @author 86215
	 * @return StringͶע����
	 */
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

	/**
	 * �����ҽ����߳�
	 * @author 86215
	 */
	class ReceiveThread extends Thread {
		public void run() {
			while (true) {
				try {
					byte[] bytes = new byte[1024];
					DatagramPacket dp = new DatagramPacket(bytes, 1024);
					ds.receive(dp);
					String s = new String(bytes, 0, dp.getLength());
					System.out.println("�ͻ����յ� " + s);
					panel_chat_show.append(s + "\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
