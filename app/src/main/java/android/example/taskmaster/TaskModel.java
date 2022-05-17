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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskModel extends AppCompatActivity {

    public static final String tasknames = "address";
    private static final String TAG = TaskModel.class.getName().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        List<Tasks> tasks= AppDatabase.getInstance(getApplicationContext()).taskDao().getAll();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_model);

        RecyclerView RecycleTask = findViewById(R.id.Recycle_task);
        Amplify.DataStore.query(Task.class,
                task->{

                    ArrayList<Task> taskArrayList=new ArrayList<>();
            while (task.hasNext()){

                Task task3 = task.next();

                taskArrayList.add(task3);

            }


                RecycleModels recycleModels = new RecycleModels(taskArrayList, position -> {
                    Toast.makeText(
                            TaskModel.this,
                            "The item clicked => " + tasks.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                    String titleQurey= taskArrayList.get(position).getTitle();
                    String StatusQuery=taskArrayList.get(position).getStatus();
                    String BODYQuery=taskArrayList.get(position).getBody();


                    Intent intent=new Intent(getApplicationContext(),TaskDetail.class);

                    intent.putExtra("task",titleQurey);
                    intent.putExtra("taskstate",StatusQuery);
                    intent.putExtra("taskBody",BODYQuery);
                    startActivity(intent);




                });

                RecycleTask.setAdapter(recycleModels);
                RecycleTask.setHasFixedSize(true);
                RecycleTask.setLayoutManager(new LinearLayoutManager(this));


                }
                ,falied->{
            Log.e(TAG,falied.toString());
        });



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