package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

public class MainActivity extends AppCompatActivity {



    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.textView7);
        Button clickButton=findViewById(R.id.button);
        Button clickButton2=findViewById(R.id.button2);


clickButton.setOnClickListener(view -> {
    Intent startSecondActivityIntent = new Intent(this, AddTask.class);
    startActivity(startSecondActivityIntent);

});

clickButton2.setOnClickListener(view -> {
    navigateToTaskModel();
});


    }



    @Override
    protected void onResume() {
        super.onResume();
        setAddress();
    }

    private void setAddress() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        textView.setText(sharedPreferences.getString(Settings.ADDRESS, "does not saved"));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Settings:
                Toast.makeText(this, "Copyright 2022", Toast.LENGTH_SHORT).show();
                navigateToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void navigateToSettings() {
        Intent weatherDetailsIntent = new Intent(this, Settings.class);
        startActivity(weatherDetailsIntent);
    }

    private void navigateToTaskDetail() {
        Intent settingsIntent = new Intent(this, TaskDetail.class);
        startActivity(settingsIntent);
    }
    private void navigateToTaskModel() {
        Intent settingsIntent = new Intent(this, TaskModel.class);
        startActivity(settingsIntent);
    }
}