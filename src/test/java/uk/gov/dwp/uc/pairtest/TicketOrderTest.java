package uk.gov.dwp.uc.pairtest;

import configuration.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TicketOrderTest {


    @Test
    void getTotalTickets() {
        int adultTickets = 10;
        int childTickets = 20;
        int infantTickets = 30;
        int totalTickets = 60;
        assertEquals(60, new TicketOrder(adultTickets, childTickets,
                infantTickets).getTotalTickets());
    }

    @Test
    void calculateSeats() {
        int adultTickets = 10;
        int childTickets = 5;
        int infantTickets = 5;
        int seatAllocated = 15;
        assertEquals(seatAllocated, new TicketOrder(adultTickets,
                childTickets, infantTickets).calculateSeats());
    }

    @Test
    void calculatePrice() {
        int adultTickets = 10;
        int childTickets = 5;
        int infantTickets = 5;
        int ticketPrice = adultTickets * AppConfig.getAdultPrice()
                + childTickets * AppConfig.getChildPrice() +
                infantTickets * AppConfig.getInfantPrice();
        assertEquals(ticketPrice, new TicketOrder(adultTickets, childTickets,
                infantTickets).calculatePrice());
    }


}