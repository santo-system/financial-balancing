package com.santosystem.financial.balancing.client

import com.santosystem.financial.balancing.client.dto.YahooFinanceQuoteResponse
import com.santosystem.financial.balancing.client.dto.YahooFinanceQuoteSumaryResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(value = "yahoo-finance-client", url = "https://query2.finance.yahoo.com/")
interface YahooFinanceClient {
    @GetMapping("/v7/finance/quote?symbols={symbol}.SA&region=BR&lang=pt-BR")
    fun getQuote(@PathVariable symbol: String): YahooFinanceQuoteResponse

    @GetMapping("/v11/finance/quoteSummary/{symbol}.SA?modules=assetProfile%2CsummaryProfile%2CsummaryDetail%2CesgScores%2Cprice%2CincomeStatementHistory%2CincomeStatementHistoryQuarterly%2CbalanceSheetHistory%2CbalanceSheetHistoryQuarterly%2CcashflowStatementHistory%2CcashflowStatementHistoryQuarterly%2CdefaultKeyStatistics%2CfinancialData%2CcalendarEvents%2CsecFilings%2CrecommendationTrend%2CupgradeDowngradeHistory%2CinstitutionOwnership%2CfundOwnership%2CmajorDirectHolders%2CmajorHoldersBreakdown%2CinsiderTransactions%2CinsiderHolders%2CnetSharePurchaseActivity%2Cearnings%2CearningsHistory%2CearningsTrend%2CindustryTrend%2CindexTrend%2CsectorTrend&region=BR&lang=pt-BR")
    fun getQuoteSummary(@PathVariable symbol: String): YahooFinanceQuoteSumaryResponse

}
