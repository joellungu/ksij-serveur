package org.ksij.models;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.ejb.ApplicationException;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class EvenNotification {

    public EvenNotification(){
        FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream("src/ksij-kinshasa-firebase-adminsdk-ja7e9-a1b4d63736.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("ksij-kinshasa")
                    .build();
            //FirebaseApp.initializeApp(options, "syg");
            LocalDateTime localDateTime = LocalDateTime.now();
            //
            FirebaseApp app = null;
            if(FirebaseApp.getApps().isEmpty()) {
                app = FirebaseApp.initializeApp(options, "KSIJ"+localDateTime.getNano());
            }else {
                app = FirebaseApp.initializeApp(options);
            }
            //
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private AtomicInteger counter = new AtomicInteger();

    public int get() {
        return counter.get();
    }

    public void verification(HashMap<String, String> notification) throws IOException {

        //
        //FirebaseApp.initializeApp();
        //{id: 101,
        // titre: Des nouvelles de chez nous,
        // sousTitre: null,
        // contenu: Salut comment tu vas bros kala fort oza kaka,
        // auteur: null,
        // dateTime: 13-1-2024,
        // asPhoto:
        // true, photo: }

        // This registration token comes from the client FCM SDKs.
        //String registrationToken = "dNkUsgdUR56hFwih9LzDwd:APA91bEM5pbGRo5bLGoMRE0Ke6kuuGn5pV5B0YxB_JL4hkEHnbdF8K4Ai2unjU8rBzh2nJpeL4Hn0vQqtwONGuLUKynWeI0MvpMCVoCaZGe_t8wsbYa8SdeDkLuoDPURgAAcTMuTiSY3";
        //
        Notification.Builder builder = Notification.builder();
        /*
        Notification notification = Notification
                .builder()
                .setTitle("Une Notification")
                .setBody("Salut toi, comment tu vas ?")
                .build();
        */

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .setNotification(builder
                        .setTitle(notification.get("titre"))
                        .setBody(notification.get("contenu"))
                        .build())

                .putData("id", notification.get("id"))
                .putData("titre", notification.get("titre"))
                .putData("contenu", notification.get("contenu"))
                .putData("dateTime", notification.get("dateTime"))
                .putData("asPhoto", String.valueOf(notification.get("asPhoto")))
                .setTopic(notification.get("topic"))
                //.setTopic("test")
                //.setToken(token)q
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        //
    }
    public void rappel(HashMap<String, String> notification) throws IOException {

        Notification.Builder builder = Notification.builder();
        System.out.println("le topic: "+notification.get("topic"));

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .setNotification(builder
                        .setTitle(notification.get("titre"))
                        .setBody(notification.get("contenu"))
                        .build())

                //putData("id", notification.get("id"))
                .putData("titre", notification.get("titre"))
                .putData("contenu", notification.get("contenu"))
                //.putData("dateTime", notification.get("dateTime"))
                //.putData("asPhoto", String.valueOf(notification.get("asPhoto")))
                .setTopic(notification.get("topic"))
                //.setTopic("test")
                //.setToken(token)q
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        //
    }
    public void courseNotification2(String registrationToken) throws IOException {

        List<String> listeTokens = new LinkedList<>();
        List<Token> tokens = Token.listAll();
        //
        tokens.forEach((t)->{
            //
            listeTokens.add(t.token);
        });
        //
        //FirebaseApp.initializeApp();
        //
        // This registration token comes from the client FCM SDKs.
        //= "dNkUsgdUR56hFwih9LzDwd:APA91bEM5pbGRo5bLGoMRE0Ke6kuuGn5pV5B0YxB_JL4hkEHnbdF8K4Ai2unjU8rBzh2nJpeL4Hn0vQqtwONGuLUKynWeI0MvpMCVoCaZGe_t8wsbYa8SdeDkLuoDPURgAAcTMuTiSY3";
        //
        Notification.Builder builder = Notification.builder();
        //
        Notification notification = Notification
                .builder()
                .setTitle("Une Notification")
                .setBody("Salut toi, comment tu vas ?")
                .build();

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .setNotification(builder.build())
                .putData("title", "Une Notification")
                .putData("body", "Salut toi, comment tu vas ?")
                .setToken(registrationToken)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        //
    }


    /*

    @Scheduled(cron="0 15 10 * * ?")
    void cronJob(ScheduledExecution execution) {
        counter.incrementAndGet();
        System.out.println(execution.getScheduledFireTime());
    }

    @Scheduled(cron = "{cron.expr}")
    void cronJobWithExpressionInConfig() {
        counter.incrementAndGet();
        System.out.println("Cron expression configured in application.properties");
    }
    */

}
