package uk.gov.hmrc.api.models

case class NICredit(taxYear: Int, numberOfCredits: Int,
                    contributionCreditTypeCode: String, contributionCreditType: String,
                    class2Or3EarningsFactor: Double, class2NicAmount: Double,
                    class2Or3CreditStatus: String) {

  /*"taxYear": 2022,
    "numberOfCredits": 53,
    "contributionCreditTypeCode": "C2",
    "contributionCreditType": "CLASS 2 - NORMAL RATE",
    "class2Or3EarningsFactor": 99999999999999.98,
    "class2NicAmount": 99999999999999.98,
    "class2Or3CreditStatus": "NOT KNOWN/NOT APPLICABLE"*/

}
