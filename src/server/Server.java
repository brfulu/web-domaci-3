package server;

import com.google.gson.Gson;
import server.quote.QuoteResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int TCP_PORT = 8080;
    private static final int POOL_SIZE = 41;

    public static void main(String[] args) {
        try {
            ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
            ServerSocket ss = new ServerSocket(TCP_PORT);
            System.out.println("Server is running...");
            while (true) {
                Socket socket = ss.accept();
                pool.submit(new ServerThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
