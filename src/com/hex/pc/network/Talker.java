package com.hex.pc.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Talker implements NetComunicaiton {
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;

    PcClient master;
    private String IP;
    private boolean alive;

    public Talker(PcClient master, String IP) {
        this.master = master;
        this.IP = IP;

    }

    public void run() {
        this.alive = true;
        System.out.println("running");
        try {
            System.out.println("Connecting to " + IP);
            // 1. creating a socket to connect to the server
            requestSocket = new Socket(IP, 6969);
            System.out.println("Connected to localhost in port 6969");
            // 2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            // 3: Communicating with the server

            do {
                try {
                    message = (String) in.readObject();
                    master.messageDispach(message);

                }
                catch(ClassNotFoundException classNot) {
                    System.err.println("data received in unknown format");
                }
            }
            while(alive && !message.equals("bye"));
        }
        catch(UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException) {
            ioException.printStackTrace();

        }
        finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                requestSocket.close();
            }
            catch(IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void sendMessage(String msg) {
        synchronized(out) {
            try {
                out.writeObject(msg);
                out.flush();
                System.out.println("client>" + msg);
            }
            catch(IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hex.pc.network.NetComunicaiton#kill()
     */
    @Override
    public void kill() {
        alive = false;

    }
}
