package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    public static final String ADDRESS = "address";
    public static String address;
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);


        setContentView(R.layout.activity_settings);


        editText = findViewById(R.id.editTextTextPersonName3);
        button = findViewById(R.id.button4);
        Spinner spinner = findViewById(R.id.spinner2);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("team1");
        arrayList.add("team2");
        arrayList.add("team3");
        arrayList.add("team4");
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

            saveAddress();
            String state = spinner.getSelectedItem().toString();

        });
    }

    private void saveAddress() {

        String username = editText.getText().toString();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();


        preferenceEditor.putString(ADDRESS, username);
        preferenceEditor.apply();
        Toast.makeText(this, "username is Saved", Toast.LENGTH_SHORT).show();

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}