package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;

public class TaskDetail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextView textView = findViewById(R.id.textView5);
        TextView textView2 = findViewById(R.id.textView8);
        TextView textView3 = findViewById(R.id.textView6);
        ActionBar actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);



        Intent passedIntent = getIntent();
        String tasks = passedIntent.getStringExtra("task");
        String taskState = passedIntent.getStringExtra("taskstate");
        String taskBody = passedIntent.getStringExtra("taskBody");

        textView.setText(tasks);
        textView2.setText(taskState);
        textView3.setText(taskBody);
    }


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
            case R.id.action_SettingsDetail:
                Toast.makeText(this, "Copyright 2022", Toast.LENGTH_SHORT).show();
                navigateToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.taskdetail, menu);
        return true;
    }

    private void navigateToSettings() {
        Intent weatherDetailsIntent = new Intent(this, Settings.class);
        startActivity(weatherDetailsIntent);
    }

    private void navigateToMain() {
        Intent settingsIntent = new Intent(this, MainActivity.class);
        startActivity(settingsIntent);
    }

}