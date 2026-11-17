package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public interface TicketOrderHandler {

    public TicketOrder createTicketOrder (TicketTypeRequest[] ticketTypeRequest)
            throws InvalidPurchaseException;
}
