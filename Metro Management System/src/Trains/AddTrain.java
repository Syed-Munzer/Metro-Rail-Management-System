package Trains;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MetroManagementSystem.Database;
import MetroManagementSystem.GUI;

public class AddTrain 
{
    public AddTrain(JFrame oldFrame, Database database) throws SQLException
    {
        JFrame frame=new JFrame("Add Train");
        frame.setSize(750,350);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setLocationRelativeTo(oldFrame);
        frame.getContentPane().setBackground(GUI.background);

        JPanel panel=new JPanel(new GridLayout(4,2,20,20));
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30,30));

        panel.add(GUI.JLabel("ID:"));
        JLabel id = GUI.JLabel(String.valueOf(TrainsDatabase.getNextID(database)));
        panel.add(id);
        // try 
        // {
        //     JLabel id = JLabel(String.valueOf(TrainsDatabase.getNextID(database)));
        //     panel.add(id);
        // } 
        // catch (SQLException e) 
        // {
        //     e.printStackTrace(); 
        // }

        panel.add(GUI.JLabel("Capacity:"));

        JTextField capacity=GUI.JTextField();
        panel.add(capacity);

        panel.add(GUI.JLabel("Description:"));

        JTextField description=GUI.JTextField();
        panel.add(description);

        JButton cancel=GUI.JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
            }
        });
        panel.add(cancel);

        JButton submit=GUI.JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Train t=new Train();
                t.setID(Integer.parseInt(id.getText()));
                t.setCapacity(Integer.parseInt(capacity.getText()));
                t.setDescription(description.getText());
                try
                {
                    TrainsDatabase.AddTrain(t,database);
                    JOptionPane.showMessageDialog(frame, "Train added successfully");
                    frame.dispose();
                }
                catch(SQLException e1)
                {
                    JOptionPane.showMessageDialog(frame, "Operation Failed");
                    frame.dispose();
                }
            }
        });
        panel.add(submit);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
