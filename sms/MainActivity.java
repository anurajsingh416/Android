package com.example.sms;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumber,message;
    private Button sendButton;
    private static final int PERMISSION_REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumber = findViewById(R.id.phoneNumber);
        message = findViewById(R.id.message);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS)!= PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},PERMISSION_REQUEST_CODE);
                }else{
                    sendSms();
                }
            }
        });
      if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, PERMISSION_REQUEST_CODE);
    }
        registerReceiver(new SMSReceiver(), new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

}

    private void sendSms(){
        String phone = phoneNumber.getText().toString();
        String msg = message.getText().toString();
        if(!phone.isEmpty() && !msg.isEmpty()) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,msg,null,null);
            Toast.makeText(this, "SMS Sent!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter phone number and message", Toast.LENGTH_SHORT).show();
        }
    }
    public void onRequestPermissionsResult(int requestCode,String []permissions,int []grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode==PERMISSION_REQUEST_CODE){
            if(grantResults[0]==PERMISSION_GRANTED){
                sendSms();
            }else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}