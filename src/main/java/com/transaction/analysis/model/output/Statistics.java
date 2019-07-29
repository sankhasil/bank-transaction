/**
 * 
 */
package com.transaction.analysis.model.output;

import java.math.BigDecimal;

import com.transaction.analysis.model.BankBase;

/**
 * @author Sankha
 *
 */
public class Statistics extends BankBase {

	private String category;
	private BigDecimal totalAmount;
	private Long transactionCount;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(Long transactionCount) {
		this.transactionCount = transactionCount;
	}

}
