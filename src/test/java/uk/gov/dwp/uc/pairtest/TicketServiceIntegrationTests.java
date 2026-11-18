package uk.gov.dwp.uc.pairtest;

import configuration.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Integration tests to test the ticket service end to end
 */
public class TicketServiceIntegrationTests {

    private static final Long VALID_ACCOUNT_ID = 46783L;

    @Mock
    private TicketPaymentService ticketPaymentService;

    @Mock
    private SeatReservationService seatReservationService;

    private TicketOrderHandler ticketOrderHandler;
    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ticketOrderHandler = new TicketOrderHandlerImpl();
        ticketService = new TicketServiceImpl(seatReservationService,
                ticketPaymentService, ticketOrderHandler);
    }

    @Test
    void integrationTest() throws Exception {
        TicketTypeRequest[] ticketTypeRequests = new TicketTypeRequest[]{
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 6),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 5),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 8),
        };

        int expectedTotalSeats = 11;
        int expectedTotalCosts =
                (6 * AppConfig.getAdultPrice()) + (5 * AppConfig.getChildPrice())
                        + (8 * AppConfig.getInfantPrice());

        ticketService.purchaseTickets(VALID_ACCOUNT_ID, ticketTypeRequests);

        verify (ticketPaymentService).makePayment(eq(VALID_ACCOUNT_ID),
                eq(expectedTotalCosts));
        verify (seatReservationService).reserveSeat(eq(VALID_ACCOUNT_ID),
                eq(expectedTotalSeats));
    }

}
