package server;

public class HttpRequest {
    private String method;
    private String path;
    private String version;
    private String header;

    public HttpRequest(String starterLine) {
        String[] tokens = starterLine.split(" ");
        this.method = tokens[0];
        this.path = tokens[1];
        this.version = tokens[2];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}


