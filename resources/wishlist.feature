Feature: Testing the features of sears shopping website

  Scenario: Adding an item to wishlist
    Given I navigate to sears website
    When I am logged in
      | Email                 | Password    |
      | selenium501@gmail.com | selenium501 |
    And I add an item to wishlist
    Then I should be able to see that item in my profile
