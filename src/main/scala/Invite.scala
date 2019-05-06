import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.{Version, _}
import net.fortuna.ical4j.util.UidGenerator
import java.util.Properties

import javax.activation.DataHandler
import javax.mail._
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import javax.mail.util.ByteArrayDataSource
import net.fortuna.ical4j.model.parameter.{CuType, PartStat, Rsvp}


object Invite {
  def main(args: Array[String]): Unit = {
    try {
      val invite = new Invite
      invite.send()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}

class Invite {
  @throws[Exception]
  def send(): Unit = {
    try {

      val from = "hasijaayushi@gmail.com"
      val to = "ayushi.hasija@knoldus.in"
      val prop = new Properties
      prop.put("mail.smtp.auth", "true")
      prop.put("mail.smtp.starttls.enable", "true")
      prop.put("mail.smtp.host", "smtp.gmail.com")
      prop.put("mail.smtp.port", "587")
      val username = "hasijaayushi@gmail.com"
      val password = "Shasija24"
      val session = Session.getInstance(prop, new Authenticator() {
        override protected def getPasswordAuthentication = new PasswordAuthentication(username, password)
      })
      // Define message

      val message = new MimeMessage(session)
      message.addHeaderLine("method=REQUEST")
      message.addHeaderLine("charset=UTF-8")
      message.addHeaderLine("component=VEVENT")
      message.setFrom(new InternetAddress(from))
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to))
      message.setSubject("Testing Calendar Invite")



      val calendar = new Calendar
      calendar.getProperties.add(new ProdId("-//Google Inc//Google Calendar 70.9054//EN"))
      calendar.getProperties.add(Version.VERSION_2_0)
      calendar.getProperties.add(CalScale.GREGORIAN)

      // initialise as an all-day event..
      val fromDate = new DateTime(1557153079000L)
      fromDate.setUtc(true)
      val toDate = new DateTime(1557153139000L)
      toDate.setUtc(true)
      val meeting = new VEvent(fromDate, toDate, "Booking Confirmation Invite")

      val state = new Status("NEEDS-ACTION")
      meeting.getProperties.add(state)

      // Generate a UID for the event..
      val ug = new UidGenerator("RAP")
      meeting.getProperties.add(ug.generateUid)



      val transp = new Transp("OPAQUE")
      meeting.getProperties.add(transp)

      import net.fortuna.ical4j.model.parameter.Cn
      import net.fortuna.ical4j.model.parameter.Role
      import net.fortuna.ical4j.model.property.Attendee
      import java.net.URI
      // add attendees..// add attendees..

      val dev1 = new Attendee(URI.create("mailto:sakshi.hasija24@gmail.com"))
      dev1.getParameters.add(CuType.INDIVIDUAL)
      dev1.getParameters.add(Role.REQ_PARTICIPANT)
      dev1.getParameters.add(PartStat.NEEDS_ACTION)
      dev1.getParameters.add(new Cn("sakshi.hasija24@gmail.com"))
      dev1.getParameters.add(Rsvp.TRUE)
      meeting.getProperties.add(dev1)

      calendar.getComponents.add(meeting)

      println(meeting)


      // Create the message part
      val messageBodyPart = new MimeBodyPart
      // Fill the message
      messageBodyPart.setHeader("Content-Type", "application/ics")
      messageBodyPart.setHeader("name","invite.ics")
      messageBodyPart.setHeader("Content-Disposition","attachment")
      messageBodyPart.setHeader("filename","invite.ics")
      messageBodyPart.setHeader("method","REQUEST")
      messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage")
      messageBodyPart.setHeader("Content-ID", "calendar_message")
      messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(calendar.toString, "text/calendar"))) // very important
      // Create a Multipart
      val multipart = new MimeMultipart
      // Add part one
      multipart.addBodyPart(messageBodyPart)
      // Put parts in message
      message.setContent(multipart)
      Transport.send(message)
      System.out.println("Success")
    } catch {
      case me: MessagingException =>
        me.printStackTrace()
      case ex: Exception =>
        ex.printStackTrace()
    }
  }
}


