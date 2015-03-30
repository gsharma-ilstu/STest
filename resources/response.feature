Feature: Testing the features of sears shopping website

  Scenario: Check website response time
    Given I navigate to sears website
    Then the page should be loaded within 6 seconds
