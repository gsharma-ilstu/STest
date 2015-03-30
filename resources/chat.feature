Feature: Testing the features of sears shopping website

  Background: 
    Given I navigate to sears website

  Scenario: Ability to chat with customer support
    When I click on the chat link
    And I enter name and email id
      | Name     | Email                 |
      | Selenium | selenium501@gmail.com |
    And I click on start chat
    Then I should be able to chat with the customer support and get a response
