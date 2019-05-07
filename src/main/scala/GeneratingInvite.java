

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

public class GeneratingInvite {

    public static void main(String args[]) throws Exception{
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        // initialise as an all-day event..
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 25);
        VEvent christmas = new VEvent(new Date(cal.getTime()), "Christmas Day");


     /*   // Generate a UID for the event..
        UidGenerator ug = new UidGenerator("RAP");
        christmas.getProperties().add(ug.generateUid());*/
        Status state = new Status("NEEDS-ACTION");
        christmas.getProperties().add(state);
        calendar.getComponents().add(christmas);
        System.out.println(calendar);
    }
}