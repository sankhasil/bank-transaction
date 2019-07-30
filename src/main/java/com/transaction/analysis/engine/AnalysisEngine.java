/**
 * 
 */
package com.transaction.analysis.engine;

import java.math.BigDecimal;
import java.nio.file.FileSystems;
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
	private Map<String, DailyBalance> dailyBalanceMap = new HashMap<>();
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

	public Map<String, DailyBalance> getDailyBalanceMap() {
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

	public void analyzeDailyBalaceData(String inputPath) {
//		for (Transaction trans : this.listOfTransaction) {
//			String fullPath = inputPath + FileSystems.getDefault().getSeparator()
//					+ trans.getLogFilePath().replaceFirst("./", "");
//			transReader.readBankLogs(fullPath, trans.getBankCode());
//		}
		for (String bankCode : this.bankLogsMap.keySet()) {
			List<BankLog> bankLog = this.bankLogsMap.get(bankCode);

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
