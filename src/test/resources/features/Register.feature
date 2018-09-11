Feature: Register
  To test the functionality of registration

  Scenario: Register a new user
    Given I am on Home Page of "https://www.etsy.com/"
    And I Dismiss cookies popup
    When I enter the email and password to register a new user
    Then the user is registered successfully

  Scenario Outline: Search for an item
    Given I am on Home Page of "https://www.etsy.com/"
    And I Dismiss cookies popup
    When I search for <item>
    And I sort by <sortType>
    And I add the most expensive item to the cart
    And I search for <second_item>
    And I add the item on the cart
    Then the cart contains 2 items
    Examples:
    |item      | sortType     | second_item   |
    |Sketchbook| Lowest Price | turntable mat |