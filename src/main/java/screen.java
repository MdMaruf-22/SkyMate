package Homescreen;

//import ContactApp.ContactApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import com.sun.jna.platform.win32.Advapi32Util;
//import Homescreen.HomeScreen;
import javax.swing.*;

public class screen extends JFrame{
	public screen (){
	    super("Ip-mobile-app");

	    // Set the size of the JFrame
	    setSize(458, 802); // size 7"

	    //Stop the running app when we close it (when we close the window)
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	    // Set the layout to null to be able to position components freely
	    setLayout(null);
	    
	    // Center the window on the Homescreen.screen
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocationRelativeTo(null);
	    
	    //To customer won't be able to change the size of the window.
	    setResizable(false);

		// Create the buttons
		JButton returnButton = new JButton("return");
		JButton quitButton = new JButton("Q");
		JButton backButton = new JButton(">");

		// Create the buttons with icons:
		//JButton contactButton = new JButton(new ImageIcon("chemin/vers/image.png"));

		// Set the size and position of the buttons
		returnButton.setBounds(100, 705, 50, 50);
		quitButton.setBounds(200, 705, 50, 50);
		backButton.setBounds(300, 705, 50, 50);

		// Add the buttons to the JFrame (visible)
		add(returnButton);
		add(quitButton);
		add(backButton);

		// Add an ActionListener to turn off
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// Add an ActionListener to come back to the HomeScreen
//		backButton.addActionListener(e -> {
//			HomeScreen HomeScreen = new HomeScreen();
//			dispose();//close the actual page
//			HomeScreen.setVisible(true);
//		});

	 // Create a label to display the current time
	    JLabel timeLabel = new JLabel();
	    timeLabel.setBounds(20, 20, 150, 10); //size and position
	    timeLabel.setFont(new Font("Arial", Font.BOLD, 12)); //front
	    add(timeLabel); //make the time visible
	    
	    // Update the time label every second
	    Timer timer = new Timer(1000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            Calendar now = Calendar.getInstance(); //actual time
	            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//time format
	            // defines the text displayed,convert the current time to a specified format string.
	            timeLabel.setText(sdf.format(now.getTime()));
	        }
	    });
	    timer.start();
	    
	    // Create a label to display the battery level
	    JLabel batteryLabel = new JLabel();
	    batteryLabel.setBounds(getWidth() - 70, 20, 50, 10);//size and position
	    batteryLabel.setFont(new Font("Arial", Font.BOLD, 12));//front
	    add(batteryLabel);
	    
	 // Update the battery label every second
	    Timer batteryTimer = new Timer(1000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            int batteryLevel = (int) (Math.random() * 100); //change to reel batterie
	            //int batteryLevel = Advapi32Util.getBatteryChargePercent();
	            /*Linux version
	             * import java.nio.file.Files;
	             * import java.nio.file.Paths;
				 // ...
				 * String batteryPath = "/sys/class/power_supply/BAT0/capacity";
				 * int batteryLevel = Integer.parseInt(Files.readString(Paths.get(batteryPath)).trim());
	             */
	            batteryLabel.setText( batteryLevel + "%");
	        }
	    });
	    batteryTimer.start();
	    
	    // Create a panel for the top bar
	    JPanel topBar = new JPanel();
	    topBar.setLayout(new BorderLayout());
	    topBar.setBounds(0, 0, getWidth(), 50); //getWidth() = largeur du panneau
	    topBar.setBackground(Color.WHITE); // color of the panel
	    add(topBar);

	    // Create a panel for the bottom bar
	    JPanel bottomBar = new JPanel();
	    bottomBar.setLayout(new BorderLayout());
	    bottomBar.setBounds(0, 702, getWidth(), 80); //getHeight= tout en bas de la fenêtre
	    bottomBar.setBackground(Color.WHITE);
	    add(bottomBar);
	   
	    // Load the background image
	    ImageIcon backgroundImage = new ImageIcon(" ");//"chemin/vers/image.png"

	    // Create a JLabel to display the background image
	    JLabel backgroundLabel = new JLabel(backgroundImage);
	    backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
	    add(backgroundLabel);

	    // Set the background color (optional)
	    getContentPane().setBackground(Color.lightGray);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		screen defaultScreen = new screen();
		   defaultScreen.setVisible(true);//snow the new window
	}

}
