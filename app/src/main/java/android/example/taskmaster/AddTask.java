package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.taskmaster.data.AppDatabase;
import android.example.taskmaster.data.Tasks;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ActionBar actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);



        TextView text = findViewById(R.id.textView3);
        Button button = findViewById(R.id.button3);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("new");
        arrayList.add("in progress");
        arrayList.add("complete");
        arrayList.add("assigned");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = arrayList.get(position);
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        button.setOnClickListener(view -> {
            EditText titleFild = findViewById(R.id.editTextTextPersonName);
            String title = titleFild.getText().toString();

            EditText bodyFild = findViewById(R.id.editTextTextPersonName2);
            String body = bodyFild.getText().toString();

String state = spinner.getSelectedItem().toString();
            Tasks task = new Tasks(title,body,state);
            AppDatabase.getInstance(getApplicationContext()).taskDao().insertStudent(task);

            text.setText("submitted!");

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.taskdetail, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_mainpage:
                navigateToMain();
                return true;
            case R.id.action_Settings:
                navigateToSettings();
                return true;
        }
        return super.onOptionsItemSelected(item);
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