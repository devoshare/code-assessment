package uk.gov.dwp.uc.pairtest;

import configuration.AppConfig;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TicketServiceImplTest {

    @Mock
    TicketPaymentService ticketPaymentService;

    @Mock
    SeatReservationService seatReservationService;

    @InjectMocks
    private TicketServiceImpl ticketServiceImpl;

    @Mock
    private TicketOrderHandler ticketOrderHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should throw exception if accountId is negative")
    void shouldThrowExceptionIfAccountIdIsNegative() {
        TicketTypeRequest ticketTypeRequest =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 5);

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(-1L, ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should throw exception id accountId is zero")
    void shouldThrowExceptionIfAccountIdIsZero() {
        TicketTypeRequest ticketTypeRequest =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 5);

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(0L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should throw exception if the accountId is null")
    void shouldThrowExceptionIfTheAccountIdIsNull() {
        TicketTypeRequest ticketTypeRequest =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 5);

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(null,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should throw exception if the TicketTypeRequest is null")
    void shouldThrowExceptionIfTicketTypeRequestIsNull() {
        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(5372L, null));
        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should throw exception id the TicketTypeRequest is empty")
    void shouldThrowExceptionIfTheTicketTypeRequestIsEmpty() {
        TicketTypeRequest[] ticketTypeRequest =
                new TicketTypeRequest[]{};

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(null,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);

    }


    //Max Tickets
    @Test
    @DisplayName("Should throw exception if number of tickets is more than " +
            "max amount")
    void shouldThrowExceptionIfNumberOfTicketsIsMoreThanMaxAmount() {
        TicketTypeRequest[] ticketTypeRequest = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 10),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 10),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 8),

        };

        TicketOrder mockOrder = mock(TicketOrder.class);
        when(mockOrder.getAdultTickets()).thenReturn(10);
        when(mockOrder.getChildTickets()).thenReturn(10);
        when(mockOrder.getInfantTickets()).thenReturn(8);
        when(mockOrder.getTotalTickets()).thenReturn((26));
        when(ticketOrderHandler.createTicketOrder(any())).thenReturn(mockOrder);

        assertThrows(InvalidPurchaseException.class, () ->
                ticketServiceImpl.purchaseTickets(3628L, ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    //Child Only ticket or no adult check
    @Test
    @DisplayName("Should throw exception for child only tickets")
    void shouldThrowExceptionForChildOnlyTickets() {
        TicketTypeRequest[] ticketTypeRequest = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
        };

        TicketOrder mockOrder = mock(TicketOrder.class);
        when(mockOrder.getAdultTickets()).thenReturn(0);
        when(mockOrder.getChildTickets()).thenReturn(2);
        when(mockOrder.getInfantTickets()).thenReturn(0);
        when(mockOrder.getTotalTickets()).thenReturn((2));
        when(ticketOrderHandler.createTicketOrder(any())).thenReturn(mockOrder);

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(6738L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    //Infant Only Tickets or no adult check
    @Test
    @DisplayName("Should throw exception for Infant only tickets")
    void shouldThrowExceptionForInfantOnlyTickets() {
        TicketTypeRequest[] ticketTypeRequest = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2),
        };

        TicketOrder mockOrder = mock(TicketOrder.class);
        when(mockOrder.getAdultTickets()).thenReturn(0);
        when(mockOrder.getChildTickets()).thenReturn(0);
        when(mockOrder.getInfantTickets()).thenReturn(2);
        when(mockOrder.getTotalTickets()).thenReturn((2));
        when(ticketOrderHandler.createTicketOrder(any())).thenReturn(mockOrder);

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(6738L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    //Child and Infant Only Tickets or no adult check
    @Test
    @DisplayName("Should throw exception for Child and Infant only tickets")
    void shouldThrowExceptionForChildAndInfantOnlyTickets() {
        TicketTypeRequest[] ticketTypeRequest = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 5)
        };

        TicketOrder mockOrder = mock(TicketOrder.class);
        when(mockOrder.getAdultTickets()).thenReturn(0);
        when(mockOrder.getChildTickets()).thenReturn(2);
        when(mockOrder.getInfantTickets()).thenReturn(5);
        when(mockOrder.getTotalTickets()).thenReturn((7));
        when(ticketOrderHandler.createTicketOrder(any())).thenReturn(mockOrder);

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(6738L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    //Check for business rule where 2 infants are allowed per adult as infant
    // will sit on lap of the adult
    @Test
    @DisplayName("Should throw exception if there are more than two infants " +
            "per Adult")
    void shouldThrowExceptionLapRule() {
        TicketTypeRequest[] ticketTypeRequest = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 5),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 5)
        };


        TicketOrder mockOrder = mock(TicketOrder.class);
        when(mockOrder.getAdultTickets()).thenReturn(2);
        when(mockOrder.getChildTickets()).thenReturn(5);
        when(mockOrder.getInfantTickets()).thenReturn(5);
        when(mockOrder.getTotalTickets()).thenReturn((12));
        when(ticketOrderHandler.createTicketOrder(any())).thenReturn(mockOrder);

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(6738L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    //Added 3 test methods to improve test coverage
    @Test
    @DisplayName("Should throw exception id any part of TicketTypeRequest " +
            "contains null elements")
    void shouldThrowExceptionIfTheTicketTypeRequestContainsNullElements() {
        TicketTypeRequest[] ticketTypeRequest = new TicketTypeRequest[]{
                new TicketTypeRequest(null, 2),
        };

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(563L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should throw exception is negetive number of tickets " +
            "requested")
    void shouldThrowExceptionIsNegativeNumberOfTicketsRequested() {
        TicketTypeRequest[] ticketTypeRequest = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, -7),
        };

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(563L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);

    }

    @Test
    @DisplayName("Should throw exception is TicketTypeRequest contains null " +
            "elements")
    void shouldThrowExceptionIsTicketTypeRequestContainsNullElements() {
        TicketTypeRequest[] ticketTypeRequest = new TicketTypeRequest[]{
                null,

        };

        assertThrows(InvalidPurchaseException.class,
                () -> ticketServiceImpl.purchaseTickets(563L,
                        ticketTypeRequest));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }



    //Happy Path Testing
    @Test
    @DisplayName("Should purchase ticket and reserve seats ")
    void shouldPurchaseTicketAndReserveSeats() {

        Long VALID_ACCOUNT_ID = 736L;
        TicketTypeRequest[] ticketTypeRequest = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 5),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2)
        };

        int expectedPrice =
                (2 * AppConfig.getAdultPrice()) + (5 * AppConfig.getChildPrice())
                        + (2 * AppConfig.getInfantPrice());

        int expectedSeats = 7;

        TicketOrder mockOrder = mock(TicketOrder.class);
        when(mockOrder.getAdultTickets()).thenReturn(2);
        when(mockOrder.getChildTickets()).thenReturn(5);
        when(mockOrder.getInfantTickets()).thenReturn(2);
        when(mockOrder.getTotalTickets()).thenReturn((9));
        when(mockOrder.calculatePrice()).thenReturn(expectedPrice);
        when(mockOrder.calculateSeats()).thenReturn(expectedSeats);
        when(ticketOrderHandler.createTicketOrder(any())).thenReturn(mockOrder);

        ticketServiceImpl.purchaseTickets(VALID_ACCOUNT_ID, ticketTypeRequest);

        // Only test orchestration: ensure services called with expected values
        verify(ticketPaymentService).makePayment(eq(VALID_ACCOUNT_ID),
                eq(expectedPrice));

        verify(seatReservationService).reserveSeat(eq(VALID_ACCOUNT_ID),
                eq(expectedSeats));
    }

}
