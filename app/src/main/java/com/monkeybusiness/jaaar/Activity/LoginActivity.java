package com.monkeybusiness.jaaar.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.monkeybusiness.jaaar.R;
import com.rey.material.widget.Button;
import com.rey.material.widget.FloatingActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {


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
//        startAnimations();
        ButterKnife.bind(this);

        textViewforgotpass.setText(Html.fromHtml("<u>Forgot Password?</u>"));
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRoundRect("", Color.WHITE,10);


//        linearLayoutMain.setBackgroundDrawable(drawable);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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
