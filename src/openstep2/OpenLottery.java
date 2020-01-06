package openstep2;

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
	JLabel jtime = new JLabel("        开奖时间：");
	JLabel jtimerp = new JLabel("       距离开奖时间：");
	JLabel kj = new JLabel("马上就要开奖了~~~~~");
	JTextArea ja2 = new JTextArea();
	JTextArea ja = new JTextArea();
	JLabel label1 = new JLabel(
			"   红球1         红球2         红球3          红球4        红球5         红球6            蓝");
	JLabel label2 = new JLabel("");
	

	int sectals = 0; // 设置刷新数据的时间间隔

	public OpenLottery() {
		this.setLayout(null);
		panal1.setBounds(20, 20, 600, 50);
		panal1.setBackground(Color.RED);
		panal1.setLayout(new GridLayout(2, 1));
		panal1.add(jtime);
		panal1.add(jtimerp);
		dispTime();

		panal2.setBounds(20, 70, 600, 200);
		panal2.setBackground(Color.GREEN);
		JLabel tz = new JLabel("投注情况");
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
		JLabel zj = new JLabel("中奖情况");
		ScrollPane sPanel2 = new ScrollPane();
		sPanel2.setSize(600, 200);

		ja.setSize(600, 580);
		sPanel2.add(ja2);
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

	
	

	
	public void dispTime() {
		GregorianCalendar tmp = new GregorianCalendar();
		// 开奖时间
		GregorianCalendar gc = new GregorianCalendar(tmp.get(Calendar.YEAR),
				tmp.get(Calendar.MONTH), tmp.get(Calendar.DAY_OF_MONTH), 14,
				20, 0);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		jtime.setText("        开奖时间：" + sf.format(gc.getTime()));
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// 执行的输出的内容
				GregorianCalendar stoday = new GregorianCalendar();
				if (!stoday.after(gc)) {
					sectals++;
					System.out.println("sectals " + sectals);
					if (sectals == 5) {
						sectals = 0;
						dispLotteryDate();
					}

					String diff = getDistanceTime(sf.format(stoday.getTime()),
							sf.format(gc.getTime()));
					jtimerp.setText("       距离开奖时间：" + diff);
				} else {
					System.out.println("时间到");
					timer.cancel();
				}
			}
		};

		// 定义开始等待时间 --- 等待 5 秒
		long delay = 500;
		// 定义每次执行的间隔时间
		int intevalPeriod = 1 * 1000;
		// 安排任务在一段时间内运行
		//timer.scheduleAtFixedRate(task, delay, intevalPeriod);
		timer.schedule(task, delay, intevalPeriod);
	}

	/**
	 * 显示当前投注数据
	 */
	public void dispLotteryDate() {
		boolean flag = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test";
			String username = "root";
			String password = "123456";
			Connection conn = DriverManager.getConnection(url, username,
					password);
			String sql = "select * from  t_lot ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(sql);
			StringBuffer ts = new StringBuffer();
			while (rs.next()) {
				String s1 = rs.getString("stuname") + rs.getString("red1")
						+ rs.getString("red2") + rs.getString("red3")
						+ rs.getString("red4") + rs.getString("red5")
						+ rs.getString("red6") + rs.getString("blue") + "\n";
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
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
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
		return day + "天" + hour + "小时" + min + "分" + sec + "秒";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		OpenLottery olt = new OpenLottery();
		olt.setTitle("双色球开奖中心");

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		olt.setLocation((size.width - 600) / 2, (size.height - 700) / 2);
		olt.setSize(600, 700);
		olt.setVisible(true);

	}

}
