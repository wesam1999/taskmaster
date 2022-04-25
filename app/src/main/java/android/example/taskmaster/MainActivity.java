package android.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button clickButton=findViewById(R.id.button);
        Button clickButton2=findViewById(R.id.button2);

clickButton.setOnClickListener(view -> {
    Intent startSecondActivityIntent = new Intent(this, AddTask.class);
    startActivity(startSecondActivityIntent);

});

clickButton2.setOnClickListener(view -> {
    Intent startSecondActivityIntent = new Intent(this, AllTask.class);
    startActivity(startSecondActivityIntent);
});


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}