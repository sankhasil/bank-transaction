package com.transaction.analysis.model.input;

import java.util.TimeZone;

import com.transaction.analysis.model.Base;

/**
 * 
 * @author Sankha
 *
 */
public class Transaction extends Base {

	private String bankCode;
	private TimeZone bankTimeZone;
	private String logFilePath;
	private String bankName;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public TimeZone getBankTimeZone() {
		return bankTimeZone;
	}

	public void setBankTimeZone(TimeZone bankTimeZone) {
		this.bankTimeZone = bankTimeZone;
	}

	public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
