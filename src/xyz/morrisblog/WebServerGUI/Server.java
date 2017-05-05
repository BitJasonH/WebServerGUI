package xyz.morrisblog.WebServerGUI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private int port;
    private String rootDir;

    private boolean isStart;

    /**
     * Constructor for initializing a Server
     *
     * @param port The port is which the server should listen to
     * @param ip   The ip is which the server runs on, default is 127.0.0.1
     */
    private Server(int port, byte[] ip) {
        this.port = port;

        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByAddress(ip));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Factory method to get a new server using specified ip address
     *
     * @param port listening port
     * @param ip   listening ip
     * @return a new server listening ip:port
     */
    public static Server getNewServerByIp(int port, byte[] ip) {
        return new Server(port, ip);
    }

    /**
     * Factory method to get a new server using 127.0.0.1
     *
     * @param port listening port
     * @return a new server listening 127.0.0.1:port
     */
    static Server getDefaultServer(int port) {
        return new Server(port, new byte[]{127, 0, 0, 1});
    }

    void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    void stopServer() {
        isStart = false;
        try {
            if (socket != null) {
                socket.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("No Server Running");
        }
    }

    /**
     * Make the server start to receive request and send response
     */
    @Override
    public void run() {
        isStart = true;
        while (isStart) {
            try {
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                System.out.println("Server Closed");
                continue;
            }

            RequestProcessor newRequestProcessor = RequestProcessor.getNewRequestContainer();
            newRequestProcessor.setRequestStream(inputStream);
            newRequestProcessor.setResponseStream(outputStream);
            newRequestProcessor.setWorkingDir(rootDir);
            Thread newThread = new Thread(newRequestProcessor);
            newThread.start();

            try {
                newThread.join();
                socket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
