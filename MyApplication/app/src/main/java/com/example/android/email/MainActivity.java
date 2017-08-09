package com.example.android.email;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;

        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import javax.mail.MessagingException;

import static android.R.id.message;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addImage = (Button) findViewById(R.id.button);

        Activity activity = null;
        addImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new SendMail().execute("");
            }
        });
    }

    private class SendMail extends AsyncTask<String, Integer, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        protected Void doInBackground(String... params) {
            mail m = new mail("sruthibadal@gmail.com", "password");

            String[] toArr = {"sruthibadal@gmail.com", "sruthibadal@gmail.com"};
            m.setTo(toArr);
            m.setFrom("sruthibadal@gmail.com");
            m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
            m.setBody("Hey");

            try {
                if (m.send()) {
                    Log.v("mail", "Email sent");


                } else {
                    Log.e("Email","Email Not Sent");
                }
            } catch (Exception e) {
                Log.e("MailApp", "Could not send email", e);
            }
            return null;
        }
    }
}




