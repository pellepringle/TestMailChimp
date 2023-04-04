Feature: Creating account

  Scenario Outline:
    Given I open "<browser>"
    Given I enter "<email>"
    Given My username is "<length>" long
    Given I enter a "<password>"
    When I click sign up
    Then We "<attempt>" to create account
    Examples:
      | browser | email                        | length | password  | attempt |
      | chrome  | random                       | 10     | Admin123! | success |
      | edge    | pelle.johansson@gmailen.com  | 120    | Admin123! | fail    |
      | edge    | pelle.johansson11@gmail.com  | 13     | Admin123! | fail    |
      | chrome  | pelle.johdanssonen@gmail.com | 0      | Admin123! | fail    |

