package com.monkeybusiness.jaaar.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.amulyakhare.textdrawable.TextDrawable;
import com.dd.processbutton.iml.ActionProcessButton;
import com.monkeybusiness.jaaar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {


    @Bind(R.id.input_email)
    EditText inputEmail;
    @Bind(R.id.input_password)
    EditText inputPassword;
    @Bind(R.id.btn_login)
    ActionProcessButton loginButton;
    @Bind(R.id.linearLayoutMain)
    LinearLayout linearLayoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRoundRect("", Color.WHITE,10);


        linearLayoutMain.setBackgroundDrawable(drawable);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.setMode(ActionProcessButton.Mode.ENDLESS);
                loginButton.setProgress(1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loginButton.setProgress(100);
                        login();
                    }
                }, 3000);

            }
        });
    }

    public void login() {
        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
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
