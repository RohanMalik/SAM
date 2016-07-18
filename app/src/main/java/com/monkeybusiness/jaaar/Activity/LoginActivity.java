package com.monkeybusiness.jaaar.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.gson.Gson;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.loginRequestObject.LoginRequestObject;
import com.monkeybusiness.jaaar.objectClasses.loginRequestObject.Session;
import com.monkeybusiness.jaaar.objectClasses.loginResponseData.LoginResponse;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.dialogBox.CommonDialog;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.FloatingActionButton;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import rmn.androidscreenlibrary.ASSL;

public class LoginActivity extends AppCompatActivity {


    final String TAG = "LoginActivity.java";
    @Bind(R.id.input_email)
    EditText inputEmail;
    @Bind(R.id.input_password)
    EditText inputPassword;
    @Bind(R.id.btn_login)
    FloatingActionButton loginButton;
    @Bind(R.id.textViewForgotPass)
    TextView textViewforgotpass;
//    @Bind(R.id.linearLayoutMain)
//    LinearLayout linearLayoutMain;

    CheckBox checkBoxRememberMe;


//    private void startAnimations() {
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
//        anim.reset();
//        LinearLayout l=(LinearLayout) findViewById(R.id.linearLayoutMain);
//        l.clearAnimation();
//        l.startAnimation(anim);
//
//        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
//        anim.reset();
//        ImageView iv = (ImageView) findViewById(R.id.logo);
//        iv.clearAnimation();
//        iv.startAnimation(anim);
//
//        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
//        anim.reset();
//        EditText iv1 = (EditText) findViewById(R.id.input_email);
//        iv1.clearAnimation();
//        iv1.startAnimation(anim);
//
//        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
//        anim.reset();
//        EditText iv2 = (EditText) findViewById(R.id.input_password);
//        iv2.clearAnimation();
//        iv2.startAnimation(anim);
//
//        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
//        anim.reset();
//        Button iv3 = (Button) findViewById(R.id.btn_login);
//        iv3.clearAnimation();
//        iv3.startAnimation(anim);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);
//        startAnimations();
        ButterKnife.bind(this);

        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBoxRememberMe);

        textViewforgotpass.setText(Html.fromHtml("<u>Forgot Password?</u>"));
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRoundRect("", Color.WHITE, 10);

        String verifiedUser = Prefs.with(this).getString(PrefsKeys.VERIFIED_USER,Constants.UNVERIFIED);

        if (verifiedUser.equalsIgnoreCase(Constants.VERIFIED))
        {
            Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();
        }

//        linearLayoutMain.setBackgroundDrawable(drawable);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
//        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
//        startActivity(i);
//        finish();

        ProgressDialog dialog = ProgressDialog.show(this, "Please wait", "Verifying...",true);

        LoginRequestObject loginRequestObject = new LoginRequestObject();

        Session session = new Session();
        session.setUsername(inputEmail.getText().toString());

        boolean remember;

        if (checkBoxRememberMe.isChecked())
        {
            remember = true;
        }
        else
        {
            remember = false;
        }

        session.setRemember(remember);
        loginRequestObject.setSession(session);
        loginRequestObject.setPassword(inputPassword.getText().toString());

//        String ssn = "{\"session\":{\"username\":\"bytedreams\"},\"password\":\"bytedreams\"}";
        Log.d(TAG, "JSON : " + new Gson().toJson(loginRequestObject));
        String jsonObject = new Gson().toJson(loginRequestObject);

        try {
            TypedInput in = new TypedByteArray("application/json", jsonObject.getBytes("UTF-8"));
            String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES,"");
            String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

            RestClient.getApiServicePojo(xCookies,aCookies).apiCallLogin(in, new Callback<LoginResponse>() {
                @Override
                public void success(LoginResponse loginResponse, Response response) {
                    Log.d(TAG, "Response : " + new Gson().toJson(loginResponse));
                    dialog.dismiss();
                    boolean isXcookies = true;
                    for (Header header : response.getHeaders()) {
                        if (header.getName().equalsIgnoreCase("set-cookie")) {
                            if (isXcookies)
                            {
                                Prefs.with(getApplicationContext()).save(PrefsKeys.X_COOKIES, header.getValue().replaceAll("; path=/", ""));
                                Log.e("Cookies", "==" + header.getValue().replaceAll("; path=/", ""));
                                isXcookies = false;
                            }
                            else
                            {
                                Prefs.with(getApplicationContext()).save(PrefsKeys.A_COOKIES, header.getValue().replaceAll("; path=/; HttpOnly", ""));
                                Log.e("Cookies", "==" + header.getValue().replaceAll("; path=/; HttpOnly", ""));
                                break;
                            }
                        }
                    }

                    if (loginResponse!=null)
                    {
                        if (loginResponse.getResponseMetadata().getSuccess().equalsIgnoreCase("yes") && loginResponse.getData().getUserRoles().get(0).getRoleName().equalsIgnoreCase("Teacher"))
                        {
                            Prefs.with(LoginActivity.this).save(PrefsKeys.VERIFIED_USER, Constants.VERIFIED);
                            Prefs.with(LoginActivity.this).save(PrefsKeys.LOGIN_RESPONSE_DATA,loginResponse);
                            Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (loginResponse.getResponseMetadata().getSuccess().equalsIgnoreCase("yes") && loginResponse.getData().getUserRoles().get(0).getRoleName().equalsIgnoreCase("School"))
                        {
                            new CommonDialog(LoginActivity.this).Show("Something went wrong");
                            Prefs.with(LoginActivity.this).save(PrefsKeys.VERIFIED_USER,Constants.UNVERIFIED);
                        }
                        else {
                            new CommonDialog(LoginActivity.this).Show(loginResponse.getResponseMetadata().getMessage());
                            Prefs.with(LoginActivity.this).save(PrefsKeys.VERIFIED_USER,Constants.UNVERIFIED);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    dialog.dismiss();
                    Log.d(TAG, "Error : " + error.toString());
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // Log.d(TAG, "Login");

//        if (!validate()) {
//            onLoginFailed();
        return;
        //       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
