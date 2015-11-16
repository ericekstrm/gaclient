package ga;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        InputMap im = panel.getInputMap();
        ActionMap am = panel.getActionMap();
        
        im.put(KeyStroke.getKeyStroke("W"), "pressed W");
        im.put(KeyStroke.getKeyStroke("released W"), "released W");
        am.put("pressed W", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forward = true;
            }
        });
        am.put("released W", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forward = false;
            }
        });
        
        im.put(KeyStroke.getKeyStroke("S"), "pressed S");
        im.put(KeyStroke.getKeyStroke("released S"), "released S");
        am.put("pressed S", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backwards = true;
            }
        });
        am.put("released S", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backwards = false;
            }
        });
        
        im.put(KeyStroke.getKeyStroke("A"), "pressed A");
        im.put(KeyStroke.getKeyStroke("released A"), "released A");
        am.put("pressed A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                left = true;
            }
        });
        am.put("released A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                left = false;
            }
        });
        
        im.put(KeyStroke.getKeyStroke("D"), "pressed D");
        im.put(KeyStroke.getKeyStroke("released D"), "released D");
        am.put("pressed D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                right = true;
            }
        });
        am.put("released D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                right = false;
            }
        });
        
        im.put(KeyStroke.getKeyStroke("SPACE"), "pressed SPACE");
        am.put("pressed SPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                motor = 0;
            }
        });

        add(panel);

        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        //Loop för att kolla ifall någon tangent är nedtryckt för manövring av bil
        while (true) {
            if (steering > 0) {
                --steering;
            }else if (steering < 0) {
                ++steering;
            }
            if (motor > 0) {
                --motor;
            }else if (motor < 0) {
                ++motor;
            }
            if (forward == true && motor <= 125) {
                motor += 2;
            } else if (backwards == true && motor >= -125) {
                motor -= 2;
            } else if (left == true && steering >= -125) {
                steering -= 2;
            } else if (right == true && steering <= 125) {
                steering += 2;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {                
            }
        }
    }

    JTextArea infoArea;

    private void addControlButtons(JPanel p) {
        
        int arrowWidth = 70;
        int arrowHeight = 50;
        int connectWidth = 120;
        int connectHeight = 50;

        Button upArrow = new Button("FORWARD");
        upArrow.setBounds(200, 280, arrowWidth, arrowHeight);
        upArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                motor += 2;
            }
        });
        p.add(upArrow);

        Button leftArrow = new Button("LEFT");
        leftArrow.setBounds(120, 340, arrowWidth, arrowHeight);
        leftArrow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                steering -= 1;
            }
        });
        p.add(leftArrow);

        Button downArrow = new Button("DOWN");
        downArrow.setBounds(200, 340, arrowWidth, arrowHeight);
        downArrow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                motor -= 2;
            }
        });
        p.add(downArrow);

        Button rightArrow = new Button("RIGHT");
        rightArrow.setBounds(280, 340, arrowWidth, arrowHeight);
        rightArrow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                steering += 1;
            }
        });
        p.add(rightArrow);

        Button space = new Button("BRAKE");
        space.setBounds(120, 400, 230, 40);
        space.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                motor = 0;
            }
        });
        p.add(space);

        Button leftBlinker = new Button("<=");
        leftBlinker.setBounds(130, 280, 50, 50);
        p.add(leftBlinker);

        Button rightBlinker = new Button("=>");
        rightBlinker.setBounds(290, 280, 50, 50);
        p.add(rightBlinker);

        Button connect = new Button("Connect");
        connect.setBounds(20, 20, connectWidth, connectHeight);
        connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectUI.ConnectUI();
            }
        });
        p.add(connect);

        Button disconnect = new Button("Disconnect");
        disconnect.setBounds(160, 20, connectWidth, connectHeight);
        disconnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ClientSocket.disconnectClient();
            }
        });
        p.add(disconnect);

        infoArea = new JTextArea();
        infoArea.setBounds(20, 90, 260, 140);
        infoArea.setEditable(false);
        infoArea.setText("Connected to: " + ClientSocket.MachineName + " on port: " + ClientSocket.PortNumber + newLine + "Motor: " + motor + newLine + "Steering:" + steering + newLine);
        p.add(infoArea);

    }

}
