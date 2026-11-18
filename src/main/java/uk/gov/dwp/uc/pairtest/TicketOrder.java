package uk.gov.dwp.uc.pairtest;


public class TicketOrder {

    private final int adultTickets;

    private final int childTickets;

    private final int infantTickets;

    TicketOrder (int adultTickets, int childTickets, int infantTickets) {
        this.adultTickets = adultTickets;
        this.childTickets = childTickets;
        this.infantTickets = infantTickets;
    }

    public int getTotalTickets () {
        return this.adultTickets + this.childTickets + this.infantTickets;
    }

    //calculate seats
    public int calculateSeats () {
        return 0;
    }

    //calculate price
    public int calculatePrice () {
        return 0;
    }

    public int getAdultTickets() {
        return adultTickets;
    }

    public int getChildTickets() {
        return childTickets;
    }

    public int getInfantTickets() {
        return infantTickets;
    }
}
