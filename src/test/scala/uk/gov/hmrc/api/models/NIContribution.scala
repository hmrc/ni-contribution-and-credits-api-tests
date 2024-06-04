package uk.gov.hmrc.api.models

case class NIContribution(taxYear: Int ,contributionCategoryLetter: String,
                          contributionCategory: String, totalContribution: Double,
                          primaryContribution: Double,  class1ContributionStatus:String,
                          primaryPaidEarnings:Double)  {

  /*taxYear: 2022,
  "contributionCategoryLetter": "s",
  "contributionCategory": "(NONE)",
  "totalContribution": 99999999999999.98,
  "primaryContribution": 99999999999999.98,
  "class1ContributionStatus": "COMPLIANCE & YIELD INCOMPLETE",
  "primaryPaidEarnings": 99999999999999.98*/

}
