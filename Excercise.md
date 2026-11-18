# Steps followed to complete the coding assignment 
* Implemented as a final class with final fields to enforce immutability 
  while preserving the required class signature. 
  * Typically would have implemented this as a modern Java record, but kept 
    it as a traditional class to strictly comply with the exercise rules.
* Tests
  * Writing tests for accountId being null, zero or negative 
  * Writing tests for validating TicketTypeRequest
    * Test for null 
    * Test for empty TicketTypeRequest 
    * Test for max tickets i.e > 25
    * Test for child only tickets - no adult 
    * Test for infant only tickets - no adult
    * Test for child + infant only tickets - no adult
    * Test for business rule 1xAdult per 2xInfant (Max)
* Implementation
    * Implemented validate accountId method
    * Implemented validateTicketType method to validate the ticket type
    * Implemented skeleton for TicketOrder, TicketOrderHandler and its 
      implementation
* Refactor
  * Refactored tests to help with  testing the business logic
* Tests
  * Tests for TicketOrderHandler for testing aggregation logic 
* Implementation
  * Implement the creteTicketOrder, and getTicketCount methods
* Refactor
  * Refactored the tests to enable mocking of the object
  * Refactored the implementation methods so that tests run green.
* Tests
  * Tests for AppConfig that will handle the externalisation of properties
* Implementation
  * Implementation the AppCOnfig class for handling properties
* Tests
  * Tests for SeatCalculations and PaymentAmount in the TicketOrder class
* Implementation
  * Implemented calculateSeats and calculatePrice in the TicketOrder class
* Tests
  * Added integration tests to run tests end to end in the 
    TicketServiceIntegrationTests 
* Implementation
  * Added the call to the paymentService and seatBooking service in the 
    TicketServiceImpl class
* Re-Ran all tests from IDE and command-line 
* Ran the jacoco report 
* Added 3 more tests into TestServiceImpl to improve test coverage after 
  jacoco report
  