package com.melapelapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.melapelapp.commons.validation.Validator;
import com.melapelapp.domain.User;
import com.melapelapp.service.StoreService;
import com.melapelapp.service.StoreServiceFactory;

import static com.melapelapp.commons.validation.Validator.ValidationTypes.EMAIL_ADDRESS;
import static com.melapelapp.commons.validation.Validator.ValidationTypes.PASSWORD;


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

    StoreService storeService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //TODO: SharedPreferences  add this later

        initializeViews();

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setImageResource(R.drawable.melapel_app_logo);

        storeService = StoreServiceFactory.create();
    }

    private void initializeViews() {
        txtUserName =  (EditText) findViewById(R.id.txt_user);
        txtPassword =  (EditText)  findViewById(R.id.txt_password);

        txtSignUp =  (TextView)  findViewById(R.id.txt_signup);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        btnLogin = (Button) findViewById(R.id.btn_login);

        imgLogo.setImageResource(R.drawable.melapel_app_logo);
        btnLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
    }

    public void login() {
        Log.d(TAG, "Login");

        boolean valid = Validator.getInstance().add(txtUserName, EMAIL_ADDRESS).add(txtPassword, PASSWORD).validate();

        if (!valid) {
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        User user = storeService.login(txtUserName.getText().toString(), txtPassword.getText().toString());
                        if (user != null) {
                            onLoginSuccess();
                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), PersonListActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                        }
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
