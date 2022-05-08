package android.example.taskmaster.data;


public class Models  {
    private String title;
    private String state;
    private String body;

    public Models(String title, String state, String body) {
        this.title = title;
        this.state = state;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getState() {
        return state;
    }

    public String getBody() {
        return body;
    }

}
