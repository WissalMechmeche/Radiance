/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author MSI
 */
public class SmsSender {
    // Vos identifiants Twilio
    public static final String ACCOUNT_SID = "AC6f8458a4c5a9390aa02fb7188102e896";
    public static final String AUTH_TOKEN = "e3e72e7aebdb899c13124e9a008edbad";

    public static void sendSms(String toPhoneNumber, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber("+15672922174"),
                messageBody)
            .create();

        System.out.println("SMS envoyé avec succès : " + message.getSid());
    }
    
}
