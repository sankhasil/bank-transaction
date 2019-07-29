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
public class DailyBalance extends BankBase {
	
	private String Day;
	private BigDecimal totalOutgoingAmount;
	private Long outgoingCount;
	private BigDecimal totalIncomingAmount;
	private Long incomingCount;
	
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
