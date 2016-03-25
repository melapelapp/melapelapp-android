package com.melapelapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.melapelapp.Store;
//import com.melapelapp.StoreSignup;
//import com.melapelapp.RegistrationService;
//import com.melapelapp.service.RegistrationServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
//stackoverflow driven development muahahaha

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher{
    private static final String TAG = "SignupActivity";
    ImageView imgLogo;
    TextView txtGeolocation;
    TextView txtLogin;
    TextView txtAddress1;
    TextView txtAddress2;
    TextView txtCityName;
    TextView txtStateName;
    TextView txtPostalCode;
    EditText txtStoreName;
    EditText txtUserName;
    EditText txtPhoneNumber;
    EditText txtPassword;
    Button btnSignUp;


    Context context;
    GeoLocationTracker geoLocationTracker;
    UserDataProvider userDataProvider;
    ProgressDialog progressDialog;

    private void initializeViews()
    {

        txtStoreName =  (EditText) findViewById(R.id.txt_store_name);
        txtUserName =  (EditText)  findViewById(R.id.txt_user_name);
        txtPhoneNumber =  (EditText)  findViewById(R.id.txt_phone_number);
        txtPassword =  (EditText)  findViewById(R.id.txt_password);
        txtLogin =  (TextView) findViewById(R.id.txt_login_link);
        txtAddress1 = (TextView) findViewById(R.id.txt_address1);
        txtAddress2 = (TextView) findViewById(R.id.txt_address2);
        txtCityName = (TextView) findViewById(R.id.txt_city_name);
        txtStateName = (TextView) findViewById(R.id.txt_state_name);
        txtPostalCode = (TextView) findViewById(R.id.input_postal_code);
        txtGeolocation = (TextView) findViewById(R.id.txt_geolocation);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        imgLogo = (ImageView) findViewById(R.id.img_logo);

        btnSignUp.setOnClickListener(this);
        txtLogin.setOnClickListener(this);

        imgLogo.setImageResource(R.drawable.melapel_app_logo);
        txtGeolocation.addTextChangedListener(this);

        progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retrieving data ...");
        progressDialog.show();

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //TODO: SharedPreferences  add this later, and refactor to remove duplication

        initializeViews();

        context = getApplicationContext();
        //GeoLocationTracker geoLocationTracker = GeoLocationTracker.getInstance(context);

        geoLocationTracker = new GeoLocationTracker(context,txtGeolocation);
        userDataProvider = new UserDataProvider(context);


    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnSignUp.setEnabled(false);

        //final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String storeName = txtStoreName.getText().toString();
        String userName = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();

        Address address = new Address(Locale.getDefault());
        address.setAddressLine(0, txtAddress1.getText().toString());
        address.setLocality(txtAddress1.getText().toString());
        address.setAdminArea(txtAddress1.getText().toString());
        address.setPostalCode(txtAddress1.getText().toString());



//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
  }

    public void onSignupSuccess() {
        btnSignUp.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnSignUp.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String storeName   = txtStoreName.getText().toString();
        String userName    = txtUserName.getText().toString();
        String password    = txtPassword.getText().toString();
        String address1    = txtAddress1.getText().toString();
        String address2    = txtAddress2.getText().toString();
        String cityName    = txtCityName.getText().toString();
        String stateName   = txtStateName.getText().toString();
        String postalCode  = txtPostalCode.getText().toString();
        String phoneNumber = txtPassword.getText().toString();
        

        if (storeName.isEmpty() || storeName.length() < 3) {
            txtStoreName.setError("at least 3 characters");
            valid = false;
        } else {
            txtStoreName.setError(null);
        }

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

        if (phoneNumber.isEmpty() || !Patterns.PHONE.matcher(phoneNumber).matches()) {
            txtPhoneNumber.setError("enter a valid phone number");
            valid = false;
        } else {
            txtPhoneNumber.setError(null);
        }

        if (address1.isEmpty() || address1.length() < 3) {
            txtAddress1.setError("at least 5 characters");
            valid = false;
        } else {
            txtAddress1.setError(null);
        }

        if (cityName.isEmpty() || cityName.length() < 3) {
            txtCityName.setError("at least 5 characters");
            valid = false;
        } else {
            txtCityName.setError(null);
        }

        if (stateName.isEmpty() || stateName.length() < 3) {
            txtStateName.setError("at least 5 characters");
            valid = false;
        } else {
            txtStateName.setError(null);
        }

        if (postalCode.isEmpty() || postalCode.length() < 3) {
            txtPostalCode.setError("at least 5 characters");
            valid = false;
        } else {
            txtPostalCode.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.btn_signup:
                signup();
                break;
            case R.id.link_login:
                finish();
                break;

        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setLocation();
        setUserAccount();
        geoLocationTracker.stop();
        progressDialog.dismiss();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    private void setUserAccount()
    {

        String userName = userDataProvider.getEmail();
        String phoneNumber = userDataProvider.getPhoneNumber();

        txtUserName.setText(userName);
        txtPhoneNumber.setText(phoneNumber);
    }

    private void setLocation()
    {
       Location location = geoLocationTracker.getLocation();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());;
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: This can happen because location services is not enabled? If so, suggest to enable?
            // else, just ignore?
        }

        if (addresses != null || addresses.size() > 0) {
            Address address = addresses.get(0);
            txtAddress1.setText(address.getAddressLine(0));
            txtCityName.setText(address.getLocality());
            txtStateName.setText(address.getAdminArea());
            txtPostalCode.setText(address.getPostalCode());
        }
    }





}