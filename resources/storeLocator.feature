Feature: Testing the features of sears shopping website

  Scenario: Locating a store
    Given I navigate to sears website
    When I click on store locator link
    And I enter city and state
      | City        | State |
      | bloomington | IL    |
    And I click on search
    Then I should be able to see a store located in my city and state
    