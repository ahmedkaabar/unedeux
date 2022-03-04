/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.PasswordAuthentication;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author YedesHamda
 */
public class mail {

    public static void sendMail(String msg, String dest) {
        try {
            System.out.println("Preparing to send email");
            Properties properties = new Properties();

            //Enable authentication
            properties.put("mail.smtp.auth", "true");
            //Set TLS encryption enabled
            properties.put("mail.smtp.starttls.enable", "true");
            //Set SMTP host
            properties.put("mail.smtp.host", "smtp.gmail.com");
            //Set smtp port
            properties.put("mail.smtp.port", "587");

            //Your gmail address
            String userName = "yassin.daboussi@esprit.tn";
            //Your gmail password
            String password = "24058589";

            //Create a session with account credentials
            Session session;
            session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(userName, password);
                }
            }
            );

            //Prepare email message
            //  Message message = prepareMessage(session, userName, "nawresboubakri@gmail.com",msg);
            Message message = prepareMessage(session, userName, dest, msg);
            //Send mail
            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (MessagingException ex) {
            ex.getMessage();
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            //  message.setSubject("Notification [Update]");
            message.setSubject("Notification");
            String htmlCode = "<h3> Cotakwira </h3> <br/> <h2><b>" + msg + "</b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * **********************************************************************************
     */
    public void notificationS(String title, String msg) {

        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle(title);
        tray.setMessage(msg);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
    }

    public void notificationF(String title, String msg) {

        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle(title);
        tray.setMessage(msg);
        tray.setNotificationType(NotificationType.ERROR);
        tray.showAndDismiss(Duration.millis(3000));

    }

    public long verifier(String email) {

        HttpClient client = new DefaultHttpClient();
        String Email = email;
        String APIKey = "ev-bdc5cd9c72218c0e99da99a4496bea65";
        String APIURL = "https://api.email-validator.net/api/verify";

        try {
            HttpPost request = new HttpPost(APIURL);
            List<NameValuePair> Input = new ArrayList<NameValuePair>();
            Input.add(new BasicNameValuePair("EmailAddress", Email));
            Input.add(new BasicNameValuePair("APIKey", APIKey));
            try {
                request.setEntity(new UrlEncodedFormEntity(Input));
            } catch (UnsupportedEncodingException ex) {
                ex.getStackTrace();
            }
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String Output = EntityUtils.toString(entity, "UTF-8");
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(Output);
            JSONObject jsonObject = (JSONObject) obj;
            long result = (long) jsonObject.get("status");
            // result 200, 207, 215 - valid
            // result 215 - can be retried to update catch-all status
            // result 114 - greylisting, wait 5min and retry
            // result 118 - api rate limit, wait 5min and retry
            // result 3xx/4xx - bad
            String info = (String) jsonObject.get("info");
            String details = (String) jsonObject.get("details");
            System.out.println("re" + result + "" + info);
            return result;
        } catch (ParseException e) {
            // e.printStackTrace();
            e.getMessage();
            return 0;
        } catch (IOException ex) {
            ex.getMessage();
            return 0;
            //    Logger.getLogger(mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*   } finally {
            client.getConnectionManager().shutdown();
            return result;
        }*/

    }


   
}
