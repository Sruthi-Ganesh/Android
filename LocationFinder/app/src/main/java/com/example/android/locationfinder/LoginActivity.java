package com.example.android.locationfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SruthiGanesh on 4/26/2017.
 */

public class LoginActivity extends AppCompatActivity {
    TextView username;
    TextView password;
     String user="";
     String pass="";
    Button submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        submit =(Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();
                loginActivity();
            }
        });
    }
    // To log in to the mainactivity
    public void loginActivity()
    {
        if(user.equals("zoho"))
        {
            if(pass.equals("zoho@1"))
            {
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(this,"Wrong password",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this,"Wrong user name",Toast.LENGTH_LONG).show();
        }
    }
}