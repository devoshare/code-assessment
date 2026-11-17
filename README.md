# Cinema ticket exercise 
A Java implementation for the cinema ticket service exercise. 

## Development approach and TDD
Followed strict TDD principles **Red->Green->Refactor** during the exercise
Captured steps in the **Excercise.md** file

## Business Rules and Validations
**Validations**
* AccountId has to be a positive number - zero, negative and null are rejected
* TicketTypeRest cannot be null, empty or negative number of tickets

**Business Rules**
* Cannot request for more than max number of tickets i.e 25
* Infant tickets cant be requested without Adult tickets
* Child tickets cant be requested without Adult tickets

**Assumptions**
* Each Infant sits on Adult's lap, assuming 1:2, so number of infant cannot
  be more than Adult tickets; and for every adult there can be only 2 infants 
  as infants sits on adults laps

# Environment 
* Java 21
* Maven 3.9.12
* Dev OS: Ubuntu 26.04

## Build and dependency notes
* Added plug in 'maven-surefire-plugin' (3.2.5) for running the tests from
  cmd-line using 'mvn clean test', tests will run in the IDE "IntelliJ"
  without the plugin as well.
* Added 'jacoco plugin' (0.8.12) for running the test coverage reports, the 
  report can be produced by running 

### Running Tests
To execute the test suite and collect coverage metrics:
```bash
mvn clean test
````

### Running Jacoco Reports
To execute the test suite and collect coverage metrics:
```bash
mvn jacoco:report
```

