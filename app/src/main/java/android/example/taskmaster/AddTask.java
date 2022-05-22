package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;

public class AddTask extends AppCompatActivity {
    public static final String TASK_ID = "taskId";
    private Handler handler;
    private static final String TAG = AddTask.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ActionBar actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);




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
        Spinner spinner3 = findViewById(R.id.spinner3);
        ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList.add("team1");
        arrayList.add("team2");
        arrayList.add("team3");
        arrayList.add("team4");
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
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

            String state3 = spinner3.getSelectedItem().toString();
Task newTask=Task.builder().title(title).body(body).status(state).build();
//Amplify.DataStore.save(newTask, success -> Log.i(TAG, "Saved item: " + success.item().getTitle()),
//        error -> Log.e(TAG, "Could not save item to DataStore", error));
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





//            Tasks task = new Tasks(title,body,state);
//            AppDatabase.getInstance(getApplicationContext()).taskDao().insertStudent(task);



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