package server;

import com.google.gson.Gson;
import server.quote.Quote;
import server.quote.QuoteResponse;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson;

    public ServerThread(Socket sock) {
        try {
            this.gson = new Gson();
            this.client = sock;
            this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String starterLine = in.readLine();
            HttpRequest request = new HttpRequest(starterLine);

            String header = "";
            while (true) {
                String line = in.readLine();
                header += line;
                if (line.trim().equals("")) break;
                header += "\r\n";
            }
            request.setHeader(header);

            if (request.getMethod().equals("GET")) {
                if (request.getPath().equals("/qoute")) {
                    out.println(getQuoteResponse());
                } else if (request.getPath().equals("/favicon.ico")) {
                    out.println(notFound());
                } else {
                    out.println(redirect("/qoute"));
                }
            } else {
                out.println(notSupportedResponse());
            }

            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getQuoteResponse() {
        String json = Http.get("quotes.rest", "/qod");
        QuoteResponse quouteResponse = gson.fromJson(json, QuoteResponse.class);
        Quote quote = quouteResponse.getContents().getQuotes()[0];

        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        response += "<html><head><title>Quote of the day</title></head>\n";
        response += "<body><h1>" + quote.getQuote() + "</h1>\n";
        response += "<body><h3>" + quote.getAuthor() + "</h3>\n";
        response += "</body></html>";

        return response;
    }

    private String notSupportedResponse() {
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        response += "<html><head><title>Not supported</title></head>\n";
        response += "<body><h1> Not supported </h1>\n";
        response += "</body></html>";
        return response;
    }

    private String redirect(String path) {
        return "HTTP/1.1 301 Moved Permanently\r\nLocation: " + path + "\r\n\r\n";
    }

    private String notFound() {
        return "HTTP/1.1 404 Not found\r\n\r\n";
    }
}
