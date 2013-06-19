package com.hex.pc.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.hex.network.GameLogger;
import com.hex.network.NetCommunication;
import com.hex.network.NetworkPlayer;

public class Server extends Thread implements NetCommunication {

    NetworkPlayer c;
    ServerSocket providerSocket;
    Socket connection;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    private boolean alive;
    private GameLogger logger;

    /**
     * make a new network thread
     * 
     * @param c
     *            client to talk to
     * 
     */
    public Server() {

        alive = true;
        this.logger = new GameLogger("server");
        try {
            this.providerSocket = new ServerSocket(6969);
            // 2. Wait for connection
            System.out.println("Waiting for connection");
            connection = providerSocket.accept();
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            // 3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            System.out.println("Connection successful");
        }
        catch(IOException e) {

            e.printStackTrace();
            System.exit(6969);
        }

    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Thread#run()
     */
    public void run() {
        System.out.println("running");
        try {

            // 4. The two parts communicate via the input and output streams
            do {
                try {
                    message = (String) in.readObject();
                    System.out.println("client>" + message);
                    logger.log("to me:  " + message);
                    c.receivedMessage(message);

                    if(message.equals("bye")) sendMessage("bye");
                }
                catch(ClassNotFoundException classnot) {
                    System.err.println("Data received in unknown format");
                }
            }
            while(alive && !message.equals("bye"));
        }
        catch(IOException ioException) {
            ioException.printStackTrace();
        }
        finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                providerSocket.close();
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
                logger.log("from me:  " + msg);

                System.out.println("server>" + msg);
            }
            catch(IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    @Override
    public void kill() {
        alive = false;

    }
}
