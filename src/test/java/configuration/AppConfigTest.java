package configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    int maxAllowed = 25;
    int adultPrice = 25;
    int childPrice = 15;
    int infantPrice = 0;


    @Test
    @DisplayName("Should load max number of tickets from properties file")
    void shouldLoadMaxNumberOfTicketsFromPropertiesFile() {
        assertEquals(maxAllowed, AppConfig.getMAXTickets());
    }

    @Test
    @DisplayName("Should load cost of adult ticket")
    void shouldLoadAdultTicketPrice() {
        assertEquals(adultPrice, AppConfig.getAdultPrice());
    }

    @Test
    @DisplayName("Should load cost of child ticket")
    void shouldLoadChildTicketPrice() {
        assertEquals(childPrice, AppConfig.getChildPrice());
    }

    @Test
    @DisplayName("Should load cost of infant ticket")
    void shouldLoadInfantTicketPrice() {
        assertEquals(infantPrice, AppConfig.getInfantPrice());
    }

}