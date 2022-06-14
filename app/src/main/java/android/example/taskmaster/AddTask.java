package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
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
import com.amplifyframework.datastore.generated.model.Team;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AddTask extends AppCompatActivity {
    public static final String TASK_ID = "taskId";
    public static final int REQUEST_CODE = 123;
    private static final int REQUEST_CODE_SEND = 4567;
    private Handler handler;
    private Handler handler1;
    private static final String TAG = AddTask.class.getName();
    private EditText titleFild;
    private EditText bodyFild;
    private Spinner spinner3;
    private ArrayList<Team> arrayListspinner3;
    private TextView text;
    private Button button;
    private Spinner spinner;
    private ArrayList<String> spinnerlist;
    private Button uplode_file;
    private Task newTask;
    private Uri currentUri=null;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        spinner3 = findViewById(R.id.spinner3);
        arrayListspinner3 = new ArrayList<>();
        text = findViewById(R.id.textView3);
        button = findViewById(R.id.button3);
        spinner = findViewById(R.id.spinner);
        titleFild = findViewById(R.id.editTextTextPersonName);
        bodyFild = findViewById(R.id.editTextTextPersonName2);
        uplode_file = findViewById(R.id.buttonDownload);
        uplode_file.setOnClickListener(view->pictureUpload());


        handle();


        observe();


        add_spinner();


        add_Spinner_API_Query();


        button.setOnClickListener(view -> {

            String title = titleFild.getText().toString();
            String body = bodyFild.getText().toString();

            String state = spinner.getSelectedItem().toString();


            for (int i = 0; i < arrayListspinner3.size(); i++) {

                if (arrayListspinner3.get(i).getName() == spinner3.getSelectedItem().toString()) {

                    newTask = Task.builder().
                            title(title).
                            status(state).
                            body(body)
                            .teamListtasksId(arrayListspinner3.get(i).getId())
                            .uriImage(currentUri.toString())
                            .build();
                    arrayListspinner3.get(i).getListtasks().add(newTask);

                }

            }
            Amplify.API.mutate(
                            ModelMutation.create(newTask),
                            response -> {
                                Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getTitle());
                                handler.sendEmptyMessage(1);


                            },
                            error -> Log.e("MyAmplifyApp", "Create failed", error)
                    );


        });
    }

    public void add_Spinner_API_Query() {
        Amplify.API.query(
                ModelQuery.list(Team.class),
                teamsName -> {
                    for (Team note : teamsName.getData()) {
                        arrayListspinner3.add(note);
                    }

                    handler1.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, error.toString())
        );


    }

    public void observe() {

        Amplify.DataStore.observe(Task.class,
                started -> Log.i(TAG, "Observation began."),
                change -> {

                    Bundle bundle = new Bundle();
                    bundle.putString(TASK_ID, change.item().toString());

                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);


                },
                failure -> Log.e(TAG, "Observation failed.", failure),
                () -> Log.i(TAG, "Observation complete.")
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void handle() {

        handler = new Handler(
                Looper.getMainLooper(), msg -> {
            text.setText("submitted!");
            return true;

        }
        );
        handler1 = new Handler(
                Looper.getMainLooper(), msg -> {
            spinnerlist = (ArrayList<String>) arrayListspinner3.stream().map(index -> {
                return index.getName();
            }).collect(Collectors.toList());

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerlist);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner3.setAdapter(arrayAdapter);

            return true;

        }
        );
    }

    public void add_spinner() {

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
                Toast.makeText(parent.getContext(), "Selected teams : " + tutorialsName, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
//            case android.R.id.home:
//                this.finish();
//                return true;
            case R.id.action_mainpage:
                navigateToMain();
                return true;
            case R.id.action_Settings:
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

    private void navigateToMain() {
        Intent settingsIntent = new Intent(this, MainActivity.class);
        startActivity(settingsIntent);
    }
    private void pictureUpload() {




//
//    Intent getIntent=getIntent();
//    String string =getIntent.getAction();
//    String getIntenttype=getIntent.getType();
//    if(string.equals(Intent.ACTION_SEND)){
//        if(getIntenttype.startsWith("image/")){
//            Uri imageUri=getIntent.getParcelableExtra(Intent.EXTRA_STREAM);
//            if(imageUri!=null){
//getIntent.setType("image/*");
//                startActivityForResult(getIntent, REQUEST_CODE_SEND);
//
//            }
//
//
//        }
//    }
//else if (string.equals(Intent.ACTION_PICK)){
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, REQUEST_CODE);
//    }


        // Launches photo picker in single-select mode.
        // This means that the user can select one photo or video.
//        Intent intent = new Intent();
//      String string=intent.getAction();
//        if(string.equals(Intent.ACTION_SEND)){
//            intent.setAction(Intent.ACTION_SEND);
//            intent.setType("image/*");
//
//            startActivityForResult(intent, REQUEST_CODE);
//        }else if (string.equals(Intent.ACTION_PICK) ){
//            intent.setAction(Intent.ACTION_PICK);
//            intent.setType("image/*");
//
//            startActivityForResult(intent, REQUEST_CODE);
//
//
//            pictureDownload();
//        }
//


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);



        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            Log.e(TAG, "onActivityResult: Error getting image from device");
            return;
        }

        switch(requestCode) {
            case REQUEST_CODE:
                // Get photo picker response for single select.
                currentUri = data.getData();

                // Do stuff with the photo/video URI.
                Log.i(TAG, "onActivityResult: the uri is => " + currentUri);

                try {
                    Bitmap bitmap = getBitmapFromUri(currentUri);

                    File file = new File(getApplicationContext().getFilesDir(), "image1.jpg");
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();

                    // upload to s3
                    // uploads the file
                    Amplify.Storage.uploadFile(
                            "image.jpg",
                            file,
                            result -> Log.i(TAG, "Successfully uploaded: " + result.getKey()),
                            storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;

        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }
    private void pictureDownload() {
        Amplify.Storage.downloadFile(
                "image.jpg",
                new File(getApplicationContext().getFilesDir() + "/download.jpg"),
                result -> {
                    Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
                    Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());
                },
                error -> Log.e(TAG,  "Download Failure", error)
        );
    }
}