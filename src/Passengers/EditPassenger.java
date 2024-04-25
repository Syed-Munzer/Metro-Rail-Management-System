package Passengers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MetroManagementSystem.Database;
import MetroManagementSystem.GUI;

public class EditPassenger 
{
    private JComboBox<String> id;
    private JTextField name;
    private JTextField email;
    private JTextField tel;

    public EditPassenger(JFrame parent, Database database) throws SQLException
    {
        JFrame frame=new JFrame("Edit Passenger");
        frame.setSize(750,500);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setLocationRelativeTo(parent);
        frame.getContentPane().setBackground(GUI.background);

        JPanel panel=new JPanel(new GridLayout(5,2,20,20));
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30,50));

        panel.add(GUI.JLabel("ID:"));
        id=GUI.JComboBox(PassengersDatabase.getIDs(database));
        panel.add(id);

        panel.add(GUI.JLabel("Name:"));
        name=GUI.JTextField();
        panel.add(name);

        panel.add(GUI.JLabel("Email:"));
        email=GUI.JTextField();
        panel.add(email);

        panel.add(GUI.JLabel("Tel:"));
        tel=GUI.JTextField();
        panel.add(tel);

        JButton submit=GUI.JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Passenger p=new Passenger();
                p.setID(Integer.parseInt(id.getSelectedItem().toString()));
                p.setName(name.getText());
                p.setEmail(email.getText());
                p.setTel(tel.getText());
                try
                {
                    PassengersDatabase.EditPassenger(p,database);
                    JOptionPane.showMessageDialog(frame, "Passenger updated successfully");
                    frame.dispose();
                }
                catch(SQLException e1)
                {
                    JOptionPane.showMessageDialog(frame, "Operation Failed.");
                    frame.dispose();
                }
            }
        });
        panel.add(submit);

        JButton delete=GUI.JButton("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    PassengersDatabase.DeletePassenger(id.getSelectedItem().toString(), database);
                    JOptionPane.showMessageDialog(frame, "Passenger deleted succesfully");
                    frame.dispose();
                }
                catch(SQLException e1)
                {
                    JOptionPane.showMessageDialog(frame, "Operation Failed.");
                    frame.dispose();
                }
            }
        });
        panel.add(delete);

        id.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    refreshData(database);
                }
                catch (SQLException e1)
                {
                    JOptionPane.showMessageDialog(frame, e1.getMessage());
                    frame.dispose();
                }
            }
        });


        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void refreshData(Database database) throws SQLException
    {
        if(id.getSelectedItem()!=null)
        {
            String ID=id.getSelectedItem().toString();
            Passenger p=PassengersDatabase.getPassenger(ID, database);
            name.setText(p.getName());
            email.setText(p.getEmail());
            tel.setText(p.getTel());
        }
    }
}
