package com.example.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent){
        Object[] pdus = (Object[])intent.getExtras().get("pdus");
        SmsMessage []messages = new SmsMessage[pdus.length];
        StringBuilder messageBody = new StringBuilder();

        for (int i = 0; i < pdus.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            messageBody.append(messages[i].getMessageBody());
        }
        String receivedMessage = messageBody.toString();
        Toast.makeText(context, "Received message: " + receivedMessage, Toast.LENGTH_LONG).show();
    }

}

