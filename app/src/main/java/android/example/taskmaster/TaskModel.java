package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.taskmaster.data.AppDatabase;
import android.example.taskmaster.data.Tasks;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class TaskModel extends AppCompatActivity {

    public static final String tasknames = "address";




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        List<Tasks> tasks= AppDatabase.getInstance(getApplicationContext()).taskDao().getAll();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_model);

        RecyclerView RecycleTask = findViewById(R.id.Recycle_task);
        RecycleModels recycleModels = new RecycleModels(tasks, position -> {
            Toast.makeText(
                    TaskModel.this,
                    "The item clicked => " + tasks.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            String taskname =  tasks.get(position).getTitle().toString();
            String taskState =  tasks.get(position).getState().toString();
            String taskBody =  tasks.get(position).getBody().toString();


      Intent intent=new Intent(getApplicationContext(),TaskDetail.class);

      intent.putExtra("task",taskname);
      intent.putExtra("taskstate",taskState);
            intent.putExtra("taskBody",taskBody);
            startActivity(intent);




        });

        RecycleTask.setAdapter(recycleModels);
        RecycleTask.setHasFixedSize(true);
        RecycleTask.setLayoutManager(new LinearLayoutManager(this));


    }

    private void navigateToTaskDetail() {
        Intent settingsIntent = new Intent(this, TaskDetail.class);
        startActivity(settingsIntent);
    }

    private void navigateToSettings() {
        Intent weatherDetailsIntent = new Intent(this, Settings.class);
        startActivity(weatherDetailsIntent);
    }

    private void navigateToMain() {
        Intent settingsIntent = new Intent(this, MainActivity.class);
        startActivity(settingsIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.taskdetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        switch (item.getItemId()) {
            case R.id.action_mainpage:
                navigateToMain();
                return true;
            case R.id.action_Settings:
                navigateToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}