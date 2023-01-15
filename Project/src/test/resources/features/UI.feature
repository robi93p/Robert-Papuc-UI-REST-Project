Feature: Homework UI

  @test
  Scenario: Validation of Cart functionality
    Given user navigate to website
    When user close modal
    And user add 2 products to Cart
    Then user remove 3 product from the Cart
