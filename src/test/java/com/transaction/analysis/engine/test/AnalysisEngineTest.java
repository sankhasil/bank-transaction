/**
 * 
 */
package com.transaction.analysis.engine.test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.transaction.analysis.engine.AnalysisEngine;
import com.transaction.analysis.model.input.BankLog;
import com.transaction.analysis.model.input.Transaction;
import com.transaction.analysis.model.output.Bank;
import com.transaction.analysis.model.output.DailyBalance;
import com.transaction.analysis.model.output.Statistics;

/**
 * @author Sankha
 *
 */
public class AnalysisEngineTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	public Random random = new Random();
	public AnalysisEngine analysisEngine = AnalysisEngine.getInstance();

	@Before
	public void setUpData() {
		Transaction transaction1 = new Transaction();
		transaction1.setBankCode("T001");
		transaction1.setBankName("TestBank");
		transaction1.setDate(LocalDateTime.now());
		transaction1.setLogFilePath("./T001.csv");
		transaction1.setRowNumber(1L);
		analysisEngine.setListOfTransaction(Arrays.asList(transaction1));

		List<BankLog> bankLogList = new ArrayList<>();
		for (int i = 1; i < 6; i++) {
			BankLog bankLog = new BankLog();
			bankLog.setCurrency("EUR");
			bankLog.setDate(LocalDateTime.now());
			bankLog.setRowNumber(1L);
			bankLog.setTransactionAmount(BigDecimal.valueOf(100.00 * random.nextInt(i)));
			bankLog.setTransactionCategory("category" +i);
			bankLog.setTransactionCurrency(Currency.getInstance(Locale.FRANCE));
			bankLog.setTransactionDestination("T001" + UUID.randomUUID().toString());
			bankLog.setTransactionSource("T00" + random.nextInt(i) + UUID.randomUUID().toString());
			bankLog.setTransactionId(UUID.randomUUID());
			bankLogList.add(bankLog);
		}
		analysisEngine.getBankLogsMap().put("T001", bankLogList);

	}

	@Test
	public void test_analyzeStatisticsData() {
		analysisEngine.analyzeStatisticsData();

		assertTrue("Should have more than one Data", analysisEngine.getStatisticMap().size() > 0);
		assertTrue("Should contain the bankCode as key", analysisEngine.getStatisticMap().containsKey("T001"));
		assertTrue("Should have a list of statistics data", analysisEngine.getStatisticMap().get("T001").size() > 0);
		
		Statistics stat = analysisEngine.getStatisticMap().get("T001").stream()
		.filter(sta -> sta.getCategory().equalsIgnoreCase("category" + 4)).findFirst()
		.orElse(null);
		assertTrue("Fourth Category have total amount greater than 0.00",
				stat.getTotalAmount().compareTo(BigDecimal.ZERO) > 0);
	}
	
	@Test
	public void test_analyzeDailyBalanceData() {
		analysisEngine.analyzeDailyBalanceData();

		assertTrue("Should have more than one Data", analysisEngine.getDailyBalanceMap().size() > 0);
		assertTrue("Should contain the bankCode as key", analysisEngine.getDailyBalanceMap().containsKey("T001"));
		assertTrue("Should have a list of statistics data", analysisEngine.getDailyBalanceMap().get("T001").size() > 0);
		
		DailyBalance dailyBalance = analysisEngine.getDailyBalanceMap().get("T001").stream()
		.filter(daily -> daily.getDate().equals(LocalDate.now())).max((comp1,comp2) -> comp1.getTotalIncomingAmount().compareTo(comp2.getTotalIncomingAmount())).orElse(null);
		assertTrue("Today's Transaction have total incoming amount greater than 0.00",
				dailyBalance.getTotalIncomingAmount().compareTo(BigDecimal.ZERO) > 0);
	}
	
	@Test
	public void test_analyzeBankData() {
		analysisEngine.analyzeBankData();

		assertTrue("Should have more than one Data", analysisEngine.getBanksList().size() > 0);
		Bank bankData = analysisEngine.getBanksList().get(0);
		assertTrue("First Bank data should be 'T001'",bankData.getBankCode().equalsIgnoreCase("T001"));
	}
}
