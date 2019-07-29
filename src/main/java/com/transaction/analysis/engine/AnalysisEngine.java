/**
 * 
 */
package com.transaction.analysis.engine;

import java.util.List;

import com.transaction.analysis.model.input.BankLog;
import com.transaction.analysis.model.input.Transaction;

/**
 * @author Sankha
 *
 */
public class AnalysisEngine {
	
	 private List<Transaction> listOfTransaction;
	 private List<BankLog> listOfBankLogs;
	public List<Transaction> getListOfTransaction() {
		return listOfTransaction;
	}
	public void setListOfTransaction(List<Transaction> listOfTransaction) {
		this.listOfTransaction = listOfTransaction;
	}
	public List<BankLog> getListOfBankLogs() {
		return listOfBankLogs;
	}
	public void setListOfBankLogs(List<BankLog> listOfBankLogs) {
		this.listOfBankLogs = listOfBankLogs;
	}
	 

}
