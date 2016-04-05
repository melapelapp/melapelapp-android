package com.melapelapp;

import android.app.ProgressDialog;
import android.location.Address;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.melapelapp.commons.SimpleCountDownTimer;
import com.melapelapp.commons.validation.Validator;
import com.melapelapp.domain.SignUpObject;
import com.melapelapp.geolocation.GeolocationAddressTracker;
import com.melapelapp.geolocation.GeolocationTracker;
import com.melapelapp.geolocation.LocationSettingsBuilder;
import com.melapelapp.geolocation.TrackingListener;
import com.melapelapp.service.UserService;
import com.melapelapp.service.UserServiceStub;

import java.util.Locale;

import static com.melapelapp.commons.validation.Validator.ValidationTypes.*;

public class SignupActivity extends AppCompatActivity {
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

    ProgressDialog progressDialog;

    GeolocationTracker geoLocationTracker;
    UserService signUpService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeViews();
        setButtonHandlers();
        displayUserAccountData();

        signUpService = new UserServiceStub();

        geoLocationTracker = new GeolocationAddressTracker(
                getApplicationContext(),
                new TrackingListener<Address>() {
                    @Override
                    public void doWithTracking(Address address) {
                        displayAddress(address);
                    }

                    @Override
                    public void onStop() {
                        progressDialog.dismiss();
                    }
                },
                LocationSettingsBuilder.newInstance().withMinTime(1000 * 2).withMinDistance(1).withMaxUpdates(3).build()
        );

        geoLocationTracker.start();

        if (geoLocationTracker.isEnabled()) {
            displayDialogMessage(R.string.loading_userdata);
            new SimpleCountDownTimer(8000) {
                @Override
                public void onFinish() { progressDialog.dismiss(); }
            }.start();
        }

    }

    private void initializeViews() {
        txtStoreName = (EditText) findViewById(R.id.txt_store_name);
        txtUserName = (EditText) findViewById(R.id.txt_user_name);
        txtPhoneNumber = (EditText) findViewById(R.id.txt_phone_number);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        txtLogin = (TextView) findViewById(R.id.txt_login_link);
        txtAddress1 = (TextView) findViewById(R.id.txt_address1);
        txtAddress2 = (TextView) findViewById(R.id.txt_address2);
        txtCityName = (TextView) findViewById(R.id.txt_city_name);
        txtStateName = (TextView) findViewById(R.id.txt_state_name);
        txtPostalCode = (TextView) findViewById(R.id.input_postal_code);
        txtGeolocation = (TextView) findViewById(R.id.txt_geolocation);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setImageResource(R.drawable.melapel_app_logo);

        progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
    }

    private void signUp() {
        boolean valid = Validator.getInstance().add(txtStoreName, TEXT_MIN_LENGTH, 3).add(txtUserName, EMAIL_ADDRESS)
                .add(txtPassword, TEXT_MIN_MAX_LENGTH, 4, 10).add(txtPhoneNumber, PHONE)
                .add(txtAddress1, TEXT_MIN_LENGTH, 3).add(txtCityName, TEXT_MIN_LENGTH, 3)
                .add(txtStateName, TEXT_MIN_LENGTH, 3).add(txtPostalCode, TEXT_MIN_LENGTH, 3)
                .validate();

        if (!valid) {
            return;
        }

        btnSignUp.setEnabled(false);
        displayDialogMessage(R.string.creating_account);

        Address address = new Address(Locale.getDefault());
        address.setAddressLine(0, txtAddress1.getText().toString());
        address.setLocality(txtAddress1.getText().toString());
        address.setAdminArea(txtAddress1.getText().toString());
        address.setPostalCode(txtAddress1.getText().toString());

        final SignUpObject signUpObj = new SignUpObject(
                txtStoreName.getText().toString(),
                txtUserName.getText().toString(),
                txtPassword.getText().toString(),
                address
        );

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        try {
                            signUpService.signup(signUpObj);
                            setResult(RESULT_OK, null);
                            Toast.makeText(getBaseContext(), "success!", Toast.LENGTH_LONG).show();
                            btnSignUp.setEnabled(true);
                        } catch(Exception e) {
                            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            btnSignUp.setEnabled(true);
                        } finally {
                            progressDialog.dismiss();
                        }
                    }
                }, 3000);
    }

    private void setButtonHandlers() {
        btnSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        txtLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayDialogMessage(int stringId) {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(stringId));
        progressDialog.show();
    }

    private void displayUserAccountData() {
        UserDataProvider userDataProvider = new UserDataProvider(getApplicationContext());
        txtUserName.setText(userDataProvider.getEmail());
        txtPhoneNumber.setText(userDataProvider.getPhoneNumber());
    }

    private void displayAddress(Address address) {
        if (address != null) {
            txtAddress1.setText(address.getAddressLine(0));
            txtCityName.setText(address.getLocality());
            txtStateName.setText(address.getAdminArea());
            txtPostalCode.setText(address.getPostalCode());
        }

        progressDialog.dismiss();
    }

}