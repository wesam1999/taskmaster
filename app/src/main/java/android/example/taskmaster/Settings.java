package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Settings extends AppCompatActivity {

    public static final String ADDRESS = "address";
    public static final String USER_TEAM = "user team";
    private static final String TAG = Settings.class.getName();

    public static String address;
    private Button button;
    private EditText editText;
    private Handler handler;
    private String userName;
    private User user1;
    private Spinner spinner;
    private ArrayList<Team> arrayList=new ArrayList<Team>();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);





        editText = findViewById(R.id.editTextTextPersonName3);
        button = findViewById(R.id.button4);
        spinner = findViewById(R.id.spinner2);


        handler = new Handler(Looper.getMainLooper(), msg -> {
            saveAddress();
            return true;
        });

        handler=new Handler(
                Looper.getMainLooper(),msg -> {
            ArrayList<String> spinnerlist= (ArrayList<String>) arrayList.stream().map(index->{
                return index.getName();
            }).collect(Collectors.toList());


            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerlist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
            return true;

        }
        );

        query();


        button.setOnClickListener(view -> {

            userName = editText.getText().toString();





            for (int i = 0; i < arrayList.size(); i++) {

                if (arrayList.get(i).getName()==spinner.getSelectedItem().toString()){
                    user1 = User.builder().username(userName).teamListusersId(arrayList.get(i).getId()).build();
                    arrayList.get(i).getListusers().add(user1);
                    Intent sendStuff = new Intent(this, TaskModel.class);
                    sendStuff.putExtra(USER_TEAM, arrayList.get(i).getId());
                    startActivity(sendStuff);
                }

            }

            Amplify.API.mutate(
                    ModelMutation.create(user1),
                    response -> {
                        Log.i("MyAmplifyApp", "Added Todo with id: " + response);


                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );

//            save();

        });
    }

    public void save() {

        Amplify.API.mutate(
                ModelMutation.create(user1),
                response -> {
                    Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getUsername());


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
                ModelQuery.list(Team.class),
                teamsName -> {
                    for (Team team : teamsName.getData()) {

                        arrayList.add(team);

                    }

                    handler.sendEmptyMessage(1);

                },
                error -> Log.e(TAG, error.toString())
        );

    }

}