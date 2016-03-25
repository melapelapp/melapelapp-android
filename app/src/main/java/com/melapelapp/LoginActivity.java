package com.melapelapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    //ImageView imgLogo;
    TextView txtSignUp;
    TextView txtUserName;
    EditText txtPassword;
    Button btnLogin;
    Context context;

    ImageView imgLogo;


    private void initializeViews()
    {

        txtUserName =  (EditText) findViewById(R.id.txt_user);
        txtPassword =  (EditText)  findViewById(R.id.txt_password);

        txtSignUp =  (TextView)  findViewById(R.id.txt_signup);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        btnLogin = (Button) findViewById(R.id.btn_login);

        imgLogo.setImageResource(R.drawable.melapel_app_logo);
        btnLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //TODO: SharedPreferences  add this later

        initializeViews();

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setImageResource(R.drawable.melapel_app_logo);
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), ActivityPersonList.class);
                        startActivity(intent);


                    }
                }, 3000);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

       btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String userName    = txtUserName.getText().toString();
        String password    = txtPassword.getText().toString();


        if (userName.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
            txtUserName.setError("enter a valid email address");
            valid = false;
        } else {
            txtUserName.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            txtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.btn_login:
                login();
                break;
            case R.id.txt_signup:
                signUp();
                break;

        }

    }

    private void signUp()
    {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

}
