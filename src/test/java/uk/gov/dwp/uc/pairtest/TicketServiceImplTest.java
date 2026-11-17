package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;


class TicketServiceImplTest {

    @Mock
    TicketPaymentService ticketPaymentService;

    @Mock
    SeatReservationService seatReservationService;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        try {
            autoCloseable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Should throw exception if accountId is negative")
    void shouldThrowExceptionIfAccountIdIsNegative() {
        TicketTypeRequest ticketTypeRequest =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 5);

        assertThrows(InvalidPurchaseException.class,
                () -> new TicketServiceImpl().purchaseTickets(-1L, ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should throw exception id accountId is zero")
    void shouldThrowExceptionIfAccountIdIsZero() {
        TicketTypeRequest ticketTypeRequest =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 5);

        assertThrows(InvalidPurchaseException.class,
                () -> new TicketServiceImpl().purchaseTickets(0L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should throw exception if the accountId is null")
    void shouldThrowExceptionIfTheAccountIdIsNull() {
        TicketTypeRequest ticketTypeRequest =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 5);

        assertThrows(InvalidPurchaseException.class,
                () -> new TicketServiceImpl().purchaseTickets(null,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should throw exception if the TicketTypeRequest is null")
    void shouldThrowExceptionIfTicketTypeRequestIsNull() {
        assertThrows(InvalidPurchaseException.class,
                () -> new TicketServiceImpl().purchaseTickets (5372L, null));
        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should throw exception id the TicketTypeRequest is empty")
    void shouldThrowExceptionIfTheTicketTypeRequestIsEmpty() {
        TicketTypeRequest[] ticketTypeRequest =
                new TicketTypeRequest[]{};

        assertThrows(InvalidPurchaseException.class,
                () -> new TicketServiceImpl().purchaseTickets(null,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);

    }


    //Max Tickets
    @Test
    @DisplayName("Should throw exception if number of tickets is more than " +
            "max amount")
    void shouldThrowExceptionIfNumberOfTicketsIsMoreThanMaxAmount() {
        TicketTypeRequest[] ticketTypeRequest =  new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 23),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 5)
        };

        assertThrows(InvalidPurchaseException.class,
                () -> new TicketServiceImpl().purchaseTickets(6738L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    //Child Only ticket or no adult check
    @Test
    @DisplayName("Should throw exception for child only tickets")
    void shouldThrowExceptionForChildOnlyTickets() {
        TicketTypeRequest[] ticketTypeRequest =  new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
        };

        assertThrows(InvalidPurchaseException.class,
                () -> new TicketServiceImpl().purchaseTickets(6738L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    //Infant Only Tickets or no adult check
    @Test
    @DisplayName("Should throw exception for Infant only tickets")
    void shouldThrowExceptionForInfantOnlyTickets() {
        TicketTypeRequest[] ticketTypeRequest =  new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2),
        };

        assertThrows(InvalidPurchaseException.class,
                () -> new TicketServiceImpl().purchaseTickets(6738L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    //Child and Infant Only Tickets or no adult check
    @Test
    @DisplayName("Should throw exception for Child and Infant only tickets")
    void shouldThrowExceptionForChildAndInfantOnlyTickets() {
        TicketTypeRequest[] ticketTypeRequest =  new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 5)
        };

        assertThrows(InvalidPurchaseException.class,
                () -> new TicketServiceImpl().purchaseTickets(6738L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    //Check for business rule where 2 infants are allowed per adult as infant
    // will sit on lap of the adult
    @Test
    @DisplayName("Should throw exception if there are more than two infants " +
            "per Adult")
    void shouldThrowExceptionLapRule () {
        TicketTypeRequest[] ticketTypeRequest =  new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 5),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 5)
        };
        assertThrows(InvalidPurchaseException.class,
                () -> new TicketServiceImpl().purchaseTickets(6738L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

}
