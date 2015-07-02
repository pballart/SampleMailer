package com.solartappse.mailer.mailer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
    }

    /** Called when the user touches the button */
    public void sendMail(View view) {
        // Do something in response to button click
        EditText recipientEditText   = (EditText)findViewById(R.id.recipient);
        EditText subjectEditText   = (EditText)findViewById(R.id.subject);
        EditText bodyEditText   = (EditText)findViewById(R.id.body);
        if (recipientEditText.getText().toString().length() == 0 || subjectEditText.getText().toString().length() == 0 || bodyEditText.getText().toString().length() == 0) {
            this.showToastWithText("Error: one of the text fields is empty");
            return;
        }
        if (!this.isEmailValid(recipientEditText.getText().toString())) {
            this.showToastWithText("The email adress is not valid");
            return;
        }

        // If all verifications passed then send mail using intent ACTION_SEND
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{recipientEditText.getText().toString()});
        i.putExtra(Intent.EXTRA_SUBJECT, subjectEditText.getText().toString());
        i.putExtra(Intent.EXTRA_TEXT   , bodyEditText.getText().toString());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(EmailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showToastWithText(String text) {
        Context context = getApplicationContext();
        CharSequence textToast = text;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, textToast, duration);
        toast.show();
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email adress
     * @return boolean true for valid false for invalid
     */
    public boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
