package MetroManagementSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Passengers.*;
import Trips.BookTrip;

public class ModifyList2
{
    public ModifyList2(JFrame oldFrame, Database database)
    {
        JFrame frame=new JFrame("Modify");
        frame.setSize(400,600);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setLocationRelativeTo(oldFrame);
        frame.getContentPane().setBackground(Color.decode("#EBFFD8"));

        JPanel panel=new JPanel(new GridLayout(9, 18, 10, 10));
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton addPassenger=JButton("Add Passenger");
        addPassenger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    new AddPassenger(frame,database);
                }
                catch(SQLException e1)
                {
                    JOptionPane.showMessageDialog(frame, e1.getMessage());
                }
            }
        });
        panel.add(addPassenger);

        JButton editPassenger=JButton("Edit Passenger");
        editPassenger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    new EditPassenger(frame,database);
                }
                catch(SQLException e1)
                {
                    JOptionPane.showMessageDialog(frame, e1.getMessage());
                }
            }
        });
        panel.add(editPassenger);

        frame.getContentPane().add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JButton JButton(String text)
    {
        JButton btn=new JButton(text);
        btn.setBackground(Color.decode("#45C4B0"));
        btn.setForeground(Color.white);
        btn.setFont(new Font(null, Font.BOLD,22));
        return btn;
    }
}
