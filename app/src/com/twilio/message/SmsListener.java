package com.twilio.message;

import com.twilio.sdk.resource.instance.Message;

public static abstract class SmsListener {
    abstract void msgRecieved(Message msg);
}