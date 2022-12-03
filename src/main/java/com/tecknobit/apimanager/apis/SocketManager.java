package com.tecknobit.apimanager.apis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketManager {

    private ServerSocket serverSocket;
    private int currentServerPort;
    private Runnable currentRoutine;
    private Socket socket;

    //SERVER
    public SocketManager(int serverPort) {
        currentServerPort = serverPort;
    }

    //CLIENT
    public SocketManager(String host, int serverPort) throws IOException {
        socket = new Socket(host, serverPort);
    }

    public SocketManager() {
        currentServerPort = -1;
    }

    public void startListener(Runnable routine) throws IOException {
        currentRoutine = routine;
        restartListener(currentServerPort, routine);
    }

    public void restartListener(int port) throws IOException {
        currentServerPort = port;
        restartListener(port, currentRoutine);
    }

    public void restartListener(int port, Runnable routine) throws IOException {
        if (serverSocket == null || currentServerPort != port) {
            serverSocket = new ServerSocket(port);
            currentServerPort = port;
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(routine);
        }
    }

    public void acceptRequest() throws IOException {
        if (((serverSocket != null) && (socket == null || socket.isClosed())))
            socket = serverSocket.accept();
    }

    public void writeContent(String message) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(message);
        if (serverSocket != null && serverSocket.isBound())
            socket.close();
    }

    public String readContent() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
    }

}
