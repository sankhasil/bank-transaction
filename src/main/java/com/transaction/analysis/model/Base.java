/**
 * 
 */
package com.transaction.analysis.model;

import java.time.LocalDateTime;

/**
 * @author Sankha
 *
 */
public class Base {
	private LocalDateTime date;
	private Long rowNumber;
	
	
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Long getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

}
