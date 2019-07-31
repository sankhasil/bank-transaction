/**
 * 
 */
package com.transaction.analysis.engine;

import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.transaction.analysis.model.input.BankLog;
import com.transaction.analysis.model.input.Transaction;
import com.transaction.analysis.model.output.Bank;
import com.transaction.analysis.model.output.DailyBalance;
import com.transaction.analysis.model.output.Statistics;

/**
 * @author Sankha
 *
 */
public class AnalysisEngine {

	private static AnalysisEngine INSTANCE;
	private List<Transaction> listOfTransaction;
	private Map<String, List<BankLog>> bankLogsMap = new HashMap<>();

	private List<Bank> banksList;
	private Map<String, List<DailyBalance>> dailyBalanceMap = new HashMap<>();
	private Map<String, List<Statistics>> statisticMap = new HashMap<>();

	private AnalysisEngine() {
	}

	public static AnalysisEngine getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AnalysisEngine();
		}

		return INSTANCE;
	}

	public List<Transaction> getListOfTransaction() {
		return listOfTransaction;
	}

	public void setListOfTransaction(List<Transaction> listOfTransaction) {
		this.listOfTransaction = listOfTransaction;
	}

	public List<Bank> getBanksList() {
		return banksList;
	}

	public Map<String, List<DailyBalance>> getDailyBalanceMap() {
		return dailyBalanceMap;
	}

	public Map<String, List<Statistics>> getStatisticMap() {
		return statisticMap;
	}

	public void analyzeBankData() {
		this.banksList = this.listOfTransaction.stream().map(trans -> {
			Bank bank = new Bank();
			bank.setBankCode(trans.getBankCode());
			bank.setBankName(trans.getBankName());
			return bank;
		}).collect(Collectors.toList());

		Collections.sort(this.banksList);
	}

	public void analyzeDailyBalanceData() {
		if (this.bankLogsMap.size() > 0) {
			for (String bankCode : this.bankLogsMap.keySet()) {
				Map<LocalDateTime, Double> outgoingAmountTotalByDate = this.bankLogsMap.get(bankCode).stream()
						.collect(Collectors.groupingBy(BankLog::getDate, Collectors.summingDouble(bankLog -> {
							if (bankLog.getTransactionSource().startsWith(bankCode))
								return bankLog.getTransactionAmount().doubleValue();
							return 0;
						})));
				
				Map<LocalDateTime, Double> incomingAmountTotalByDate = this.bankLogsMap.get(bankCode).stream()
						.collect(Collectors.groupingBy(BankLog::getDate, Collectors.summingDouble(bankLog -> {
							if (bankLog.getTransactionDestination().startsWith(bankCode))
								return bankLog.getTransactionAmount().doubleValue();
							return 0;
						})));
				
				List<DailyBalance> dailyBalanceList = new ArrayList<>();
				for(LocalDateTime dateTime : incomingAmountTotalByDate.keySet()) {
					DailyBalance dailyBalance = new DailyBalance();
					dailyBalance.setCurrency("EUR");
					dailyBalance.setDate(dateTime.toLocalDate());
					dailyBalance.setIncomingCount(new Long(incomingAmountTotalByDate.size()));
					dailyBalance.setTotalIncomingAmount(BigDecimal.valueOf(incomingAmountTotalByDate.get(dateTime)));
					dailyBalance.setOutgoingCount(new Long(outgoingAmountTotalByDate.size()));
					dailyBalance.setTotalOutgoingAmount(BigDecimal.valueOf(outgoingAmountTotalByDate.get(dateTime)));
					dailyBalanceList.add(dailyBalance);
				}
				this.dailyBalanceMap.put(bankCode, dailyBalanceList);
			}
		}
	}

	public void analyzeStatisticsData() {
		if (this.bankLogsMap.size() > 0) {

			for (String bankCode : this.bankLogsMap.keySet()) {
				Map<String, Long> transactionCount = this.bankLogsMap.get(bankCode).stream()
						.collect(Collectors.groupingBy(BankLog::getTransactionCategory, Collectors.counting()));

				Map<String, Double> transactionTotal = this.bankLogsMap.get(bankCode).stream()
						.collect(Collectors.groupingBy(BankLog::getTransactionCategory,
								Collectors.summingDouble(bankLog -> bankLog.getTransactionAmount().doubleValue())));
				List<Statistics> statisticsList = new ArrayList<>();
				for (String category : transactionCount.keySet()) {
					Statistics statics = new Statistics();
					statics.setCategory(category);
					statics.setCurrency("EUR");
					statics.setTotalAmount(BigDecimal.valueOf(transactionTotal.get(category)));
					statics.setTransactionCount(transactionCount.get(category));
					statisticsList.add(statics);
				}
				this.statisticMap.put(bankCode, statisticsList);

			}
		}
	}

	public Map<String, List<BankLog>> getBankLogsMap() {
		return bankLogsMap;
	}

}
