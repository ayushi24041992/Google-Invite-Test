import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class TestingEmail {

    public static void main(String[] args) {
        try {
            TestingEmail email = new TestingEmail();
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send() throws Exception {

        try {
            String from = "hasijaayushi@gmail.com";
            String to = "ayushi.hasija@knoldus.in";
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.starttls.enable", "true");


            Session session = Session.getDefaultInstance(prop, null);
            // Define message
            MimeMessage message = new MimeMessage(session);
            message.addHeaderLine("method=REQUEST");
            message.addHeaderLine("charset=UTF-8");
            message.addHeaderLine("component=VEVENT");

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Meeting Request using Gmail");

            System.out.println("here");

            StringBuffer sb = new StringBuffer();

            StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                    "PRODID:-//Google Inc//Google Calendar 70.9054//EN\n" +
                    "VERSION:2.0\n" +
                    "CALSCALE:GREGORIAN\n" +
                    "METHOD:REQUEST\n" +
                    "BEGIN:VTIMEZONE\n" +
                    "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:hasijaayushi@gmail.com\n" +
                    "ORGANIZER:MAILTO:xx@xx.com\n" +
                    "DTSTART;TZID=Asia/Kolkata:20190107T104500\n" +
                    "DTEND;TZID=Asia/Kolkata:20190107T105500\n" +
                    "LOCATION:Conference room\n" +
                    "TRANSP:OPAQUE\n" +
                    "SEQUENCE:0\n" +
                    "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n" +
                    " 000004377FE5C37984842BF9440448399EB02\n" +
                    "DTSTAMP:20190429T051550Z" +
                    "CATEGORIES:Meeting\n" +
                    "DESCRIPTION:This the description of the meeting.\n\n" +
                    "SUMMARY:Test meeting request\n" +
                    "PRIORITY:5\n" +
                    "CLASS:PUBLIC\n" +
                    "BEGIN:VALARM\n" +
                    "TRIGGER:PT1440M\n" +
                    "ACTION:DISPLAY\n" +
                    "DESCRIPTION:Reminder\n" +
                    "END:VALARM\n" +
                    "END:VEVENT\n" +
                    "END:VCALENDAR");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            messageBodyPart.setHeader("Content-ID", "calendar_message");
            messageBodyPart.setDataHandler(new DataHandler(
                    new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very important

            // Create a Multipart
            Multipart multipart = new MimeMultipart();

            // Add part one
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            message.setContent(multipart);

            // send message
            Transport.send(message);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
