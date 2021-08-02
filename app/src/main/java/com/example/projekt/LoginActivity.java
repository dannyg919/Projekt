package com.example.projekt;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.facebook.ParseFacebookUtils;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collection;


public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";



    private EditText etUsername;
    private EditText etPassword;
    private TextView tvSignup;
    private Button btnLogin;
    private LoginButton btnFBLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etUsername = findViewById(R.id.etStartTime);
        etPassword = findViewById(R.id.etPassword);
        tvSignup = findViewById(R.id.tvSignup);
        btnLogin = findViewById(R.id.btnLogin);
        btnFBLogin = findViewById(R.id.btnFBLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnFBLogin.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                loginFBUser();
            }
        });



        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();

            }
        });


    }

    private void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loginFBUser() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Please, wait a moment.");
        dialog.setMessage("Logging in...");
        dialog.show();
        Collection<String> permissions = Arrays.asList("public_profile", "email");
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, (user, err) -> {
            dialog.dismiss();
            if (err != null) {
                Log.e("FacebookLoginExample", "done: ", err);
                Toast.makeText(this, err.getMessage(), Toast.LENGTH_LONG).show();
            } else if (user == null) {
                Toast.makeText(this, "The user cancelled the Facebook login.", Toast.LENGTH_LONG).show();
                Log.d("FacebookLoginExample", "Uh oh. The user cancelled the Facebook login.");
            } else if (user.isNew()) {
                Toast.makeText(this, "User signed up and logged in through Facebook.", Toast.LENGTH_LONG).show();
                Log.d("FacebookLoginExample", "User signed up and logged in through Facebook!");
                getUserDetailFromFB();
            } else {
                Toast.makeText(this, "User logged in through Facebook.", Toast.LENGTH_LONG).show();
                Log.d("FacebookLoginExample", "User logged in through Facebook!");
                showAlert("Oh, you!", "Welcome back!");
            }
            closeContextMenu();
            goMainActivity();
        });
    }

    private void getUserDetailFromFB() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
            ParseUser user = ParseUser.getCurrentUser();
            try {
                if (object.has("first_name"))
                    user.put("firstName", object.getString("first_name"));
                if (object.has("last_name"))
                    user.put("lastName", object.getString("last_name"));
                if(object.has("id"))
                    user.setUsername(object.getString("id"));
                if (object.has("email"))
                    user.setEmail(object.getString("email"));



            } catch (JSONException e) {
                e.printStackTrace();
            }
            user.saveInBackground(e -> {
                if (e == null) {
                    showAlert("First Time Login!", "Welcome!");
                } else
                    showAlert("Error", e.getMessage());
            });
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,name,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    public void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        goMainActivity();
    }





}