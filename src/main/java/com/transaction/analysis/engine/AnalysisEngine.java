/**
 * 
 */
package com.transaction.analysis.engine;

import java.util.List;
import java.util.Map;

import com.transaction.analysis.model.input.BankLog;
import com.transaction.analysis.model.input.Transaction;

/**
 * @author Sankha
 *
 */
public class AnalysisEngine {
	
	 private List<Transaction> listOfTransaction;
	 private Map<String,BankLog> bankLogsMap;
	public List<Transaction> getListOfTransaction() {
		return listOfTransaction;
	}
	public void setListOfTransaction(List<Transaction> listOfTransaction) {
		this.listOfTransaction = listOfTransaction;
	}
	public Map<String, BankLog> getBankLogsMap() {
		return bankLogsMap;
	}
	public void setBankLogsMap(Map<String, BankLog> bankLogsMap) {
		this.bankLogsMap = bankLogsMap;
	}
	

}
