import javax.swing.*;
import java.awt.*;
//import ContactApp.ContactApp;

public class HomeScreen extends Homescreen.screen {
    public HomeScreen(){
        // Create the buttons
        JButton contactButton = new JButton("Contacts"); //new ImageIcon("")
        JButton weatherButton = new JButton("Weather");
        JButton galleryButton = new JButton("Gallery");
        JButton calendarButton = new JButton("Calendar");
        JButton eventButton = new JButton("Events");

        // Create the buttons with icons:
        //JButton contactButton = new JButton(new ImageIcon("chemin/vers/image.png"));

        // Set the size and position of the buttons
        contactButton.setBounds(50, 60, 100, 100);
        weatherButton.setBounds(175, 60, 100, 100);
        galleryButton.setBounds(300, 60, 100, 100);
        calendarButton.setBounds(50, 200, 100, 100);
        eventButton.setBounds(175, 200, 100, 100);

        // Add the buttons to the JFrame (visible)
        add(contactButton);
        add(weatherButton);
        add(galleryButton);
        add(calendarButton);
        add(eventButton);

        // Add an ActionListener to open the window of contacts apps
//        contactButton.addActionListener(e -> {
//            ContactApp contactApp = new ContactApp();
//            dispose();//close the actual page
//            contactApp.setVisible(true);
//        });

        //Add an ActionListener to open the window of weather app
        weatherButton.addActionListener(e->{
            WeatherApp weatherApp=new WeatherApp();
            weatherApp.main();
            dispose();
        });
        // Load the background image
        ImageIcon backgroundImage = new ImageIcon(" ");

        // Create a JLabel to display the background image
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        // Set the background color (optional)
        getContentPane().setBackground(Color.gray);
    }
    // Load the background image
    ImageIcon backgroundImage = new ImageIcon(" ");
    public  static void main(String[] args) {
        HomeScreen HomeScreen = new HomeScreen();
        HomeScreen.setVisible(true);//snow the new window
    }
}
