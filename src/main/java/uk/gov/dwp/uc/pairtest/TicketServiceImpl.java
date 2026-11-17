package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;
import java.util.Objects;

public class TicketServiceImpl implements TicketService {

    private SeatReservationService seatReservationService;

    private TicketPaymentService ticketPaymentService;

    private TicketOrderHandler ticketOrderHandler;

    public TicketServiceImpl(SeatReservationService seatReservationService,
                             TicketPaymentService ticketPaymentService,
                             TicketOrderHandler ticketOrderHandler) {
        this.seatReservationService = seatReservationService;
        this.ticketPaymentService = ticketPaymentService;
        this.ticketOrderHandler = ticketOrderHandler;
    }

    /**
     * Should only have private methods other than the one below.
     */

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests)
            throws InvalidPurchaseException {
        validateAccountId(accountId);
        validateTicketType(ticketTypeRequests);
        TicketOrder ticketOrder = ticketOrderHandler.createTicketOrder(ticketTypeRequests);
        validateBusinessRules(ticketOrder);
    }

    //implementing validation for accountId
    private void validateAccountId(Long accountId) throws InvalidPurchaseException {
        if ((accountId == null) || (accountId <= 0)) {
            throw new InvalidPurchaseException("AccountID is invalid");
        }
    }


    //implementing the validation for TicketTypeRequest
    private void validateTicketType(TicketTypeRequest[] ticketTypeRequest)
            throws InvalidPurchaseException {
        if (ticketTypeRequest == null || ticketTypeRequest.length == 0) {
            throw new InvalidPurchaseException("TicketTypeRequest  is " +
                    "empty");
        }

        // Check for null elements
        if (Arrays.stream(ticketTypeRequest).anyMatch(Objects::isNull)) {
            throw new InvalidPurchaseException("TicketTypeRequest is null");
        }

        // Check for null ticket type inside a request
        if (Arrays.stream(ticketTypeRequest).anyMatch(r ->
                r.getTicketType() == null)) {
            throw new InvalidPurchaseException("TicketTypeRequest contains  " +
                    "null requests");
        }

        // Check for negative ticket requests
        if (Arrays.stream(ticketTypeRequest).anyMatch(
                request -> request.getNoOfTickets() < 0)) {
            throw new InvalidPurchaseException("Number of requested tickets " +
                    "is negative");
        }
    }

    //check the business rules
    private void validateBusinessRules(TicketOrder ticketOrder) {
        if (ticketOrder.getTotalTickets() > 25) {
            throw new InvalidPurchaseException("Maximum 25 tickets allowed  " +
                    "per purchase");
        }

        if (ticketOrder.getAdultTickets() == 0 &&
                (ticketOrder.getChildTickets() > 0 || ticketOrder.getInfantTickets() > 0)) {
            throw new InvalidPurchaseException("Child and infant tickets " +
                    "require at least one adult");
        }

        int requiredAdults = (ticketOrder.getInfantTickets() + 1) / 2;

        if (ticketOrder.getAdultTickets() < requiredAdults) {
            throw new InvalidPurchaseException("At least 1 adult is required " +
                    "for  every 2 infants.");
        }

    }

}
