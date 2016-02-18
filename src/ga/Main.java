package ga;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Main extends JFrame {

    static int motor = 0;
    static int steering = 0;
    static boolean forward = false;
    static boolean backwards = false;
    static boolean right = false;
    static boolean left = false;
    static String newLine = "\n";

    static ClientSocket client;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super("Ga client");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setFocusable(true);
        addControlButtons(panel);

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        left = true;
                        break;
                    case KeyEvent.VK_D:
                        right = true;
                        break;
                    case KeyEvent.VK_W:
                        forward = true;
                        break;
                    case KeyEvent.VK_S:
                        backwards = true;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        left = false;
                        break;
                    case KeyEvent.VK_D:
                        right = false;
                        break;
                    case KeyEvent.VK_W:
                        forward = false;
                        break;
                    case KeyEvent.VK_S:
                        backwards = false;
                        break;
                    default:
                        break;
                }
            }
        });

        add(panel);

        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        panel.requestFocus();

        //Loop för att kolla ifall någon tangent är nedtryckt för manövring av bil
        while (true) {
            if (steering > 0) {
                --steering;
            } else if (steering < 0) {
                ++steering;
            }
            if (motor > 0) {
                --motor;
            } else if (motor < 0) {
                ++motor;
            }
            if (forward == true && motor <= 125) {
                motor += 2;
            }
            if (backwards == true && motor >= -125) {
                motor -= 2;
            }
            if (left == true && steering >= -125) {
                steering -= 2;
            }
            if (right == true && steering <= 125) {
                steering += 2;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
            }
        }
    }

    JTextArea infoArea;

    private void addControlButtons(JPanel panel) {

        int arrowWidth = 70;
        int arrowHeight = 50;
        int connectWidth = 120;
        int connectHeight = 50;

        Button upArrow = new Button("FORWARD");
        upArrow.setFocusable(false);
        upArrow.setBounds(200, 280, arrowWidth, arrowHeight);
        upArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                motor += 2;
            }
        });
        panel.add(upArrow);

        Button leftArrow = new Button("LEFT");
        leftArrow.setFocusable(false);
        leftArrow.setBounds(120, 340, arrowWidth, arrowHeight);
        leftArrow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                steering -= 1;
            }
        });
        panel.add(leftArrow);

        Button downArrow = new Button("DOWN");
        downArrow.setFocusable(false);
        downArrow.setBounds(200, 340, arrowWidth, arrowHeight);
        downArrow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                motor -= 2;
            }
        });
        panel.add(downArrow);

        Button rightArrow = new Button("RIGHT");
        rightArrow.setFocusable(false);
        rightArrow.setBounds(280, 340, arrowWidth, arrowHeight);
        rightArrow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                steering += 1;
            }
        });
        panel.add(rightArrow);

        Button space = new Button("BRAKE");
        space.setFocusable(false);
        space.setBounds(120, 400, 230, 40);
        space.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                motor = 0;
            }
        });
        panel.add(space);

        Button leftBlinker = new Button("<=");
        leftBlinker.setFocusable(false);
        leftBlinker.setBounds(130, 280, 50, 50);
        panel.add(leftBlinker);

        Button rightBlinker = new Button("=>");
        rightBlinker.setFocusable(false);
        rightBlinker.setBounds(290, 280, 50, 50);
        panel.add(rightBlinker);

        Button connect = new Button("Connect");
        connect.setFocusable(false);
        connect.setBounds(20, 20, connectWidth, connectHeight);
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectUI.ConnectUI();
            }
        });
        panel.add(connect);

        Button disconnect = new Button("Disconnect");
        disconnect.setFocusable(false);
        disconnect.setBounds(160, 20, connectWidth, connectHeight);
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientSocket.disconnectClient();
            }
        });
        panel.add(disconnect);

        infoArea = new JTextArea();
        infoArea.setBounds(20, 90, 260, 140);
        infoArea.setEditable(false);
        infoArea.setFocusable(false);
        infoArea.setText("Connected to: " + ClientSocket.MachineName + " on port: " + ClientSocket.PortNumber + newLine + "Motor: " + motor + newLine + "Steering:" + steering + newLine);
        panel.add(infoArea);

    }

}
