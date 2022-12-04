package com.tecknobit.apimanager.apis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketManager {

    private final ConcurrentHashMap<Integer, ServerSocket> listeners;
    private final boolean serverUse;
    private final boolean allowMultipleListeners;
    private ServerSocket serverSocket;
    private int currentServerPort;
    private ExecutorService executor;
    private Runnable currentRoutine;
    private Socket socket;
    private String currentHost;

    //CLIENT
    public SocketManager(String host, int serverPort) {
        serverUse = false;
        currentHost = host;
        currentServerPort = serverPort;
        allowMultipleListeners = false;
        listeners = null;
    }

    //SERVER
    public SocketManager(boolean allowMultipleListeners) {
        serverUse = true;
        this.allowMultipleListeners = allowMultipleListeners;
        if (allowMultipleListeners) {
            executor = Executors.newCachedThreadPool();
            listeners = new ConcurrentHashMap<>();
        } else
            listeners = null;
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
                if (allowMultipleListeners) {
                    listeners.put(port, new ServerSocket(port));
                } else {
                    serverSocket = new ServerSocket(port);
                    currentServerPort = port;
                }
            }
            if (!allowMultipleListeners) {
                // TODO: 04/12/2022 WORK WHEN IS NOT ALLOWED MULTIPLE LISTENERS TO AVOID MORE COROUTINES AT THE SAME TIME
                if (executor != null)
                    executor.shutdown();
                executor = Executors.newSingleThreadExecutor();
            }
            currentRoutine = routine;
            executor.execute(routine);
        }
    }

    public Socket acceptRequest() throws IOException {
        return acceptRequest(currentServerPort);
    }

    public Socket acceptRequest(int port) throws IOException {
        if (serverUse) {
            if (allowMultipleListeners) {
                if (listeners.size() == 1)
                    port = listeners.keys().nextElement();
                serverSocket = listeners.get(port);
            }
            return socket = serverSocket.accept();
        }
        return null;
    }

    public synchronized <T> void writeContent(T content) throws IOException {
        writeContentTo(currentHost, currentServerPort, content);
    }

    public synchronized <T> void writeContentTo(int port, T content) throws IOException {
        writeContentTo(currentHost, port, content);
    }

    public synchronized <T> void writeContentTo(String host, T content) throws IOException {
        writeContentTo(host, currentServerPort, content);
    }

    public synchronized <T> void writeContentTo(String host, int port, T content) throws IOException {
        if (!serverUse)
            socket = new Socket(host, port);
        writeContentTo(socket, content);
    }

    public synchronized <T> void writeContentTo(Socket targetSocket, T content) throws IOException {
        PrintWriter printWriter = new PrintWriter(targetSocket.getOutputStream(), true);
        printWriter.println(content.toString());
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