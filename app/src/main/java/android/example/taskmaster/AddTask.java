package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.team;

import java.util.ArrayList;

public class AddTask extends AppCompatActivity {
    public static final String TASK_ID = "taskId";
    private Handler handler;
    private Handler handler2;
    private static final String TAG = AddTask.class.getName();
    private EditText titleFild;
    private EditText bodyFild;
    private Spinner spinner3;
    private ArrayList<String> arrayListspinner3;
    private TextView text;
    private Button button;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ActionBar actionBar = getSupportActionBar();
        spinner3 = findViewById(R.id.spinner3);
        arrayListspinner3 = new ArrayList<>();

        actionBar.setDisplayHomeAsUpEnabled(true);
        text = findViewById(R.id.textView3);
        button = findViewById(R.id.button3);
        spinner = findViewById(R.id.spinner);
        handle();


        observe();



        add_spinner();



        add_Spinner_API_Query();


        button.setOnClickListener(view -> {
            titleFild = findViewById(R.id.editTextTextPersonName);
            String title = titleFild.getText().toString();

            bodyFild = findViewById(R.id.editTextTextPersonName2);
            String body = bodyFild.getText().toString();

String state = spinner.getSelectedItem().toString();

            String teamTask = spinner3.getSelectedItem().toString();
Task newTask=Task.builder().title(title).body(body).status(state).build();
            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    response -> {
                        Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId());
                        Bundle bundle=new Bundle();
                        bundle.putString(TASK_ID,response.getData().getId().toString());

                        Message message=new Message();
                        message.setData(bundle);
                        handler.sendMessage(message);


                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );


        });
    }
    public void add_Spinner_API_Query(){
        Amplify.API.query(
                ModelQuery.list(team.class),
                teamsName -> {
                    for (team note : teamsName.getData()) {
                        Bundle bundle=new Bundle();
                        bundle.putString(TASK_ID,note.getName());

                        Message message=new Message();
                        message.setData(bundle);
                        handler.sendMessage(message);




                    }

                },
                error -> Log.e(TAG, error.toString())
        );

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayListspinner3);
        spinner3.setAdapter(arrayAdapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nameTeam=arrayListspinner3.get(position);
                Toast.makeText(AddTask.this, "select team =>"+nameTeam, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
   public void   observe(){

       Amplify.DataStore.observe(Task.class,
               started -> Log.i(TAG, "Observation began."),
               change -> {Log.i(TAG, change.item().toString());

                   Bundle bundle=new Bundle();
                   bundle.putString(TASK_ID,change.item().toString());

                   Message message=new Message();
                   message.setData(bundle);
                   handler.sendMessage(message);


               },
               failure -> Log.e(TAG, "Observation failed.", failure),
               () -> Log.i(TAG, "Observation complete.")
       );
   }

    public void handle(){

        handler=new Handler(
                Looper.getMainLooper(),msg -> {

            String taskId = msg.getData().getString(TASK_ID);
            Intent intent = new Intent(this, TaskModel.class);
            intent.putExtra(TASK_ID, taskId);

            startActivity(intent);

            Toast.makeText(this, "the toast is works"+msg, Toast.LENGTH_SHORT).show();
            text.setText("submitted!");
            return true;

        }
        );
        handler2=new Handler(
                Looper.getMainLooper(),msg -> {

            arrayListspinner3.add(msg.getData().toString());
            return true;

        }
        );
    }

    public void add_spinner(){

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
                Toast.makeText(parent.getContext(), "Selected teams : " + tutorialsName,Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
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