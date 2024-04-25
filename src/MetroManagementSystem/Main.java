package MetroManagementSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import Trips.BookTrip;
import Trips.Trip;
import Trips.TripsDatabase;

public class Main
{
    private static JFrame frame;
    private static JPanel table;
    private static GridLayout gridLayout;
    private static Database database;
    public static void main(String[] args) throws SQLException
    {
        database = Database.getInstance();

        frame=new JFrame("Metro Management System");
        frame.setSize(1050,650);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.decode("#EBFFD8"));
        frame.setLocationRelativeTo(null);

        JPanel panel=new JPanel(new BorderLayout(20,20));
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(50,40,30,40));

        JLabel title=new JLabel("Welcome to Metro Management System!");
        title.setForeground(Color.decode("#012030"));
        title.setFont(new Font(null,Font.BOLD,35));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(title,BorderLayout.NORTH);

        gridLayout=new GridLayout(6,1);
        table=new JPanel(gridLayout);
        table.setBackground(Color.decode("#EBFFD8"));

        ArrayList<Trip> trips=TripsDatabase.getAllTrips(database);
        refreshTable(trips);

        JScrollPane sp=new JScrollPane(table);
        panel.add(sp,BorderLayout.CENTER);

        JButton adminLoginButton = new JButton("Log in as Admin");
        adminLoginButton.setBackground(Color.decode("#45C480"));
        adminLoginButton.setForeground(Color.white);
        adminLoginButton.setFont(new Font(null, Font.BOLD, 22));
        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAdminLoginPage();
            }
        });

        JButton userLoginButton = new JButton("Log in as User");
        userLoginButton.setBackground(Color.decode("#45C480"));
        userLoginButton.setForeground(Color.white);
        userLoginButton.setFont(new Font(null, Font.BOLD, 22));
        userLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserLoginPage();
            }
        });

        panel.add(adminLoginButton,BorderLayout.WEST);
        panel.add(userLoginButton,BorderLayout.EAST);

        frame.add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void refreshTable(ArrayList<Trip> trips)
    {
        table.removeAll();
        table.repaint();
        table.revalidate();
        int rows=trips.size()+1;
        if(rows<6)
        rows=6;
        gridLayout.setRows(rows);
        table.add(row(0,null));
        for(int i=0;i<trips.size();i++)
        {
            JPanel trip=row(i+1,trips.get(i));
            table.add(trip);
        }
    }

    private static JPanel row(int index,Trip trip)
    {
        JPanel row=new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        if (index%2==0)
        row.setBackground(Color.decode("#e5e5e5"));
        else
        row.setBackground(Color.decode("#EEEEEE"));

        String trainS,startS,destS,dateS,deptS,arrS,priceS,statusS;

        if(trip!=null)
        {
            trainS=trip.getTrain().getDescription();
            startS=trip.getStart();
            destS=trip.getDestination();
            dateS=trip.getDate();
            deptS=trip.getDepartureTime();
            arrS=trip.getArrivalTime();
            priceS=trip.getPrice()+"$";
            statusS="Booked";
            if(trip.getTrain().getCapacity()>trip.getBookedSeats())
            statusS="Available";
            row.setCursor(new Cursor(Cursor.HAND_CURSOR));
            row.addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    try
                    {
                        new BookTrip(frame, database, trip);
                    }
                    catch(SQLException e1)
                    {
                        JOptionPane.showMessageDialog(frame,e1.getMessage());
                    }
                }
            });
        }
        else
        {
            trainS="Train";
            startS="From";
            destS="To";
            dateS="Date";
            deptS="Dept";
            arrS="Arr";
            priceS="Price";
            statusS="Status";
        }

        JLabel train=JLabel(trainS,100);
        row.add(train);

        JLabel start=JLabel(startS,100);
        row.add(start);

        JLabel dest=JLabel(destS,100);
        row.add(dest);

        JLabel date=JLabel(dateS,150);
        row.add(date);

        JLabel deptTime=JLabel(deptS,65);
        row.add(deptTime);

        JLabel arrTime=JLabel(arrS,65);
        row.add(arrTime);

        JLabel price=JLabel(priceS,70);
        row.add(price);

        JLabel status=JLabel(statusS, 100);
        row.add(status);

        return row;
    }

    private static JLabel JLabel(String text, int width)
    {
        JLabel label=new JLabel(text);
        label.setPreferredSize(new Dimension(width,20));
        label.setFont(new Font(null,Font.PLAIN,20));
        label.setForeground(Color.decode("#13678A"));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private static void openAdminLoginPage() 
    {
        JFrame adminLoginFrame = new JFrame("Admin Login");
        adminLoginFrame.setSize(400, 300);
        adminLoginFrame.getContentPane().setLayout(new BorderLayout());
        adminLoginFrame.setLocationRelativeTo(frame);
    
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30, 50));
    
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
    
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
    
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        loginButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateAdminLogin(username, password)) 
                {
                    // Admin login successful, perform actions
                    JOptionPane.showMessageDialog(adminLoginFrame, "Admin login successful!");
                    new ModifyList(frame,database);
                } 
                else 
                {
                    JOptionPane.showMessageDialog(adminLoginFrame, "Invalid username or password!");
                }
            }
        });
    
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
    
        adminLoginFrame.add(loginPanel);
        adminLoginFrame.setVisible(true);
    }
    

    private static void openUserLoginPage() 
    {
        JFrame userLoginFrame = new JFrame("User Login / Sign Up");
        userLoginFrame.setSize(1000, 400);
        userLoginFrame.getContentPane().setLayout(new BorderLayout());
        userLoginFrame.setLocationRelativeTo(frame);
    
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Set FlowLayout with center alignment and gaps
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30, 50));
    
        JLabel titleLabel = new JLabel("User Login / Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font(null, Font.BOLD, 20));
    
        JPanel userLoginPanel = new JPanel(new BorderLayout());
        userLoginPanel.setBorder(BorderFactory.createTitledBorder("Login"));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateUserLogin(username, password)) 
                {
                    JOptionPane.showMessageDialog(userLoginFrame, "User login successful!");
                    new ModifyList2(frame, database);
                } 
                else 
                {
                    JOptionPane.showMessageDialog(userLoginFrame, "Invalid username or password!");
                }
            }
        });
        userLoginPanel.add(usernameLabel, BorderLayout.NORTH);
        userLoginPanel.add(usernameField, BorderLayout.CENTER);
        userLoginPanel.add(passwordLabel, BorderLayout.WEST);
        userLoginPanel.add(passwordField, BorderLayout.EAST);
        userLoginPanel.add(loginButton, BorderLayout.SOUTH);
    
        // User registration panel
        JPanel userRegistrationPanel = new JPanel(new BorderLayout());
        userRegistrationPanel.setBorder(BorderFactory.createTitledBorder("Sign Up"));
        JLabel newUsernameLabel = new JLabel("New Username:");
        JTextField newUsernameField = new JTextField(20);
        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField(20);
        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                if (validateSignUp(newUsername, newPassword)) 
                {
                    JOptionPane.showMessageDialog(userLoginFrame, "Sign up successful!");
                } 
                else 
                {
                    JOptionPane.showMessageDialog(userLoginFrame, "Sign up failed!");
                }
            }
        });
        userRegistrationPanel.add(newUsernameLabel, BorderLayout.NORTH);
        userRegistrationPanel.add(newUsernameField, BorderLayout.CENTER);
        userRegistrationPanel.add(newPasswordLabel, BorderLayout.WEST);
        userRegistrationPanel.add(newPasswordField, BorderLayout.EAST);
        userRegistrationPanel.add(signupButton, BorderLayout.SOUTH);
    
        loginPanel.add(titleLabel);
        loginPanel.add(userLoginPanel);
        loginPanel.add(userRegistrationPanel);
    
        userLoginFrame.add(loginPanel);
        userLoginFrame.setVisible(true);
    }
    
    

    private static boolean validateAdminLogin(String username, String password) 
    {
        // Query the database to validate admin login
        try {
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM admins WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If a row is returned, admin login is valid
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static boolean validateUserLogin(String username, String password) {
        // Query the database to validate user login
        try {
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If a row is returned, user login is valid
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static boolean validateSignUp(String username, String password) {
        // Check if the username already exists in the database
        try {
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Username already exists
                JOptionPane.showMessageDialog(null, "Username already exists!");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    
        try {
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(
                    "INSERT INTO users (username, password) VALUES (?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // User registration successful
                return true;
            } else {
                // User registration failed
                JOptionPane.showMessageDialog(null, "Failed to register user!");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }    
}




