package uk.gov.dwp.uc.pairtest;


public class TicketOrder {

    private int adultTickets;

    private int childTickets;

    private int infantTickets;

    TicketOrder (int adultTickets, int childTickets, int infantTickets) {
        this.adultTickets = adultTickets;
        this.childTickets = childTickets;
        this.infantTickets = infantTickets;
    }

    public int getTotalTickets () {
        return this.adultTickets + this.childTickets + this.infantTickets;
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
