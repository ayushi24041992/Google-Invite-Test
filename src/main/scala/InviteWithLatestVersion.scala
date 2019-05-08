import java.net.URI
import java.util.{Properties, UUID}

import javax.activation.DataHandler
import javax.mail._
import javax.mail.internet.{InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart}
import javax.mail.util.ByteArrayDataSource
import net.fortuna.ical4j.model.{Calendar, DateTime}
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property._
import net.fortuna.ical4j.model.parameter.CuType
import net.fortuna.ical4j.model.parameter.Rsvp
import net.fortuna.ical4j.model.parameter.PartStat

object InviteWithLatestVersion {
  def main(args: Array[String]): Unit = {
    try {
      val invite = new InviteWithLatestVersion
      invite.send()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}


class InviteWithLatestVersion {

  def send(): Unit = {
    try {
      val from = "hasijaayushi@gmail.com"
      val to = "ayushi.hasija@knoldus.in"
      val toAttendee = "sakshi.gupta@knoldus.in"
      val prop = new Properties
      prop.put("mail.smtp.auth", "true")
      prop.put("mail.smtp.starttls.enable", "true")
      prop.put("mail.smtp.host", "smtp.gmail.com")
      prop.put("mail.smtp.port", "587")
      val username = from
      val password = "Shasija24"
      val session = Session.getInstance(prop, new Authenticator() {
        override protected def getPasswordAuthentication = new PasswordAuthentication(username, password)
      })

      val message = new MimeMessage(session)
      message.addHeaderLine("method=REQUEST")
      message.addHeaderLine("charset=UTF-8")
      message.addHeaderLine("Content-Transfer-Encoding=7bit")
      message.addHeaderLine("component=VEVENT")
      message.setFrom(new InternetAddress(from))
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to))
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAttendee))
      message.setSubject("******Calendar Invite***************")

      val calendar = new Calendar
      calendar.getProperties.add(new ProdId("-//Google Inc//Google Calendar 70.9054//EN"))
      calendar.getProperties.add(Version.VERSION_2_0)
      calendar.getProperties.add(CalScale.GREGORIAN)
      calendar.getProperties.add(Method.REQUEST)
      calendar.getProperties.add(new TzId("Asia/Kolkatta"))

      val fromDate = new DateTime(1557302435000L)
      fromDate.setUtc(true)
      val toDate = new DateTime(1557303635000L)
      toDate.setUtc(true)
      val meeting = new VEvent(fromDate, toDate, "Booking Confirmation Invite")

      val organizer = new Organizer(URI.create("mailto:" + "hasijaayushi@gmail.com"))
      meeting.getProperties.add(organizer)
      meeting.getProperties.add(new Uid(UUID.randomUUID().toString))

      val location = new Location("bravos")
      meeting.getProperties.add(location)

      import net.fortuna.ical4j.model.property.Clazz
      import net.fortuna.ical4j.model.property.Priority
      meeting.getProperties.add(Priority.MEDIUM)
      meeting.getProperties.add(Clazz.PUBLIC)

      import net.fortuna.ical4j.model.parameter.Cn
      import net.fortuna.ical4j.model.property.Attendee
      import java.net.URI

      val dev1 = new Attendee(URI.create("mailto:" +"ayushi.hasija@knoldus.in"))
      dev1.getParameters.add(PartStat.NEEDS_ACTION)
      dev1.getParameters.add(Rsvp.TRUE)
      dev1.getParameters.add(CuType.INDIVIDUAL)
      dev1.getParameters.add(new Cn("Developer 1"))
      meeting.getProperties.add(dev1)

      val dev2 = new Attendee(URI.create("mailto:" +"sakshi.gupta@knoldus.in"))
      dev2.getParameters.add(PartStat.NEEDS_ACTION)
      dev2.getParameters.add(Rsvp.TRUE)
      dev2.getParameters.add(CuType.INDIVIDUAL)
      dev2.getParameters.add(new Cn("Developer 2"))
      meeting.getProperties.add(dev2)

   /*   val parameterList = new ParameterList
      parameterList.add(PartStat.NEEDS_ACTION)
      parameterList.add(Rsvp.TRUE)
      parameterList.add(CuType.INDIVIDUAL)
      meeting.getProperties().add(new Attendee(parameterList, URI.create("mailto:ayushi.hasija@knoldus.in")))
      meeting.getProperties().add(new Attendee(parameterList, URI.create("mailto:bhawna@knoldus.in")))
      meeting.getProperties().add(new Attendee(parameterList, URI.create("mailto:sakshihasija24@gmail.com")))*/
      meeting.getProperties().add(Status.VEVENT_CONFIRMED)
      meeting.getProperties().add(new Sequence(0))

      calendar.getComponents.add(meeting)

      println(calendar.getComponents)
      val messageBodyPart = new MimeBodyPart
      /*messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage")
      messageBodyPart.setHeader("Content-ID", "calendar_message")
      messageBodyPart.setHeader("Content-Disposition", "inline")
      */
      messageBodyPart.setContent(calendar.toString, "text/calendar")
      messageBodyPart.setHeader("charset", "UTF-8")
      messageBodyPart.setHeader("Content-Transfer-Encoding","7bit")
      messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(calendar.toString, "text/calendar;Content-Type= \"application/ics\";" +
        "Content-Disposition= \"attachment\";filename=\"invite.ics\";Content-Transfer-Encoding= \"base64\""))) // very important


      val multipart = new MimeMultipart
      multipart.addBodyPart(messageBodyPart)
      message.setContent(multipart)
      Transport.send(message)
    } catch {
      case me: MessagingException =>
        me.printStackTrace()
      case ex: Exception =>
        ex.printStackTrace()
    }
  }

}
