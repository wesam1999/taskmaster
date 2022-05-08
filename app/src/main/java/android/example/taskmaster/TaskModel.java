package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.taskmaster.data.Models;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TaskModel extends AppCompatActivity {

    public static final String tasknames = "address";


    List<Models> model = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fitchdata();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_model);

        RecyclerView RecycleTask = findViewById(R.id.Recycle_task);
        RecycleModels recycleModels = new RecycleModels(model, position -> {
            Toast.makeText(
                    TaskModel.this,
                    "The item clicked => " + model.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            String taskname =  model.get(position).getTitle().toString();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();


            preferenceEditor.putString(tasknames, taskname);
            preferenceEditor.apply();
            Toast.makeText(this, "task name is Saved=>"+taskname, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), TaskDetail.class));
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
                Toast.makeText(this, "Copyright 2022", Toast.LENGTH_SHORT).show();
                navigateToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fitchdata() {
        model.add(new Models("task one", "in progress", "body "));
        model.add(new Models("task two ", "complete", "body"));
        model.add(new Models("task three", "assigned", "body"));
        model.add(new Models("task three", "new", "body"));

    }
    private void saveAddress() {




    }
}