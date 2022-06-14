package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.predictions.models.LanguageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;

public class TaskDetail extends AppCompatActivity {

    private final MediaPlayer mp = new MediaPlayer();
    private TextView textView2;
    private TextView textView;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        textView = findViewById(R.id.textView5);
        textView2 = findViewById(R.id.textView8);
        textView3 = findViewById(R.id.textView6);
        Button speech=findViewById(R.id.button8);
        Button Translating=findViewById(R.id.button9);
        ImageView ImageView=findViewById(R.id.imageView3);

        ActionBar actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);
speech.setOnClickListener(view ->Text_to_speech() );
        Translating.setOnClickListener(view ->Text_to_Translating() );


        Intent passedIntent = getIntent();
        String tasks = passedIntent.getStringExtra("task");
        String taskState = passedIntent.getStringExtra("taskstate");
        String taskBody = passedIntent.getStringExtra("taskBody");

        String taskuri = passedIntent.getStringExtra("taskuri");
        Uri ImageUri=Uri.parse(taskuri);

        textView.setText(tasks);
        textView2.setText(taskState);
        textView3.setText(taskBody);
if (taskuri!=null) {
    ImageView.setImageURI(ImageUri);
}
}

    private void Text_to_Translating() {

        Amplify.Predictions.translateText(
                textView3.getText().toString() ,
                LanguageType.ENGLISH,
                LanguageType.ITALIAN,
                result -> Log.i("MyAmplifyApp", result.getTranslatedText()),
                error -> Log.e("MyAmplifyApp", "Translation failed", error)
        );



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

    private void playAudio(InputStream data) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1_024];
            int bytesRead;
            while ((bytesRead = data.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();
        } catch (IOException error) {
            Log.e("MyAmplifyApp", "Error writing audio file", error);
        }

    }

public void Text_to_speech(){
    Amplify.Predictions.convertTextToSpeech(
            textView.getText().toString(),
            result -> playAudio(result.getAudioData()),
            error -> Log.e("MyAmplifyApp", "Conversion failed", error)
    );

    Amplify.Predictions.convertTextToSpeech(
            textView2.getText().toString(),
            result -> playAudio(result.getAudioData()),
            error -> Log.e("MyAmplifyApp", "Conversion failed", error)
    );
    Amplify.Predictions.convertTextToSpeech(
            textView3.getText().toString(),
            result -> playAudio(result.getAudioData()),
            error -> Log.e("MyAmplifyApp", "Conversion failed", error)
    );

}


}