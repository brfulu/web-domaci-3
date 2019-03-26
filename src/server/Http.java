package server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Http {

    public static String get(String host, String path) {
        try {
            Socket socket = new Socket(InetAddress.getByName(host), 80);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: " + host);
            out.println("Accept: application/json");
            out.println();

            String header = readUntilBlankLine(in);
            in.readLine();
            String body = readUntilBlankLine(in);

            in.close();
            out.close();
            socket.close();

            return body;
        } catch (IOException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    private static String readUntilBlankLine(BufferedReader in) throws IOException {
        String result = "";
        while (true) {
            String line = in.readLine();
            if (line.trim().equals("") || line.trim().equals("0")) break;
            result += line;
            result += "\r\n";
        }
        return result;
    }
}
