Feature: Testing the features of sears shopping website

  Scenario Outline: Check the search functionality
    Given I navigate to sears website
    When I enter an <Item>
    And I click on search button
    Then I should be able to see the description of the <Item>

    Examples: 
      | Item                                |
      | Apple Macbook A1181                 |
      | Craftsman 16 oz. Curved Claw Hammer |
