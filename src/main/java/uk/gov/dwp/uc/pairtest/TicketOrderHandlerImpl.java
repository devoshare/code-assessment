package uk.gov.dwp.uc.pairtest;

import org.junit.platform.engine.TestDescriptor;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;

public class TicketOrderHandlerImpl implements TicketOrderHandler {

    @Override
    public TicketOrder createTicketOrder(TicketTypeRequest[] ticketTypeRequests) throws InvalidPurchaseException {

        int adultTickets =
                getTicketsCountByType(TicketTypeRequest.Type.ADULT,
                        ticketTypeRequests);

        int childTickets = getTicketsCountByType(TicketTypeRequest.Type.CHILD
                , ticketTypeRequests);

        int infantTickets = getTicketsCountByType(TicketTypeRequest.Type.INFANT,
                ticketTypeRequests);

        return new TicketOrder( adultTickets, childTickets, infantTickets);
    }

    private int getTicketsCountByType(TicketTypeRequest.Type type,
                                      TicketTypeRequest... requests) {
        return Arrays.stream(requests)
                .filter(r -> r.getTicketType() == type)
                .mapToInt(TicketTypeRequest::getNoOfTickets)
                .reduce(0, Math::addExact);
    }



}
