package server.quote;

public class Quote {
    private String quote;
    private String author;
    private int length;
    private String[] tags;
    private String category;
    private String title;
    private String date;
    private String id;

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }

    public int getLength() {
        return length;
    }

    public String[] getTags() {
        return tags;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }
}
