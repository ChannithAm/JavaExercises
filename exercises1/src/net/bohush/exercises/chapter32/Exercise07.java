package net.bohush.exercises.chapter32;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

public class Exercise07 extends JApplet {
	private ClockPanel clockPanel1 = new ClockPanel();
	private ClockPanel clockPanel2 = new ClockPanel();
	private ClockPanel clockPanel3 = new ClockPanel();	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		Exercise07 applet = new Exercise07();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Exercise07");
		frame.add(applet, BorderLayout.CENTER);
		frame.setSize(900, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public Exercise07() {
		JPanel panel1 = new JPanel(new GridLayout(1, 3));
		panel1.add(clockPanel1);
		panel1.add(clockPanel2);
		panel1.add(clockPanel3);
		add(panel1, BorderLayout.CENTER);
		JPanel panel2 = new JPanel();
		JButton jButton1 = new JButton("Resume All");
		JButton jButton2 = new JButton("Suspend All");
		jButton1.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				clockPanel1.start();
				clockPanel2.start();
				clockPanel3.start();
			}
		});
		jButton2.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				clockPanel1.stop();
				clockPanel2.stop();
				clockPanel3.stop();
			}
		});
		panel2.add(jButton2);
		panel2.add(jButton1);
		add(panel2, BorderLayout.SOUTH);
	}

	class ClockPanel extends JPanel implements Runnable {
		private JButton jbtStart = new JButton("Resume");
		private JButton jbtStop = new JButton("Suspend");
		private boolean isRunning = true;
		private static final long serialVersionUID = 1L;
		private StillClock clock = new StillClock();
		
		public void stop() {
			isRunning = false;
		}
		
		public void start() {
			isRunning = true;
		}
		
		public ClockPanel() {
			setLayout(new BorderLayout());
			JPanel panel1 = new JPanel();
			jbtStop.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					isRunning = false;
				}
			} );
			panel1.add(jbtStop);
			jbtStart.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					isRunning = true;				
				}
			} );		
			panel1.add(jbtStart);
			add(panel1, BorderLayout.SOUTH);
			add(clock, BorderLayout.CENTER);
			Thread thread = new Thread(this);
			thread.start();
		}
	
		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(1000);	
					if(isRunning) {
						clock.setCurrentTime();
						clock.repaint();						
					}
				}				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	class StillClock extends JPanel {
		private static final long serialVersionUID = 1L;
		private int hour;
		private int minute;
		private int second;

		/** Construct a default clock with the current time */
		public StillClock() {
			setCurrentTime();
		}

		/** Construct a clock with specified hour, minute, and second */
		public StillClock(int hour, int minute, int second) {
			this.hour = hour;
			this.minute = minute;
			this.second = second;
		}

		/** Return hour */
		public int getHour() {
			return hour;
		}

		/** Set a new hour */
		public void setHour(int hour) {
			this.hour = hour;
			repaint();
		}

		/** Return minute */
		public int getMinute() {
			return minute;
		}

		/** Set a new minute */
		public void setMinute(int minute) {
			this.minute = minute;
			repaint();
		}

		/** Return second */
		public int getSecond() {
			return second;
		}

		/** Set a new second */
		public void setSecond(int second) {
			this.second = second;
			repaint();
		}

		@Override
		/** Draw the clock */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Initialize clock parameters
			int clockRadius = (int) (Math.min(getWidth(), getHeight()) * 0.8 * 0.5);
			int xCenter = getWidth() / 2;
			int yCenter = getHeight() / 2;

			// Draw circle
			g.setColor(Color.black);
			g.drawOval(xCenter - clockRadius, yCenter - clockRadius,
					2 * clockRadius, 2 * clockRadius);
			g.drawString("12", xCenter - 5, yCenter - clockRadius + 12);
			g.drawString("9", xCenter - clockRadius + 3, yCenter + 5);
			g.drawString("3", xCenter + clockRadius - 10, yCenter + 3);
			g.drawString("6", xCenter - 3, yCenter + clockRadius - 3);

			// Draw second hand
			int sLength = (int) (clockRadius * 0.8);
			int xSecond = (int) (xCenter + sLength
					* Math.sin(second * (2 * Math.PI / 60)));
			int ySecond = (int) (yCenter - sLength
					* Math.cos(second * (2 * Math.PI / 60)));
			g.setColor(Color.red);
			g.drawLine(xCenter, yCenter, xSecond, ySecond);

			// Draw minute hand
			int mLength = (int) (clockRadius * 0.65);
			int xMinute = (int) (xCenter + mLength
					* Math.sin(minute * (2 * Math.PI / 60)));
			int yMinute = (int) (yCenter - mLength
					* Math.cos(minute * (2 * Math.PI / 60)));
			g.setColor(Color.blue);
			g.drawLine(xCenter, yCenter, xMinute, yMinute);

			// Draw hour hand
			int hLength = (int) (clockRadius * 0.5);
			int xHour = (int) (xCenter + hLength
					* Math.sin((hour % 12 + minute / 60.0) * (2 * Math.PI / 12)));
			int yHour = (int) (yCenter - hLength
					* Math.cos((hour % 12 + minute / 60.0) * (2 * Math.PI / 12)));
			g.setColor(Color.green);
			g.drawLine(xCenter, yCenter, xHour, yHour);
		}

		public void setCurrentTime() {
			// Construct a calendar for the current date and time
			Calendar calendar = new GregorianCalendar();

			// Set current hour, minute and second
			this.hour = calendar.get(Calendar.HOUR_OF_DAY);
			this.minute = calendar.get(Calendar.MINUTE);
			this.second = calendar.get(Calendar.SECOND);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(200, 200);
		}
	}

}

