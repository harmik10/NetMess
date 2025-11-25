/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UserInterface;


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class chatwindow extends javax.swing.JFrame {
    public static String username;
    public static String link;
    static PrintWriter out;
    public String msg;
    private Connection con=null;
    private Statement stmnt;
    private ResultSet rs;
   // static PrintWriter out;
   
    private static List<Socket> connectedClients = new ArrayList<>();
    /**
     * Creates new form chatwindow
     */
    public chatwindow(String rlink) {
        initComponents();
        link=rlink;
        txtLink.setText(link);
    }
     public static void makeServer(String username) throws IOException
    {
        
         ServerSocket serverSocket = new ServerSocket(4444);
        System.out.println("Server is running...");

        // Start a new thread to handle incoming connections
       
        Thread connectionThread = new Thread(() -> {
            try {
                while (true) {
                    Socket clientSocket = serverSocket.accept(); // Wait for a new connection
                    System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());

                    connectedClients.add(clientSocket); // Add the client to the list of connected clients

                    // Start a new thread to handle messages from the client
                    Thread receiveThread = new Thread(() -> {
                        try {
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                            while (true) {
                                String message = in.readLine(); // Read message from client
                                if (message == null) {
                                    break; // End the thread if the client disconnects
                                }

                                //chatArea.append(message+"\n");

                                // Send the message to all connected clients (except the sender)
                                for (Socket socket : connectedClients) {
                                    //if (socket != clientSocket) {
                                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                                        out.println(message);  
                                }
                            }

                            // Clean up resources when the client disconnects
                            in.close();
                            clientSocket.close();
                            connectedClients.remove(clientSocket);
                            System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    // Start the receive thread for the new client
                    receiveThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        connectionThread.start(); // Start the connection thread
//
//      
        // Read messages from the console and send them to all connected clients
        chatwindow.makeClient(username);
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String message = consoleIn.readLine(); // Read message from console
            if (message == null) {
                break; // End the loop if the server is shut down
            }

            // Send the message to all connected clients
            for (Socket socket : connectedClients) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(username+" : "+ message);
                
            }
             //chatArea.append(username+" : "+message+"\n");
        }

        // Clean up resources when the server is shut down
        consoleIn.close();
        serverSocket.close();
        System.out.println("Server shut down.");
        
    }
        
    public static void makeClient(String uname) throws IOException
    {
      
        username=uname;
        Socket socket = new Socket("localhost", 4444); // Connect to server on port 4444
        
        // Start a new thread to receive messages from the server
        Thread receiveThread = new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (true) {
                    String message = in.readLine(); // Read message from server
                    if (message == null) {
                        break; // End the thread if the server disconnects
                    }

                    chatArea.append(message+"\n");
                }

                // Clean up resources when the server disconnects
                in.close();
                socket.close();
                System.out.println("Disconnected from server.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Start the receive thread and send messages to the server
        receiveThread.start();
        out = new PrintWriter(socket.getOutputStream(), true);

        // Read messages from the console and send them to the server
//       
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String message = consoleIn.readLine();       ; // Read message from console
            if (message == null || message.equals("quit")) {
                break; // End the loop if the user types "quit" or disconnects
            }

            out.println(username+" : "+message); // Send message to server
            
        }

        // Clean up resources when the user types "quit" or disconnects
       // consoleIn.close();
        out.close();
        socket.close();
        System.out.println("Disconnected from server.");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtLink = new javax.swing.JTextField();
        btnCopy = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        lblmessage = new javax.swing.JLabel();
        txtMsg = new javax.swing.JTextField();
        btnShare = new javax.swing.JButton();
        menubar = new javax.swing.JMenuBar();
        menuUser = new javax.swing.JMenu();
        menuitemUprofile = new javax.swing.JMenuItem();
        menuitemViewUser = new javax.swing.JMenuItem();
        menuRoom = new javax.swing.JMenu();
        menuitemConfig = new javax.swing.JMenuItem();
        menuitemHistory = new javax.swing.JMenuItem();
        menuitemExit = new javax.swing.JMenuItem();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secure Net Messanger");

        txtLink.setEditable(false);
        txtLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtLink.setForeground(new java.awt.Color(0, 51, 204));
        txtLink.setText("Link to Join");

        btnCopy.setBackground(new java.awt.Color(0, 204, 153));
        btnCopy.setForeground(new java.awt.Color(255, 255, 255));
        btnCopy.setText("Copy Link");
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });

        chatArea.setEditable(false);
        chatArea.setColumns(20);
        chatArea.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        lblmessage.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblmessage.setText("Send your message");

        txtMsg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMsgActionPerformed(evt);
            }
        });
        txtMsg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMsgKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMsgKeyTyped(evt);
            }
        });

        btnShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/email.jpg"))); // NOI18N
        btnShare.setToolTipText("Share link");
        btnShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShareActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtLink, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnShare, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblmessage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMsg)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtLink, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnCopy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnShare, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblmessage)
                    .addComponent(txtMsg)))
        );

        menuUser.setText("User Tool");

        menuitemUprofile.setText("user profile");
        menuitemUprofile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemUprofileActionPerformed(evt);
            }
        });
        menuUser.add(menuitemUprofile);

        menuitemViewUser.setText("view user");
        menuitemViewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemViewUserActionPerformed(evt);
            }
        });
        menuUser.add(menuitemViewUser);

        menubar.add(menuUser);

        menuRoom.setText("Room Control");

        menuitemConfig.setText("configure room");
        menuitemConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemConfigActionPerformed(evt);
            }
        });
        menuRoom.add(menuitemConfig);

        menuitemHistory.setText("room history");
        menuitemHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemHistoryActionPerformed(evt);
            }
        });
        menuRoom.add(menuitemHistory);

        menuitemExit.setText("exit");
        menuitemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemExitActionPerformed(evt);
            }
        });
        menuRoom.add(menuitemExit);

        menubar.add(menuRoom);

        setJMenuBar(menubar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuitemUprofileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemUprofileActionPerformed
        // TODO add your handling code here:
        new userprofile().setVisible(true);
    }//GEN-LAST:event_menuitemUprofileActionPerformed

    private void menuitemConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemConfigActionPerformed
        // TODO add your handling code here:
        roomconfig r =new roomconfig();
        r.setVisible(true);
    }//GEN-LAST:event_menuitemConfigActionPerformed

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        // TODO add your handling code here:
        txtLink.selectAll();
        txtLink.copy();
    }//GEN-LAST:event_btnCopyActionPerformed

    private void txtMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMsgActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtMsgActionPerformed

    private void txtMsgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMsgKeyReleased
        // TODO add your handling code here:

           if (evt.getKeyCode()==10){
              msg = txtMsg.getText();
                out.println(username+" : "+msg);
                out.flush();
                txtMsg.setText("");

        }
    }//GEN-LAST:event_txtMsgKeyReleased

    private void txtMsgKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMsgKeyTyped
        // TODO add your handling code here

    }//GEN-LAST:event_txtMsgKeyTyped

    private void menuitemViewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemViewUserActionPerformed
        // TODO add your handling code here:
        new viewuser().setVisible(true);
        
    }//GEN-LAST:event_menuitemViewUserActionPerformed

    private void menuitemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemExitActionPerformed
        // TODO add your handling code here:
       String roomID;
       Time create_time;
        try
        {
            
            con=dbConnection.con();
            stmnt=con.createStatement();
            String qrySel="select room_id from chat_room where room_link='"+chatwindow.txtLink.getText()+"';";
            rs=stmnt.executeQuery(qrySel);
            rs.next();
            roomID=rs.getString("room_id");
            qrySel="select create_time from session_details where room_id='"+roomID+"';";
            rs=stmnt.executeQuery(qrySel);
            rs.next();
            create_time=rs.getTime("create_time");
            String qryUpt="update session_details set end_time=DATE_FORMAT(CURRENT_TIMESTAMP, '%H:%i:%s'),total_duration=TIMEDIFF(DATE_FORMAT(CURRENT_TIMESTAMP, '%H:%i:%s'),'"+create_time+"') where room_id='"+roomID+"';";
            stmnt.executeUpdate(qryUpt);
            dispose();
            System.exit(0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_menuitemExitActionPerformed

    private void menuitemHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemHistoryActionPerformed
        // TODO add your handling code here:
        new roomhistory().setVisible(true);
    }//GEN-LAST:event_menuitemHistoryActionPerformed

    private void btnShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShareActionPerformed
        // TODO add your handling code here:
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://www.gmail.com"));
        } catch (URISyntaxException ex) {
            Logger.getLogger(chatwindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(chatwindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnShareActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(chatwindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chatwindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chatwindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chatwindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String rlink = null;
                new chatwindow(rlink).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnShare;
    private static javax.swing.JTextArea chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblmessage;
    private javax.swing.JMenu menuRoom;
    private javax.swing.JMenu menuUser;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JMenuItem menuitemConfig;
    private javax.swing.JMenuItem menuitemExit;
    private javax.swing.JMenuItem menuitemHistory;
    private javax.swing.JMenuItem menuitemUprofile;
    private javax.swing.JMenuItem menuitemViewUser;
    public static javax.swing.JTextField txtLink;
    public static javax.swing.JTextField txtMsg;
    // End of variables declaration//GEN-END:variables
}
