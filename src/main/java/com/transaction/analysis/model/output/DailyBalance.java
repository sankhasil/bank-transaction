/**
 * 
 */
package com.transaction.analysis.model.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.transaction.analysis.model.BankBase;

/**
 * @author Sankha
 *
 */
public class DailyBalance {
	
	private String Day;
	private BigDecimal totalOutgoingAmount;
	private Long outgoingCount;
	private BigDecimal totalIncomingAmount;
	private Long incomingCount;
	private String currency;
	private LocalDateTime date;
	
	
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDay() {
		return Day;
	}
	public void setDay(String day) {
		Day = day;
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
