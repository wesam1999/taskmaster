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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;


import java.util.ArrayList;
import java.util.List;

public class TaskModel extends AppCompatActivity {
    public static final String TASK_Array = "taskId";
    private static final String TAG = TaskModel.class.getName();
private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Task> taskArrayList=new ArrayList<>();
        Intent intent1 = getIntent();
        String userTeam= intent1.getStringExtra(Settings.USER_TEAM);


        List<Tasks> tasks= AppDatabase.getInstance(getApplicationContext()).taskDao().getAll();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_model);
        RecyclerView RecycleTask = findViewById(R.id.Recycle_task);
        handler=new Handler(
                Looper.getMainLooper(), msg -> {
            RecycleModels recycleModels = new RecycleModels(taskArrayList, position -> {
                Toast.makeText(
                        TaskModel.this,
                        "The item clicked => " + taskArrayList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                String titleQurey= taskArrayList.get(position).getTitle();
                String StatusQuery=taskArrayList.get(position).getStatus();
                String BODYQuery=taskArrayList.get(position).getBody();
                String UriImage=taskArrayList.get(position).getUriImage();
Double locationLongitude=taskArrayList.get(position).getLocationLongitude();
                Double locationLatitude=taskArrayList.get(position).getLocationLatitude();

                Intent intent=new Intent(getApplicationContext(),TaskDetail.class);
                intent.putExtra("task",titleQurey);
                intent.putExtra("taskstate",StatusQuery);
                intent.putExtra("taskBody",BODYQuery);
                intent.putExtra("taskuri",UriImage);
                intent.putExtra("locationLongitude",locationLongitude.toString());
                intent.putExtra("locationLatitude",locationLatitude.toString());
                startActivity(intent);
            });
            RecycleTask.setAdapter(recycleModels);
            RecycleTask.setHasFixedSize(true);
            RecycleTask.setLayoutManager(new LinearLayoutManager(this));

            return true;

        }
        );
        Log.i(TAG, "onCreate: userTeam:"+userTeam);
        Amplify.API.query(
                ModelQuery.get(Team.class,userTeam),
                TeamsTask -> {
                    Log.i(TAG, "onCreate: new team:"+TeamsTask.getData());
                    for (Task task:
                            TeamsTask.getData().getListtasks() ) {


                        taskArrayList.add(task);

                    }
                    Bundle bundle=new Bundle();
                    bundle.putString(TASK_Array, "done");
                    Message message=new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                },
                error -> Log.e(TAG, error.toString())
        );



        Amplify.DataStore.observe(Task.class,
                started -> Log.i(TAG, "Observation began."),
                change -> {Log.i(TAG, change.item().toString());

                    Bundle bundle=new Bundle();
                    bundle.putString(TASK_Array,change.item().toString());

                    Message message=new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);


                },
                failure -> Log.e(TAG, "Observation failed.", failure),
                () -> Log.i(TAG, "Observation complete.")
        );



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