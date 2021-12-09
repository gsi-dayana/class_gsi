#Author: jose.espinosa@generalsoftwareinc.com and dayana@generalsoftwareinc.com

Feature: Login into Goheavy system
  As a Goheavy user
  I want to login into Goheavy system
  So that I can manage all the drivers with their vehicle to be able to makes deliveries


  Scenario Outline: Allowing and redirecting user according to its access
    Given The unauthenticated GoHeavy User is in the Login view
    When User insert email "<emails>" and password "<passwords>"
    And User clicks on the "Sign in" button
    Then The system allows the user access to the system
    And Sytem redirects to "<views>" view

    Examples: 
      | emails             | passwords | views             |
      | novanick@gmail.com | @User123  | Dashboard         |
      | evakings@gmail.com | @User123  | Drivers List      |
      | piper95@gmail.com  | @User123  | Fleet Owners List |

  Scenario Outline: Leave the email and/or password empty
    Given The unauthenticated GoHeavy User is in the Login view
    When At least one mandatory data "<email>" or "<password>" is not inserted
    And User clicks on the "Sign in" button
    Then The system displays an error messages below each field indicating they are mandatory: "<message>"

    Examples:
      | email             | password | message                |
      |                   | @User123 | This field is required |
      | piper95@gmail.com |          | This field is required |
      |                   |          | This field is required |

  Scenario Outline: Insert an invalid email and/or password
    Given The unauthenticated GoHeavy User is in the Login view
    When Inserts an "<email>" or "<password>" that is not registered in the system As customer OR does not match
    And User clicks on the "Sign in" button
    Then The system displays an error messages in a popup window in the upper right side of the view: "<message>"

    Examples:
      | email             | password | message                                                                                             |
      | random1@gmail.com | random1  | The email or password you entered is incorrect. If you are not registered please create an account. |
      | random2@gmail.com | random2  | The email or password you entered is incorrect. If you are not registered please create an account. |
