/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.tests;

/**
 *
 * @author MSI
 */
import java.sql.Date;
import java.time.LocalDate;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.sql.SQLException;
import java.util.List;
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.services.PromoService;

public class PromoReminder {
  
  // les informations de connexion à l'API Twilio
  public static final String ACCOUNT_SID = "AC6f8458a4c5a9390aa02fb7188102e896";
  public static final String AUTH_TOKEN = "0c083b11fc9076faca4e6300107f3f39";
  
  // méthode pour envoyer un SMS en utilisant l'API Twilio
  public static void sendSMS(String message, String to) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message.creator(new PhoneNumber(to), new PhoneNumber("+15672922174"), message).create();
  }
  
  public static void main(String[] args) throws SQLException {
    // supposons que nous avons la date de fin de la promotion dans une variable promoEndDate de type java.sql.Date
    PromoService ps = new PromoService();
    List<Promo> p = ps.selectAll();
    for(Promo pr : p)
    {
        Date promoEndDate = pr.getDate_fin();
        System.out.println(promoEndDate);
        // calculer la date d'aujourd'hui plus cinq jours en utilisant la classe LocalDate
    LocalDate today = LocalDate.now();
    System.out.println(today);
    LocalDate promoReminderDate = today.plusDays(5);
    System.out.println(promoReminderDate);
    
    // comparer les deux dates en utilisant la méthode equals de la classe LocalDate
    if (promoReminderDate.equals(promoEndDate.toLocalDate())) {
      // envoyer le SMS de rappel à l'utilisateur
      sendSMS("Votre promotion va expirer dans 5 jours. Profitez-en maintenant !", "+216 29 537 908");
    }
    }
    
    
    
  }
}
