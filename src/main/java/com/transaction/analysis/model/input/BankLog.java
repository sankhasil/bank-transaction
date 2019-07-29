package com.transaction.analysis.model.input;

import java.math.BigDecimal;
import java.util.UUID;

import com.transaction.analysis.model.BankBase;

/**
 * 
 * @author Sankha
 *
 */
public class BankLog extends BankBase {

	private UUID transactionId;
	private String transactionSource;
	private String transactionDestination;
	private BigDecimal transactionAmount;
	private String transactionCategory;
	
	public UUID getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionSource() {
		return transactionSource;
	}
	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}
	public String getTransactionDestination() {
		return transactionDestination;
	}
	public void setTransactionDestination(String transactionDestination) {
		this.transactionDestination = transactionDestination;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getTransactionCategory() {
		return transactionCategory;
	}
	public void setTransactionCategory(String transactionCategory) {
		this.transactionCategory = transactionCategory;
	}

}
