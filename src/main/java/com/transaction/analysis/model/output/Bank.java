/**
 * 
 */
package com.transaction.analysis.model.output;

/**
 * @author Sankha
 *
 */
public class Bank implements Comparable<Bank> {

	private String bankCode;
	private String bankName;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public int compareTo(Bank o) {
		return this.getBankCode().compareTo(o.getBankCode());
	}

}
