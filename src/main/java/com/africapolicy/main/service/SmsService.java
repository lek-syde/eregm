package com.africapolicy.main.service;

import com.twilio.Twilio;
import org.springframework.stereotype.Service;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * @author Olalekan Folayan
 */
@Service
public class SmsService {


    private String ACCOUNT_SID="ACf054565e975bdb174cbf6ac38e04c85d";
    private String AUTH_TOKEN="c5596dbec60f9898aa815d132e2190be";


    public void sendTwilioSms(String phonenumber, String messaage){


        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message.creator(new PhoneNumber(phonenumber), new PhoneNumber("+12035775203"),
                messaage).create();



    }
}

