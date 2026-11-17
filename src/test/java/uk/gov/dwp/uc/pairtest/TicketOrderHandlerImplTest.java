package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;

class TicketOrderHandlerImplTest {

    private TicketOrderHandlerImpl ticketOrderHandler;

    @BeforeEach
    void setUp() {
        // Since this class has no dependencies, direct instantiation is correct here
        ticketOrderHandler = new TicketOrderHandlerImpl();
    }
    @Test
    @DisplayName("Should correctly aggregate single ticket type request")
    void shouldCorrectlyAggregateSingleTicketType() throws InvalidPurchaseException {
        TicketTypeRequest[] requests = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3)
        };

        TicketOrder order = ticketOrderHandler.createTicketOrder(requests);

        assertNotNull(order, "TicketOrder should not be null");
        assertEquals(3, order.getAdultTickets());
        assertEquals(0, order.getChildTickets());
        assertEquals(0, order.getInfantTickets());
    }

    @Test
    @DisplayName("Should correctly aggregate mixed ticket type requests (Adult, Child, Infant)")
    void shouldCorrectlyAggregateMixedTicketTypes() throws InvalidPurchaseException {
        TicketTypeRequest[] requests = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
        };

        TicketOrder order = ticketOrderHandler.createTicketOrder(requests);

        assertNotNull(order);
        assertEquals(2, order.getAdultTickets());
        assertEquals(4, order.getChildTickets());
        assertEquals(1, order.getInfantTickets());
    }

    @Test
    @DisplayName("Should correctly sum multiple requests for the same ticket type")
    void shouldSumMultipleRequestsOfSameType() throws InvalidPurchaseException {
        TicketTypeRequest[] requests = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3),
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)
        };

        TicketOrder order = ticketOrderHandler.createTicketOrder(requests);

        assertNotNull(order);
        assertEquals(1, order.getAdultTickets());
        assertEquals(5, order.getChildTickets()); // 2 + 3
        assertEquals(0, order.getInfantTickets());
    }

    @Test
    @DisplayName("Should handle zero quantities or missing ticket types gracefully")
    void shouldHandleMissingTypesGracefully() throws InvalidPurchaseException {
        TicketTypeRequest[] requests = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2)
        };

        TicketOrder order = ticketOrderHandler.createTicketOrder(requests);

        assertNotNull(order);
        assertEquals(0, order.getAdultTickets());
        assertEquals(0, order.getChildTickets());
        assertEquals(2, order.getInfantTickets());
    }

}