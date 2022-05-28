package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.team;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
private Handler handler;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        try {
//            // Add this line, to include the Auth plugin.
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
//            Amplify.addPlugin(new AWSApiPlugin());
//            Amplify.addPlugin(new AWSDataStorePlugin());
//            Amplify.configure(getApplicationContext());
//
//            Log.i(TAG, "Initialized Amplify");
//        } catch (AmplifyException e) {
//            Log.e(TAG, "Could not initialize Amplify", e);
//        }

        team team1 = team.builder().name("TEAM1").build();

        Amplify.API.mutate(
                ModelMutation.create(team1),
                response -> {
                    Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId());
                    Bundle bundle=new Bundle();
                    bundle.putString("main class",response.getData().getName());

                    Message message=new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);


                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

        handler=new Handler(
                Looper.getMainLooper(), msg -> {

            Toast.makeText(this, "handle is work=>", Toast.LENGTH_SHORT).show();
            return true;

        }
        );


        Amplify.DataStore.observe(Task.class,
                started -> Log.i(TAG, "Observation began."),
                change -> {Log.i(TAG, change.item().toString());

                    Bundle bundle=new Bundle();
                    bundle.putString("main class",change.item().toString());

                    Message message=new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);


                },
                failure -> Log.e(TAG, "Observation failed.", failure),
                () -> Log.i(TAG, "Observation complete.")
        );
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