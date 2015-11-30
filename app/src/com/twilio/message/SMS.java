package com.twilio.message;

import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.MessageList;

public class SMS {
    private  final String ACCOUNT_SID;
    private  final String AUTH_TOKEN;
    private  TwilioRestClient client;
    private  Account account;
    private  final String FROM_PHONE;

    // Singleton class
    private SMS instance = null;
    // ACCOUNT_SID = "AC2c3676a11ab15b14427a526f805000ca", AUTH_TOKEN = "4607b8f2cc2e593a4e8906619fc95a4a"
    public SMS getInstanceOf(String account_sid, String auth_token, String from_phone, SmsListener smsListener) {
        if (instance == null) {
            this.ACCOUNT_SID = account_sid;
            this.AUTH_TOKEN = auth_token;
            this.client = new TwilioRestClient(account_sid, auth_token);
            this.account = client.getAccount();
            this.FROM_PHONE = from_phone;
            new SmsRecieveAsyncTask(smsListener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            instance = this;
        }
        return instance;
    }
    private SMS() {

    }

    public sendSMS(String to_phone, String text_body) {
        MessageFactory messageFactory = this.account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", to_phone)); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", this.FROM_PHONE)); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("Body", text_body));
        Message sms = messageFactory.create(params);
    }


    private class SmsRecieveAsyncTask extends AsyncTask<Void,Void,Void> {
        private SmsListener smsListener;

        public SmsRecieveAsyncTask(SmsListener smsListener) {
            this.smsListener = smsListener;
        }

        @Override
        protected Void doInBackground() {
            int old_len = getMsgCount();
            int new_len = 0;
            while(true) {
                Thread.sleep(5000);
                MessageList msgList = getMessageList();
                new_len = msgList.getTotal();
                if (new_len > old_len) {
                    Message msg = getLatestMsg(msgList);
                    notify(msg);
                }
            }
            return null;
        }

        private void notify(Message msg) {
            this.smsListener.msgRecieved(msg);
        }

        private MessageList getMessageList() {
            Map<String,String> filter = new HashMap<String,String>();
            filter.put("to",SMS.this.FROM_PHONE);
            return SMS.this.account.getMessages(filter);
        }
        private int getMsgCount() {
            MessageList msgList = getMessageList();
            return msgList.getTotal();
        }

        private void isNewMsg(int old_len) {
            int new_len = getMsgCount();
            if (old_len != new_len) {
                return true;
            }
            return false;

        }

        private Message getLatestMsg(MessageList msgList) {
            for(Message msg: msgList) {
                return msg;
            }
            return null;
        }

    }

}