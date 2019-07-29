package com.transaction.analysis.engine;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.UUID;

import com.transaction.analysis.model.input.BankLog;
import com.transaction.analysis.model.input.Transaction;
import com.transaction.analysis.utils.CommonUtils;

public class TransactionReader {
	private static final char DEFAULT_SEPARATOR = ',';
	private static final char DEFAULT_QUOTE = '"';
	private static final String TRANSACTION_FILE = "transactions.csv";
	private final AnalysisEngine analysisEngine = new AnalysisEngine();

	public void readTransactions(URI filePath) {
		File folder = new File(filePath);
		if (folder.isDirectory() && folder.listFiles().length > 0) {

			try (Scanner scanner = new Scanner(Paths.get(filePath.getPath().isEmpty()
					? ClassLoader.getSystemResource("csv/transactions.csv").toURI()
					: new URI(filePath.getPath() + FileSystems.getDefault().getSeparator() + TRANSACTION_FILE)));) {
				int count = 0;
				List<Transaction> transactionList = new ArrayList<>();
				while (scanner.hasNext()) {
					count++;
					List<String> line = parseLine(scanner.nextLine());
					Transaction transaction = new Transaction();
					transaction.setRowNumber((long) count);
					transaction.setDate(CommonUtils.parseFromString(line.get(0)));
					transaction.setBankCode(line.get(1));
					transaction.setBankTimeZone(TimeZone.getTimeZone(line.get(2)));
					transaction.setLogFilePath(line.get(3));
					transaction.setBankName(line.get(4));
					transactionList.add(transaction);

				}
				analysisEngine.setListOfTransaction(transactionList);

			} catch (Exception e) {

			}
		}

	}
	
	public void readBankLogs(URI filePath) {

			try (Scanner scanner = new Scanner(Paths.get(filePath));) {
				int count = 0;
				List<BankLog> bankLogList = new ArrayList<>();
				while (scanner.hasNext()) {
					count++;
					List<String> line = parseLine(scanner.nextLine());
					BankLog bankLog = new BankLog();
					bankLog.setRowNumber((long) count);
					bankLog.setDate(CommonUtils.parseFromString(line.get(0)));
					bankLog.setTransactionId(UUID.fromString(line.get(1)));
					bankLog.setTransactionSource(line.get(2));
					bankLog.setTransactionDestination(line.get(3));
					bankLog.setTransactionAmount(BigDecimal.valueOf(Double.parseDouble(line.get(4))));
					bankLog.setTransactionCategory(line.get(5));
					bankLogList.add(bankLog);

				}
				analysisEngine.setListOfBankLogs(bankLogList);

			} catch (Exception e) {

			}

	}

	public static List<String> parseLine(String cvsLine) {
		return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
	}

	public static List<String> parseLine(String cvsLine, char separators) {
		return parseLine(cvsLine, separators, DEFAULT_QUOTE);
	}

	public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

		List<String> result = new ArrayList<>();

		// if empty, return!
		if (cvsLine == null && cvsLine.isEmpty()) {
			return result;
		}

		if (customQuote == ' ') {
			customQuote = DEFAULT_QUOTE;
		}

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuffer curVal = new StringBuffer();
		boolean inQuotes = false;
		boolean startCollectChar = false;
		boolean doubleQuotesInColumn = false;

		char[] chars = cvsLine.toCharArray();

		for (char ch : chars) {

			if (inQuotes) {
				startCollectChar = true;
				if (ch == customQuote) {
					inQuotes = false;
					doubleQuotesInColumn = false;
				} else {

					// Fixed : allow "" in custom quote enclosed
					if (ch == '\"') {
						if (!doubleQuotesInColumn) {
							curVal.append(ch);
							doubleQuotesInColumn = true;
						}
					} else {
						curVal.append(ch);
					}

				}
			} else {
				if (ch == customQuote) {

					inQuotes = true;

					// Fixed : allow "" in empty quote enclosed
					if (chars[0] != '"' && customQuote == '\"') {
						curVal.append('"');
					}

					// double quotes in column will hit this!
					if (startCollectChar) {
						curVal.append('"');
					}

				} else if (ch == separators) {

					result.add(curVal.toString());

					curVal = new StringBuffer();
					startCollectChar = false;

				} else if (ch == '\r') {
					// ignore LF characters
					continue;
				} else if (ch == '\n') {
					// the end, break!
					break;
				} else {
					curVal.append(ch);
				}
			}

		}

		result.add(curVal.toString());

		return result;
	}

}
