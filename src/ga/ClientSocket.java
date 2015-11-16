package ga;

import java.io.*;
import java.net.Socket;

public class ClientSocket implements Runnable {

    public static int PortNumber;
    public static String MachineName;
    int delay = 100;

    static Socket MySocket;
    static BufferedReader in;
    static PrintWriter out;

    public ClientSocket() {
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Connecting...");
            MySocket = new Socket(MachineName, PortNumber);
            System.out.println("Connected to IP: " + MachineName + " on port: " + PortNumber);
            out = new PrintWriter(MySocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(MySocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Can't create connection");
        }
        while (true) {
            String c = " ";
            
            int n = 0;
            if (Main.motor < 0) {
                n = -Main.motor;
            } else {
                n = Main.motor + 128;
            }
            String s = "" + (n) + c;
            
            int m = 0;
            if (Main.steering < 0) {
                m = -Main.steering;
            } else {
                m = Main.steering + 128;
            }
            String v = "" + (m) + c; 
            
            String t = "000";

            String completeString = s + v + t;

            System.out.println(completeString);
            out.println(completeString);
            
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("me dead");
            }
        }
    }

    public static void disconnectClient() {
        try {
            System.out.println("Disconnecting...");
            out.close();
            in.close();
            MySocket.close();
            System.out.println("Disconnected");
        } catch (Exception e) {
            System.out.println("Can't close");
        }
    }

}
