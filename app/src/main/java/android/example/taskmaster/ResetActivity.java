package android.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.NewPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.regions.Regions;


public class ResetActivity extends AppCompatActivity {

    private Button buttonReset;
//
    private TextView newpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        findView();
        buttonReset.setOnClickListener(v -> {

            String pass = newpassword.getText().toString();

            AuthenticationHandler h = new AuthenticationHandler() {


                @Override
                public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {

                }

                @Override
                public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {

                }

                @Override
                public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

                }

                @Override
                public void authenticationChallenge(ChallengeContinuation continuation) {

                    if ("NEW_PASSWORD_REQUIRED".equals(continuation.getChallengeName())) {
                        NewPasswordContinuation newPasswordContinuation = (NewPasswordContinuation) continuation;
                        newPasswordContinuation.setPassword(pass);
                        continuation.continueTask();
                    }
                }

                @Override
                public void onFailure(Exception exception) {

                }

            };

            CognitoUserPool pool = new CognitoUserPool(getApplicationContext(), "poolId", "clientId", "clientSecret", Regions.US_WEST_2);
            pool.getUser("userId").getSession(h);
            });


    }
    public void findView(){
        buttonReset = findViewById(R.id.resetButton);

        newpassword = findViewById(R.id.Newpassword);




    }

}