package ga;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ConnectUI {

    public static JFrame frame;
    public static JTextField textFieldIP;
    public static JTextField textFieldPort;

    public static void ConnectUI() {

        int textFieldWidth = 120;
        int textFieldHeight = 20;

        frame = new JFrame();

        JLabel label1 = new JLabel("IP:");
        label1.setBounds(10, 20, 20, 10);

        textFieldIP = new JTextField(8);
        textFieldIP.setBounds(30, 16, textFieldWidth, textFieldHeight);

        JLabel label2 = new JLabel("Port:");
        label2.setBounds(160, 20, 60, 10);

        textFieldPort = new JTextField(8);
        textFieldPort.setBounds(190, 16, textFieldWidth, textFieldHeight);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(320, 16, 80, 20);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                String ip = "localhost";
                int port = 25565;
                if (ConnectUI.textFieldIP.getText().length() != 0) {
                    ip = ConnectUI.textFieldIP.getText();
                }
                if (ConnectUI.textFieldPort.getText().length() != 0) {
                    port = Integer.parseInt(ConnectUI.textFieldPort.getText());
                }
                ClientSocket.MachineName = ip;
                ClientSocket.PortNumber = port;
                Main.client = new ClientSocket();
                ConnectUI.frame.dispose();
            }
        });

        frame.setSize(420, 80);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Settings");
        frame.add(label1);
        frame.add(textFieldIP);
        frame.add(label2);
        frame.add(textFieldPort);
        frame.add(confirmButton);
        frame.setVisible(true);
    }

}
