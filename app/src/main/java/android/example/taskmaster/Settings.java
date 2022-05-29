package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.team;
import com.amplifyframework.datastore.generated.model.user;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    public static final String ADDRESS = "address";
    public static final String USER_TEAM = "user team";
    private static final String TAG = Settings.class.getName();

    public static String address;
    private Button button;
    private EditText editText;
    private Handler handler;
    private String userName;
    private user user1;
    private Spinner spinner;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);


        setContentView(R.layout.activity_settings);


        editText = findViewById(R.id.editTextTextPersonName3);
        button = findViewById(R.id.button4);
        spinner = findViewById(R.id.spinner2);
        handler = new Handler(Looper.getMainLooper(), msg -> {
            saveAddress();
            return true;
        });

        arrayList = new ArrayList<>();


        query();

        spinnerOnclick();

        button.setOnClickListener(view -> {

            String state = spinner.getSelectedItem().toString();
            Log.i("spinnerSettings", "onCreate: " + state);
            userName = editText.getText().toString();
            user1 = user.builder().userName(userName).build();
            save();

        });
    }

    public void save() {

        Amplify.API.mutate(
                ModelMutation.create(user1),
                response -> {
                    Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId());
                    Bundle bundle = new Bundle();
                    bundle.putString(USER_TEAM, response.getData().getTeamUser().toString());

                    Message message = new Message();
                    bundle.putString("sadasd", "done");
                    message.setData(bundle);
                    handler.sendMessage(message);


                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
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

    public void query() {
        Amplify.API.query(
                ModelQuery.list(team.class),
                teamsName -> {
                    for (team note : teamsName.getData()) {
                        Log.i(TAG, "<==================================>");
                        Log.i(TAG, "The team name is => " + note.getName());
                        arrayList.add(note.getName());
                    }


                },
                error -> Log.e(TAG, error.toString())
        );

    }

    public void spinnerOnclick() {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             String iteam=parent.getItemAtPosition(position).toString();
                String tutorialsName = arrayList.get(position);
                Toast.makeText(parent.getContext(), "Selected the postion of the item: " + iteam, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}