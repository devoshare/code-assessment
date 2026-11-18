package configuration;

import uk.gov.dwp.uc.pairtest.TicketServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//Application properties
public final class AppConfig {


    private static final Properties properties = new Properties();

    private static final String APPLICATION_PROPERTIES = "application" +
            ".properties";

     static {
         try (InputStream inputStream = TicketServiceImpl.class.getClassLoader()
                 .getResourceAsStream(APPLICATION_PROPERTIES)) {
             if (inputStream == null) {
                 throw new IllegalStateException(APPLICATION_PROPERTIES + " was not found on the classpath");
             }
             properties.load(inputStream);
         } catch (IOException exception) {
             throw new IllegalStateException("Unable to load " + APPLICATION_PROPERTIES, exception);
         }

     }


    public static int getMAXTickets  () {
        return Integer.parseInt(properties.getProperty("ticket.max.allowed"));
    }

    public static int getAdultPrice () {
        return Integer.parseInt(properties.getProperty("ticket.price.adult"));
    }

    public static int getChildPrice  () {
        return Integer.parseInt(properties.getProperty("ticket.price.child"));
    }

    public static int getInfantPrice () {
        return Integer.parseInt(properties.getProperty("ticket.price.infant"));
    }

}
