package finished;

/**
 * ����ģ��
 * �ֶ����뿪��ʱ��
 * ÿ5������ݿ��ȡ��ע��Ϣ
 * ��ʱ������ʱ������ʱ��
 * ʱ�䵽��ÿ5������һ�����
 * @author 86215
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OpenLottery extends JFrame {
	JPanel panal1 = new JPanel();
	JPanel panal2 = new JPanel();
	JPanel panal3 = new JPanel();
	JPanel panal4 = new JPanel();
	JPanel panal5 = new JPanel();
	JLabel jtime = new JLabel("        ����ʱ�䣺");
	JLabel jtimerp = new JLabel("       ���뿪��ʱ�䣺");
	JLabel kj = new JLabel("���Ͼ�Ҫ������~~~~~");
	JTextArea zjArea = new JTextArea(); // �н���������ı���
	JTextArea ja = new JTextArea(); // Ͷע�����ı���
	JLabel label1 = new JLabel("   ����1         ����2         ����3          ����4        ����5         ����6            ��");
	JLabel label2 = new JLabel("");
	TreeSet<Integer> ts = new TreeSet<Integer>(); // �洢���򿪽����룬Ҳ�����ж��ظ�
	int blueno; // �洢�����������

	int sectals = 0; // ����ˢ�����ݵ�ʱ����

	/**
	 * ���췽����GUI��������
	 */
	public OpenLottery() {
		this.setLayout(null);
		panal1.setBounds(20, 20, 600, 50);
		panal1.setBackground(Color.RED);
		panal1.setLayout(new GridLayout(2, 1));
		panal1.add(jtime);
		panal1.add(jtimerp);
		// ���õ���ʱ
		dispTime();

		panal2.setBounds(20, 70, 600, 200);
		panal2.setBackground(Color.GREEN);
		JLabel tz = new JLabel("Ͷע���");
		ScrollPane sPanel = new ScrollPane();
		sPanel.setSize(600, 200);
		ja.setSize(600, 580);
		sPanel.add(ja);
		panal2.add(tz);
		panal2.add(sPanel);
		panal3.setBounds(20, 270, 600, 50);
		panal3.setBackground(Color.RED);
		panal3.add(kj);

		panal4.setBounds(20, 320, 600, 100);
		panal4.setBackground(Color.BLUE);
		panal4.setLayout(new GridLayout(2, 1));
		panal4.add(label1);
		panal4.add(label2);

		panal5.setBounds(20, 420, 600, 200);
		panal5.setBackground(Color.GRAY);
		JLabel zj = new JLabel("�н����");
		ScrollPane sPanel2 = new ScrollPane();
		sPanel2.setSize(600, 200);

		ja.setSize(600, 580);
		// sPanel2.add(ja2);
		sPanel2.add(zjArea);
		panal5.add(zj);
		panal5.add(sPanel2);
		// panal1.setBounds(x, y, width, height);
		this.add(panal1);
		this.add(panal2);
		this.add(panal3);
		this.add(panal4);
		this.add(panal5);
		this.setVisible(true);
	}

	/**
	 * ���ɲ�Ʊ
	 */
	public void genLottery() {
		Random rd = new Random();
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// ���ɺ���
				int redtmp = rd.nextInt(31) + 1;
				boolean flag = true;
				while (true) {
					flag = ts.add(redtmp);
					if (flag == false)
						continue;
					else
						break;
				}
				if (ts.size() == 7) {
					// ��������
					blueno = rd.nextInt(16) + 1;
					String tlabel2 = label2.getText();
					label2.setText(tlabel2 + "          " + blueno);
					// �����н�
					analyLottery();
					// ��ʾ�н�����
					String str = dispAnalyData();
					zjArea.setText(str);
					timer.cancel();
				} else {
					String tlabel2 = label2.getText();
					label2.setText(tlabel2 + "           " + redtmp);
				}
			}
		};

		// ���忪ʼ�ȴ�ʱ�� --- �ȴ� 5 ��
		long delay = 500;
		// ����ÿ��ִ�еļ��ʱ��
		int intevalPeriod = 2 * 1000;
		// ����������һ��ʱ��������
		timer.schedule(task, delay, intevalPeriod);
	}

	/**
	 * �����н�����
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean analyLottery() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test";
			String username = "root";
			String password = "123456";
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();

			String sql = "select *  from  t_lot ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int redtotals = 0; // ���к������
			int bluetotlas = 0; // �����������
			String level = new String();
			int recid;
			
			while (rs.next()) {
				recid = rs.getInt("recid");
				String stuname = rs.getString("stuname");
				int red1 = Integer.parseInt(rs.getString("red1"));
				int red2 = Integer.parseInt(rs.getString("red2"));
				int red3 = Integer.parseInt(rs.getString("red3"));
				int red4 = Integer.parseInt(rs.getString("red4"));
				int red5 = Integer.parseInt(rs.getString("red5"));
				int red6 = Integer.parseInt(rs.getString("red6"));
				int blue = Integer.parseInt(rs.getString("blue"));
				if (ts.contains(red1))
					redtotals++;
				if (ts.contains(red2))
					redtotals++;
				if (ts.contains(red3))
					redtotals++;
				if (ts.contains(red4))
					redtotals++;
				if (ts.contains(red5))
					redtotals++;
				if (ts.contains(red6))
					redtotals++;
				if (blue == blueno)
					bluetotlas++;

				switch (redtotals) {
				case 6: {
					if (bluetotlas == 1)
						level = "level1";
					else
						level = "level2";
				}
					break;
				case 5:
					if (bluetotlas == 1)
						level = "level3";
					else
						level = "level4";
					break;
				case 4:
					if (bluetotlas == 1)
						level = "level4";
					else
						level = "level5";
					break;
				case 3:
					if (bluetotlas == 1)
						level = "level4";
					break;
				case 2:
					if (bluetotlas == 1)
						level = "level6";
					break;
				case 1:
					if (bluetotlas == 1)
						level = "level6";
					break;
				case 0:
					if (bluetotlas == 1)
						level = "level6";
					break;
				}
				// �������ݿ��
				String uptsql = "UPDATE	T_LOT	SET	LEVEL=?,FLAG=?	WHERE	RECID=? ";
				PreparedStatement uptpstmt = conn.prepareStatement(uptsql);
				uptpstmt.setString(1, level);
				uptpstmt.setString(2, "true");
				uptpstmt.setInt(3, recid);
				uptpstmt.execute();
				
				level = "";
				redtotals = 0;
				bluetotlas = 0;
			}
			pstmt.execute();
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * ����ʱ���뵹��ʱ
	 */
	public void dispTime() {
		GregorianCalendar tmp = new GregorianCalendar();
		// ����ʱ��
		GregorianCalendar gc = new GregorianCalendar(tmp.get(Calendar.YEAR), tmp.get(Calendar.MONTH),
				tmp.get(Calendar.DAY_OF_MONTH), 9, 8, 0);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		jtime.setText("        ����ʱ�䣺" + sf.format(gc.getTime()));
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// ִ�е����������
				GregorianCalendar stoday = new GregorianCalendar();
				if (!stoday.after(gc)) {
					sectals++;
					System.out.println("sectals " + sectals);
					if (sectals == 5) {
						sectals = 0;
						// Ͷעʵʱ�����ʾ
						dispLotteryDate();
					}

					String diff = getDistanceTime(sf.format(stoday.getTime()), sf.format(gc.getTime()));
					jtimerp.setText("       ���뿪��ʱ�䣺" + diff);
				} else {
					System.out.println("ʱ�䵽");
					kj.setText("ʱ�䵽!!      ������~~~");
					// ���ɲ�Ʊ
					genLottery();
					// �����н�
					//analyLottery();
					// ��ʾ�н�����
					//String str = dispAnalyData();
					//zjArea.setText(str);
					timer.cancel();
				}
			}
		};

		// ���忪ʼ�ȴ�ʱ�� --- �ȴ� 5 ��
		long delay = 500;
		// ����ÿ��ִ�еļ��ʱ��
		int intevalPeriod = 1 * 1000;
		// ����������һ��ʱ��������
		// timer.scheduleAtFixedRate(task, delay, intevalPeriod);
		timer.schedule(task, delay, intevalPeriod);
	}

	/**
	 * ʵʱ��ʾͶע���
	 */
	public void dispLotteryDate() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test";
			String username = "root";
			String password = "123456";
			Connection conn = DriverManager.getConnection(url, username, password);
			String sql = "select * from  t_lot ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(sql);
			StringBuffer ts = new StringBuffer();
			while (rs.next()) {
				String s1 = rs.getString("stuname") + rs.getString("red1") + rs.getString("red2") + rs.getString("red3")
						+ rs.getString("red4") + rs.getString("red5") + rs.getString("red6") + rs.getString("blue")
						+ "\n";
				ts.append(s1);
			}
			// ja2.removeAll();
			ja.setText("");
			ja.append(ts.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ�����н����
	 * 
	 * @return String�н����
	 */
	public String dispAnalyData() {
		StringBuffer sb = new StringBuffer();
		sb.append(dispAnalyDataSingle(1)).append(dispAnalyDataSingle(2)).append(dispAnalyDataSingle(3))
				.append(dispAnalyDataSingle(4)).append(dispAnalyDataSingle(5)).append(dispAnalyDataSingle(6));
		return sb.toString();
	}

	/***
	 * ��ʾ�н�
	 * 
	 * @param level�н�����
	 * @return String�н��ַ���
	 */
	public String dispAnalyDataSingle(int level) {
		String sql = "";
		// String s1 = "";
		StringBuffer ts1 = new StringBuffer();
		switch (level) {
		case 1: {
			ts1.append("һ�Ƚ��н����" + "\n");
			sql = "select * from  t_lot where  flag='true' and level='level1'";
			break;
		}
		case 2: {
			ts1.append("���Ƚ��н����" + "\n");
			sql = "select * from  t_lot where  flag='true' and level='level2'";
			break;
		}
		case 3: {
			ts1.append("���Ƚ��н����" + "\n");
			sql = "select * from  t_lot where  flag='true' and level='level3'";
			break;
		}
		case 4: {
			ts1.append("�ĵȽ��н����" + "\n");
			sql = "select * from  t_lot where  flag='true' and level='level4'";
			break;
		}
		case 5: {
			ts1.append("��Ƚ��н����" + "\n");
			sql = "select * from  t_lot where  flag='true' and level='level5'";
			break;
		}
		case 6: {
			ts1.append("���Ƚ��н����" + "\n");
			sql = "select * from  t_lot where  flag='true' and level='level6'";
			break;
		}
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test";
			String username = "root";
			String password = "123456";
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs1 = pstmt.executeQuery();
			while (rs1.next()) {
				String tmps1 = rs1.getString("stuname") + "\n";
				ts1.append(tmps1);
			}

		} catch (Exception e) {

		}
		return ts1.toString();
	}

	/**
	 * ����ʱ����������������Сʱ���ٷֶ�����
	 * 
	 * @param str1 ʱ����� 1 ��ʽ��1990-01-01 12:00:00
	 * @param str2 ʱ����� 2 ��ʽ��2009-01-01 12:00:00
	 * @return String ����ֵΪ��xx��xxСʱxx��xx��
	 */
	public static String getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day + "��" + hour + "Сʱ" + min + "��" + sec + "��";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		OpenLottery olt = new OpenLottery();
		olt.setTitle("˫ɫ�򿪽�����");

		// ���ھ���
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		olt.setLocation((size.width - 600) / 2, (size.height - 700) / 2);
		olt.setSize(600, 700);
		olt.setVisible(true);

	}

}
