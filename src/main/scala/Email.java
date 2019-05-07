import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

    public class Email {

        public Email() {
        }

        public static void main(String[] args) {
            try {
                Email email = new Email();
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

                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.starttls.enable", "true");
                prop.put("mail.smtp.host", "smtp.gmail.com");
                prop.put("mail.smtp.port", "587");

                final String username = "hasijaayushi@gmail.com";
                final String password = "Shasija24";

                Session session = Session.getInstance(prop,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                // Define message
                MimeMessage message = new MimeMessage(session);
                message.addHeaderLine("method=REQUEST");
                message.addHeaderLine("charset=UTF-8");
                message.addHeaderLine("component=VEVENT");

                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Testing Calendar Invite");


                Calendar calendar = new Calendar();
                calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
                calendar.getProperties().add(Version.VERSION_2_0);
                calendar.getProperties().add(CalScale.GREGORIAN);

                // initialise as an all-day event..
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
                cal.set(java.util.Calendar.DAY_OF_MONTH, 25);

                final DateTime fromDate = new DateTime(1557139519000L);
                fromDate.setUtc(true);
                System.out.println(fromDate);

                final DateTime toDate = new DateTime(1557141619000L);
                toDate.setUtc(true);
                System.out.println(toDate);

                VEvent christmas = new VEvent(fromDate,toDate, "Booking Confirmation Invite");



                // Generate a UID for the event..
         /*       UidGenerator ug = new UidGenerator("RAP");
                christmas.getProperties().add(ug.generateUid());*/
                calendar.getComponents().add(christmas);

                Status state = new Status("NEEDS-ACTION");
                christmas.getProperties().add(state);

                System.out.println(christmas);

                Transp transp = new Transp("OPAQUE");
                christmas.getProperties().add(transp);


                // Create the message part
                BodyPart messageBodyPart = new MimeBodyPart();

                // Fill the message
                messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
                messageBodyPart.setHeader("Content-ID", "calendar_message");
                messageBodyPart.setDataHandler(new DataHandler(
                        new ByteArrayDataSource(calendar.toString(), "text/calendar")));// very important

                // Create a Multipart
                Multipart multipart = new MimeMultipart();

                // Add part one
                multipart.addBodyPart(messageBodyPart);

                // Put parts in message
                message.setContent(multipart);

                // send message
                System.out.println("here");

                Transport.send(message);

                System.out.println("Success");

            } catch (MessagingException me) {
                me.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

