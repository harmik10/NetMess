package UserInterface;


import java.sql.*;
import javax.swing.JOptionPane;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
public class dbConnection {
    public static Connection con()
    {
        Connection con=null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3307/secure_net_messenger","root","root");
           // JOptionPane.showMessageDialog(null,"connection successful");
        }
        catch(Exception e)
        {
          JOptionPane.showMessageDialog(null, e);
        }
        return con;
    }
}
