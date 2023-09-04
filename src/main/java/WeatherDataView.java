import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherDataView extends JFrame {
    private WeatherDataModel model;
    private JTextArea weatherTextArea;
    private JLabel tempText = new JLabel("00");
    private JLabel cityNameText;

    private JTextField cityTextField = new JTextField();
    private JLabel cityLabel = new JLabel("City Name:");
    private JLabel languageLabel = new JLabel("Language code (e.g., 'en' for English, 'es' for Spanish):");
    private JTextField languageTextField = new JTextField();
    private JButton submitButton = new JButton("Submit");
    private JButton favoriteButton = new JButton();
    JButton buttonMenu = new JButton();
    JButton buttonSearch = new JButton();
    JPanel inputPanel = new JPanel(new GridLayout(3, 2));
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextArea label4;
    private JLabel label5;
    private JLabel label6;
    private JTextArea favourite = new JTextArea();
    JDialog dialog = new JDialog(this, "Search Input");
    private JTextArea fourDay = new JTextArea();
    private JList<String> favouriteCities=new JList<>();
    private JLabel additionalLabel = new JLabel();
    DefaultTableModel tableModel = new DefaultTableModel(5, 3);
    private JButton iconButton;
    JPanel iconPanel = new JPanel(new GridBagLayout());
    GridBagConstraints iconButtonConstraints = new GridBagConstraints();

    public WeatherDataView(WeatherDataModel model) {
        this.model = model;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SkyMate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setContentPane(new BackgroundPanel());

        // Top panel for icon buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(445, 50));
        topPanel.setOpaque(false);

        // Menu Icon button
        Icon iconMenu = new ImageIcon(getClass().getClassLoader().getResource("Icons/Menu.png"));
        int width = 15;
        int height = 15;
        Image imageMenu = ((ImageIcon) iconMenu).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon resizedIconMenu = new ImageIcon(imageMenu);


        buttonMenu.setIcon(resizedIconMenu);
        buttonMenu.setPreferredSize(new Dimension(width, height));
        buttonMenu.setBorder(null);
        buttonMenu.setBackground(new Color(0, 0, 0, 0));
        buttonMenu.setOpaque(false);
        buttonMenu.setBorderPainted(false);

        topPanel.add(buttonMenu, BorderLayout.WEST);


        // Search Icon button
        Icon iconSearch = new ImageIcon(getClass().getClassLoader().getResource("Icons/search.png"));
        Image imageSearch = ((ImageIcon) iconSearch).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon resizedIconSearch = new ImageIcon(imageSearch);

        buttonSearch.setIcon(resizedIconSearch);
        buttonSearch.setPreferredSize(new Dimension(width, height));
        buttonSearch.setBorder(null);
        buttonSearch.setBackground(new Color(0, 0, 0, 0));
        buttonSearch.setOpaque(false);
        buttonSearch.setBorderPainted(false);

        topPanel.add(buttonSearch, BorderLayout.EAST);

        // Create the custom input panel

        inputPanel.add(cityLabel);
        inputPanel.add(cityTextField);
        cityTextField.setPreferredSize(new Dimension(10,5));
        inputPanel.add(new JLabel()); // Empty cell
        inputPanel.add(languageLabel);
        inputPanel.add(languageTextField,BorderLayout.NORTH);
        inputPanel.add(new JLabel()); // Empty cell
        inputPanel.add(submitButton);

        buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openInformationFrame();
            }
        });


        // Add top panel to background panel
        add(topPanel, BorderLayout.NORTH);


        // Middle panel with transparent text box
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setPreferredSize(new Dimension(445, 100));
        middlePanel.setOpaque(false);
        //middlePanel.setBackground(Color.RED);

        // Transparent text box
        cityNameText = new JLabel();
        cityNameText.setPreferredSize(new Dimension(350,50));
        cityNameText.setOpaque(false);
        cityNameText.setBorder(null);
        // Create a custom Font with the desired size and bold style
        Font customFont = new Font(cityNameText.getFont().getName(), Font.BOLD, 30); // Adjust the size (16) as needed
        cityNameText.setFont(customFont);

        middlePanel.add(cityNameText, BorderLayout.CENTER);

        // Favorite Icon button
        Icon favourite = new ImageIcon(getClass().getClassLoader().getResource("Icons/favourite.png"));
        width = 15;
        height = 15;
        Image imageFavourite = ((ImageIcon) favourite).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon resizedIconfavourite = new ImageIcon(imageFavourite);

        favoriteButton.setPreferredSize(new Dimension(15, 5));
        favoriteButton.setBackground(new Color(0, 0, 0, 0));
        favoriteButton.setOpaque(false);
        favoriteButton.setBorderPainted(false);
        favoriteButton.setIcon(resizedIconfavourite);
        middlePanel.add(favoriteButton, BorderLayout.EAST);



        // Add middle panel to background panel
        add(middlePanel);


        // Create the new panel with GridBagLayout

        iconPanel.setPreferredSize(new Dimension(458, 100));
        iconPanel.setOpaque(false);

        ImageIcon icon = getWeatherIcon(model.getDescription());

        ImageIcon resizedIcon = resizeIcon(icon, 50, 50);

        iconButton = new JButton(resizedIcon);
        iconButton.setBackground(new Color(0, 0, 0, 0));
        iconButton.setOpaque(false);
        iconButton.setBorderPainted(false);

        // Create the text label


        // Set constraints for the icon button

        iconButtonConstraints.gridx = 0;
        iconButtonConstraints.gridy = 0;
        iconButtonConstraints.anchor = GridBagConstraints.LINE_START;

        // Set constraints for the text label
        GridBagConstraints textLabelConstraints = new GridBagConstraints();
        textLabelConstraints.gridx = 1;
        textLabelConstraints.gridy = 0;
        textLabelConstraints.anchor = GridBagConstraints.LINE_START;

        tempText.setBackground(Color.GREEN);
        tempText.setFont(customFont);
        // Add the icon button and text label to the icon panel
        iconPanel.add(iconButton, iconButtonConstraints);
        iconPanel.add(tempText, textLabelConstraints);

        // Create the text label
        additionalLabel.setFont(new Font(additionalLabel.getFont().getName(), Font.PLAIN, 15));

        // Set constraints for the additional label
        GridBagConstraints additionalLabelConstraints = new GridBagConstraints();
        additionalLabelConstraints.gridx = 1;
        additionalLabelConstraints.gridy = 1;
        additionalLabelConstraints.anchor = GridBagConstraints.LINE_START;

        // Add the additional label to the icon panel
        iconPanel.add(additionalLabel, additionalLabelConstraints);



        // Add the new panel between middlePanel and bottomPanel
        add(iconPanel);


        // Bottom panel with icons and text
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        bottomPanel.setPreferredSize(new Dimension(458, 50));
        bottomPanel.setOpaque(false);

        // Create icons and resize them
        ImageIcon icon1 = new ImageIcon(getClass().getClassLoader().getResource("Icons/Humidity.png"));
        ImageIcon resizedIcon1 = resizeIcon(icon1, 30, 30);
        JButton iconButton1 = new JButton(resizedIcon1);
        iconButton1.setContentAreaFilled(false); // Remove button background
        iconButton1.setBorderPainted(false); // Remove button border
        bottomPanel.add(iconButton1);
        label1 = new JLabel();
        bottomPanel.add(label1);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 30)));


        ImageIcon icon2 = new ImageIcon(getClass().getClassLoader().getResource("Icons/Windspeed.png"));
        ImageIcon resizedIcon2 = resizeIcon(icon2, 30, 30);
        JButton iconButton2 = new JButton(resizedIcon2);
        iconButton2.setContentAreaFilled(false); // Remove button background
        iconButton2.setBorderPainted(false); // Remove button border
        bottomPanel.add(iconButton2);
        label2 = new JLabel();
        bottomPanel.add(label2);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 30)));

        ImageIcon icon3 = new ImageIcon(getClass().getClassLoader().getResource("Icons/Cloudiness.png"));
        ImageIcon resizedIcon3 = resizeIcon(icon3, 30, 30);
        JButton iconButton3 = new JButton(resizedIcon3);
        iconButton3.setContentAreaFilled(false); // Remove button background
        iconButton3.setBorderPainted(false); // Remove button border
        bottomPanel.add(iconButton3);
        label3 = new JLabel();
        bottomPanel.add(label3);

        // Add bottom panel to background panel
        add(bottomPanel, BorderLayout.SOUTH);

        // Bottom panel with icons and text
        JPanel bottomPanel2 = new JPanel(new GridLayout(1, 3));
        bottomPanel2.setPreferredSize(new Dimension(458, 50));
        bottomPanel2.setOpaque(false);

        ImageIcon icon4 = new ImageIcon(getClass().getClassLoader().getResource("Icons/temp.png"));
        ImageIcon resizedIcon4 = resizeIcon(icon4,30,30);
        JButton iconButton4 = new JButton(resizedIcon4);
        iconButton4.setContentAreaFilled(false);
        iconButton4.setBorderPainted(false);
        bottomPanel2.add((iconButton4));
        label4 = new JTextArea();
        label4.setEditable(false);
        Font boldFont = label4.getFont().deriveFont(Font.BOLD, 12);
        label4.setFont(boldFont);
        label4.setPreferredSize(new Dimension(50,50));
        label4.setOpaque(false);
        label4.setBorder(null);
        label4.setBorder(new EmptyBorder(5, 0, 0, 0));
        bottomPanel2.add(label4);
        bottomPanel2.add(Box.createRigidArea(new Dimension(10, 30)));

        ImageIcon icon5 = new ImageIcon(getClass().getClassLoader().getResource("Icons/sunrise.png"));
        ImageIcon resizedIcon5 = resizeIcon(icon5, 30, 30);
        JButton iconButton5 = new JButton(resizedIcon5);
        iconButton5.setContentAreaFilled(false);
        iconButton5.setBorderPainted(false);
        bottomPanel2.add(iconButton5);
        label5 = new JLabel();
        bottomPanel2.add(label5);
        bottomPanel2.add(Box.createRigidArea(new Dimension(10, 30)));

        ImageIcon icon6 = new ImageIcon(getClass().getClassLoader().getResource("Icons/sunset.png"));
        ImageIcon resizedIcon6 = resizeIcon(icon6, 30, 30);
        JButton iconButton6 = new JButton(resizedIcon6);
        iconButton6.setContentAreaFilled(false);
        iconButton6.setBorderPainted(false);
        bottomPanel2.add(iconButton6);
        label6 = new JLabel();
        bottomPanel2.add(label6);

        // Add bottom panel to background panel
        add(bottomPanel2, BorderLayout.SOUTH);

        // Text box
        JPanel lastPanel = new JPanel();
        lastPanel.setLayout(new BoxLayout(lastPanel, BoxLayout.Y_AXIS));
        lastPanel.setPreferredSize(new Dimension(458, 500));
        lastPanel.setOpaque(false);
        // Add empty space at the top of the lastPanel
        int topPadding = 20;
        lastPanel.setBorder(BorderFactory.createEmptyBorder(topPadding, 0, 0, 0));

        // Create the table model with 3 rows and 4 columns


        // Create the JTable using the table model
        fourDay.setPreferredSize(new Dimension(455, 500));
        fourDay.setBackground(Color.DARK_GRAY);
        fourDay.setForeground(Color.WHITE);
        fourDay.setEditable(false);
        lastPanel.add(fourDay);
        add(lastPanel);

        // Add the background panel to the JFrame

        pack();
        setVisible(true);
    }
    public ImageIcon getWeatherIcon(String description) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/temp.png"));
        if(description!=null) {
            if (description.equals("clear sky")) {
                icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/clearsky.png"));
            } else if (description.equals("few clouds")) {
                icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/fewclouds.png"));
            } else if (description.equals("scattered clouds")) {
                icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/scattered clouds.png"));
            } else if (description.equals("broken clouds")) {
                icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/broken clouds.png"));
            } else if (description.equals("shower rain")) {
                icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/showerrain.png"));
            } else if (description.equals("rain")) {
                icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/rain.png"));
            } else if (description.equals("thunderstorm")) {
                icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/thunderstorm.png"));
            } else if (description.equals("snow")) {
                icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/snow.png"));
            } else if (description.equals("mist")) {
                icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/mist.png"));
            }
            else if(description.equals("haze")) icon = new ImageIcon(getClass().getClassLoader().getResource("Icons/haze.png"));
        }
        return icon;
    }
    public void updateWeatherIcon(String description) {

        // Load the appropriate icon based on the weather description
        ImageIcon icon = getWeatherIcon(description);
        ImageIcon resizedIcon = null;

        // Resize the icon
        if(icon!=null) resizedIcon = resizeIcon(icon, 50, 50);

        // Set the new icon on the icon button
        iconButton.setIcon(resizedIcon);
        iconPanel.add(iconButton, iconButtonConstraints);
    }
    private void openInformationFrame() {
        // Perform actions to open the new frame with information
        JFrame informationFrame = new JFrame("Favourite Cities");
        // Add your code to populate the information frame with content
        favourite.setEditable(false);
        favourite.setFont(new Font("Arial", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(favourite);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // Add the scroll pane to the information frame
        informationFrame.getContentPane().add(scrollPane);
        // Set frame properties
        informationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        informationFrame.setSize(400, 300);
        informationFrame.setLocationRelativeTo(this);
        informationFrame.setVisible(true);
        //favourite.setText(model.getFavoriteCities().toString());
        String joinedCities = String.join("\n", model.getFavoriteCities());
        favourite.setText(joinedCities);

    }
    public void setTextFavourite(String s){
        favourite.setText(s);
    }
    // Method to resize the icons
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            ImageIcon backgroundImageIcon = new ImageIcon(
                    getClass().getClassLoader().getResource("Icons/3.jpg"));
            backgroundImage = backgroundImageIcon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    public void addSubmitListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }
    public void addSearchListener(ActionListener listener) {
        buttonSearch.addActionListener(listener);
    }
    public void addAddToFavoritesListener(ActionListener listener) {
        favoriteButton.addActionListener(listener);
    }
    public void setFavoriteCitiesListModel(DefaultListModel<String> model) {favouriteCities.setModel(model);}
    public DefaultListModel<String> getFavoriteCitiesListModel() {
        return (DefaultListModel<String>) favouriteCities.getModel();
    }
    public void opeDialog(){
        dialog.getContentPane().add(inputPanel);
        dialog.setSize(400,200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    public String getCity() {
        return cityTextField.getText();
    }

    public String getLanguage() {
        return languageTextField.getText();
    }
    public void setCityName(String s){
        cityNameText.setText(s);
    }
    public void setLabel1(String s){
        label1.setText(s);
    }
    public void setLabel2(String s){
        label2.setText(s);
    }
    public void setLabel3(String s){
        label3.setText(s);
    }
    public void setLabel4(String s){
        label4.setText(s);
    }
    public void setLabel5(String s){
        label5.setText(s);
    }
    public void setLabel6(String s){
        label6.setText(s);
    }
    public void setTempText(String s){
        tempText.setText(s);
    }
    public void setFourDay(String s){
        fourDay.setText(s);
    }
    public void setAdditionalLabel(String s){additionalLabel.setText(s);}
}
