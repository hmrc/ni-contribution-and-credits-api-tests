Feature: Valid BSP API End Point
  Scenario: Verify the Success Status Code
    Given User has BSP API end point url
    When send the GET request
    Then User should get the success status code
