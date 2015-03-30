Feature: Testing the features of sears shopping website

  Scenario: Ability to compare two items
    Given I navigate to sears website
    When I select item 1 to compare
    And I select item 2 to compare
    And I click on compare now
    Then I should be able to see the details of both the items
