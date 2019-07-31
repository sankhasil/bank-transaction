/**
 * 
 */
package com.transaction.analysis.model.output;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Sankha
 *
 */
public class DailyBalance {
	
	private LocalDate date;
	private String currency;
	private BigDecimal totalOutgoingAmount;
	private Long outgoingCount;
	private BigDecimal totalIncomingAmount;
	private Long incomingCount;
	
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getTotalOutgoingAmount() {
		return totalOutgoingAmount;
	}
	public void setTotalOutgoingAmount(BigDecimal totalOutgoingAmount) {
		this.totalOutgoingAmount = totalOutgoingAmount;
	}
	public Long getOutgoingCount() {
		return outgoingCount;
	}
	public void setOutgoingCount(Long outgoingCount) {
		this.outgoingCount = outgoingCount;
	}
	public BigDecimal getTotalIncomingAmount() {
		return totalIncomingAmount;
	}
	public void setTotalIncomingAmount(BigDecimal totalIncomingAmount) {
		this.totalIncomingAmount = totalIncomingAmount;
	}
	public Long getIncomingCount() {
		return incomingCount;
	}
	public void setIncomingCount(Long incomingCount) {
		this.incomingCount = incomingCount;
	}
	
	

}
