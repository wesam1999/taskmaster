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
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.User;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
private Handler handler;
    private TextView textView;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView7);
        Button clickButton=findViewById(R.id.button);
        Button clickButton2=findViewById(R.id.button2);
        email = findViewById(R.id.textView10);
email.setText(getCurrentValue());
clickButton.setOnClickListener(view -> {
    Intent startSecondActivityIntent = new Intent(this, AddTask.class);
    startActivity(startSecondActivityIntent);

});

clickButton2.setOnClickListener(view -> {
    navigateToTaskModel();
});

//        Team team=Team.builder().name("team3").build();
//        Amplify.API.mutate(
//                ModelMutation.create(team),
//                response -> {
//                    Log.i("MyAmplifyApp", "Added Todo with id: " + response);
//
//
//                },
//                error -> Log.e("MyAmplifyApp", "Create failed", error)
//        );

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
            case R.id.logout:
                logout();
                return true;
            case R.id.reset:
                navigateToReset();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void navigateToSettings() {
        Intent weatherDetailsIntent = new Intent(this, Settings.class);
        startActivity(weatherDetailsIntent);
    }

    private void navigateToReset() {
        Intent settingsIntent = new Intent(this, ResetActivity.class);
        startActivity(settingsIntent);
    }
    private void navigateToTaskModel() {
        Intent settingsIntent = new Intent(this, TaskModel.class);
        startActivity(settingsIntent);
    }
    private void logout() {
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    startActivity(new Intent(MainActivity.this, login.class));
                    authSession("logout");
                    finish();
                },
                error -> Log.e(TAG, error.toString())
        );
    }
    private void authSession(String method) {
        Amplify.Auth.fetchAuthSession(
                result -> Log.i(TAG, "Auth Session => " + method + result.toString()),
                error -> Log.e(TAG, error.toString())
        );
    }
    String getCurrentValue(){
        AuthUser authUser=Amplify.Auth.getCurrentUser();
        Log.e("getCurrentUser", authUser.toString());
        Log.e("getCurrentUser", authUser.getUserId());
        Log.e("getCurrentUser", authUser.getUsername());
        return authUser.getUsername();
    }
}