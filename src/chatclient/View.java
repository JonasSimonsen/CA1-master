package chatclient;


import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class View extends javax.swing.JFrame implements Observer{
    
    private ChatClient CC;
    private int port = 9090;
    private String ip = "VMAHL.cloudapp.net";
    private static boolean btn1Clicked = false;
    private static boolean btn2Clicked = false;
    
    public View(String[] args) throws IOException {
        if (args.length == 2) {
             port = Integer.parseInt(args[0]);
             ip = args[1];
        }
        initComponents();
        
        DisconnectButton.setEnabled(false);
        MessageField.setEnabled(false);
        jTextArea1.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        UsernameField = new javax.swing.JTextField();
        ConnectButton = new javax.swing.JButton();
        DisconnectButton = new javax.swing.JButton();
        SendButton = new javax.swing.JButton();
        MessageField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        UsernameField.setFont(new java.awt.Font("CordiaUPC", 0, 24)); // NOI18N
        UsernameField.setText("Username...");
        UsernameField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsernameFieldMouseClicked(evt);
            }
        });

        ConnectButton.setFont(new java.awt.Font("CordiaUPC", 0, 18)); // NOI18N
        ConnectButton.setText("Connect");
        ConnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectButtonActionPerformed(evt);
            }
        });

        DisconnectButton.setFont(new java.awt.Font("CordiaUPC", 0, 18)); // NOI18N
        DisconnectButton.setText("Disconnect");
        DisconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisconnectButtonActionPerformed(evt);
            }
        });

        SendButton.setFont(new java.awt.Font("CordiaUPC", 0, 24)); // NOI18N
        SendButton.setText("Send");
        SendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendButtonActionPerformed(evt);
            }
        });

        MessageField.setFont(new java.awt.Font("CordiaUPC", 0, 24)); // NOI18N
        MessageField.setText("Message...");
        MessageField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MessageFieldMouseClicked(evt);
            }
        });
        MessageField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MessageFieldKeyPressed(evt);
            }
        });

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Connect" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MessageField)
                    .addComponent(UsernameField)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(SendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ConnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DisconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ConnectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DisconnectButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UsernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MessageField)
                    .addComponent(SendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DisconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisconnectButtonActionPerformed
        try {
            CC.stop();
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }//GEN-LAST:event_DisconnectButtonActionPerformed

    private void ConnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectButtonActionPerformed
            CC = new ChatClient(UsernameField.getText());
            CC.addObserver(this);
            try {
            CC.connect("VMAHL.cloudapp.net", 9090);         
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }    
    btn1Clicked = true;
    jList1.setVisible(true);
    jTextArea1.setVisible(true);
    if(btn1Clicked == true){
    ConnectButton.setEnabled(false);
    DisconnectButton.setEnabled(true);
    MessageField.setEnabled(true);
    }

    }//GEN-LAST:event_ConnectButtonActionPerformed

    private void SendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendButtonActionPerformed
        String input = MessageField.getText();
        String receivers ="";
        for (Object obj : jList1.getSelectedValuesList()){
            if(!obj.toString().equals(CC.getUserName())) receivers += obj.toString()+",";
        }
        if (receivers.endsWith(",")) {
            receivers = receivers.substring(0, receivers.length() - 1);
        }
        if (receivers.equals("")) CC.send(input);
        else CC.send(receivers, input);
        
        MessageField.setText("");
    }//GEN-LAST:event_SendButtonActionPerformed

    private void MessageFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MessageFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String input = MessageField.getText();
            String receivers = "";
            for (Object obj : jList1.getSelectedValuesList()) {
                if (!obj.toString().equals(CC.getUserName())) {
                    receivers += obj.toString() + ",";
                }
            }
            if (receivers.endsWith(",")) {
                receivers = receivers.substring(0, receivers.length() - 1);
            }
            if (receivers.equals("")) {
                CC.send(input);
            } else {
                CC.send(receivers, input);
            }

            MessageField.setText("");
        }
    }//GEN-LAST:event_MessageFieldKeyPressed

    private void MessageFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MessageFieldMouseClicked
        MessageField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MessageField.setText("");
            }
        });
    }//GEN-LAST:event_MessageFieldMouseClicked

    private void UsernameFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsernameFieldMouseClicked
        UsernameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UsernameField.setText("");
            }
        });
    }//GEN-LAST:event_UsernameFieldMouseClicked

    public static void main(final String args[]) {
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
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new View(args).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ConnectButton;
    private javax.swing.JButton DisconnectButton;
    private javax.swing.JTextField MessageField;
    private javax.swing.JButton SendButton;
    private javax.swing.JTextField UsernameField;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        String str = (String) arg;
        String[] strArr = str.split("#");
        if(strArr[0].equals("MSG")){
            jTextArea1.append(MessageField.getText() + "\n" + strArr[1] + ": " + strArr[2]);
            
        }
        if(strArr[0].equals("USERLIST")){
                    jList1.setListData(strArr[1].split(","));
        }
        
    
    }
}
