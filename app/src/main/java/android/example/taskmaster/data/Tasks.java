package android.example.taskmaster.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tasks {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "state")
    public String state;
    @ColumnInfo(name = "body")
    public String body;


    public Tasks(String title, String body, String state ) {
        this.title = title;
        this.state = state;
        this.body = body;
    }

    public Tasks(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Tasks() {

    }

    public int getId() {
        return id;
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
