# Cinema Ticket Service Exercise

A clean-architecture, test-driven Java implementation for the cinema ticket service exercise.

## Development Approach & TDD
- Followed strict TDD principles (**Red -> Green -> Refactor**).
- Captured steps in the **Exercise.md** file.

## Business Rules, Validations & Tests

**Validations**
- Validation criteria are implemented in the `TicketOrderHandlerImpl` / service layer.
  - **Tests:** `TestServiceImplTest`
  - `AccountId` must be a positive number (zero, negative, and null are rejected).
  - `TicketTypeRequest` cannot be null, empty, or contain a negative number of tickets.

**Business Rules**
- Business rules are implemented in the service layer.
  - **Tests:** `TestServiceImplTest`
  - Cannot request more than the maximum number of tickets (e.g., 25).
  - Infant tickets cannot be requested without Adult tickets.
  - Child tickets cannot be requested without Adult tickets.

**Calculations**
- Calculations have been implemented in the `TicketOrder` / calculation classes.
  - **Tests:** `TicketOrderTest`
  - Method for calculating total number of seats.
  - Method for calculating number of seats to be allocated.
  - Method for calculating the price for all the tickets.

**Configuration**
- Externalized properties in the `application.properties` file.
  - **Tests:** `AppConfigTest`
  - Reads configuration properties and makes them available for the application.

**Integration & Happy Path Tests**
- **Happy Path Tests:** Available in `TestServiceImplTest`
- **Integration Tests:** Available in `TestServiceIntegrationTests`

## Assumptions & Constraints
- Each infant sits on an adult's lap. Assuming a 2:2 ratio, the number of 
  infants  cannot exceed adult tickets, and for every adult, there can be a  
  maximum of 2 infants. Infants do not occupy a dedicated seat.

# Environment
- **Language:** Java 21
- **Build Tool:** Maven 3.9.12
- **Dev OS:** Ubuntu 26.04

## Build and Dependency Notes
- Added plugin `maven-surefire-plugin` (3.2.5) for running tests from the  
  command line using `mvn clean test` (tests will also run in IntelliJ 
  without  the plugin).
- Added `jacoco-maven-plugin` (0.8.12) for running test coverage reports.

### Running Tests
To execute the test suite and collect coverage metrics:
```bash
mvn clean test
```

### Running Jacoco Reports
To execute the test suite and collect coverage metrics:
```bash
mvn jacoco:report
```

