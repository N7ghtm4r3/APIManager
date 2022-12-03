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

    private final boolean serverUse;
    private final boolean allowMultipleListeners;
    private ServerSocket serverSocket;
    private int currentServerPort;
    private ExecutorService executor;
    private Runnable currentRoutine;
    private Socket socket;
    private String currentHost;
    private PrintWriter printWriter;

    //CLIENT
    public SocketManager(String host, int serverPort) {
        serverUse = false;
        currentHost = host;
        currentServerPort = serverPort;
        allowMultipleListeners = false;
    }

    //SERVER
    public SocketManager(boolean allowMultipleListeners) {
        serverUse = true;
        this.allowMultipleListeners = allowMultipleListeners;
        if (allowMultipleListeners)
            executor = Executors.newCachedThreadPool();
    }

    public void restartListener(Runnable routine) throws IOException {
        startListener(currentServerPort, routine);
    }

    public void restartListener(int port) throws IOException {
        startListener(port, currentRoutine);
    }

    public void startListener(int port, Runnable routine) throws IOException {
        if (serverUse) {
            if (serverSocket == null || currentServerPort != port) {
                serverSocket = new ServerSocket(port);
                currentServerPort = port;
            }
            if (!allowMultipleListeners) {
                executor = Executors.newSingleThreadExecutor();
            }
            currentRoutine = routine;
            executor.execute(routine);
        }
    }

    public Socket acceptRequest() throws IOException {
        if (serverUse)
            return socket = serverSocket.accept();
        return null;
    }

    public synchronized <T> void writeContent(T content) throws IOException {
        if (!serverUse)
            socket = new Socket(currentHost, currentServerPort);
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(content.toString());
    }

    public synchronized <T> void writeContent(Socket targetSocket, T content) throws IOException {
        if (serverUse) {
            printWriter = new PrintWriter(targetSocket.getOutputStream(), true);
            printWriter.println(content.toString());
        }
    }

    public synchronized String readContent() throws IOException {
        return readContent(socket);
    }

    public synchronized String readContent(Socket targetSocket) throws IOException {
        String content = new BufferedReader(new InputStreamReader(targetSocket.getInputStream())).readLine();
        if (!serverUse)
            targetSocket.close();
        return content;
    }

}
