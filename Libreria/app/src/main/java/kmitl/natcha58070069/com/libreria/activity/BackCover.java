package kmitl.natcha58070069.com.libreria.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import kmitl.natcha58070069.com.libreria.R;

public class BackCover extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_cover);

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        //callbacks into the FacebookSdk from onActivityResult() method
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userid = loginResult.getAccessToken().getUserId();

                //Intent to next page when Success to login FB
                Intent intent = new Intent(BackCover.this, MainActivity.class);
                startActivityForResult(intent, 999);
                finish();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }

        });
    }

    /* Fix Back button
    *Link to Main Page if login success
    *Exit to home screen if dont login again*/
    @Override
    public void onBackPressed() {
        if (AccessToken.getCurrentAccessToken() != null) {
            Intent intent = new Intent(BackCover.this, MainActivity.class);
            startActivityForResult(intent, 999);
            finish();
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
