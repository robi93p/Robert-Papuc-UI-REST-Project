Feature: Homework RestAPI

  @test
  Scenario Outline: Validation of API functionality
    Given the DummmyAPI is ready to be executed
    When user performs a GET request to retrieve employees with age number higher than <value>
    And user adds new employee with age higher than <value>
    And user updates an employee
    And user checks the number of employees higher than <value>
    Then user deletes the employee
    Examples:
      | value |
      | 30    |