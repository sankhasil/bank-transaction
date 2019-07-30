/**
 * 
 */
package com.transaction.analysis.engine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Logger;

import com.transaction.analysis.model.output.Bank;
import com.transaction.analysis.model.output.DailyBalance;
import com.transaction.analysis.model.output.Statistics;

/**
 * @author Sankha
 *
 */
public class TransactionWriter {

	private static final Logger LOGGER = Logger.getLogger(TransactionWriter.class.getName());
	private final static char DEFAULT_SEPARATOR = ',';
	private final static String BANK_RESULT_FILENAME = "banks.csv";
	private final static String DAILY_BALANCE_RESULT_FILENAME = "_daily_balace.csv";
	private final static String CATEGORIES_RESULT_FILENAME = "_categories.csv";
	

	public boolean writeBankResultToFile(String outputFilePath, List<Bank> bankDataList) {
		File folderPath = new File(outputFilePath);
		
		if (!folderPath.isDirectory()) {
			folderPath.mkdir();
		}

			Path filePath = Paths.get(folderPath.getAbsolutePath() + folderPath.separatorChar + BANK_RESULT_FILENAME);
			String content = this.toCSV(bankDataList, DEFAULT_SEPARATOR, false);
			try {
				Path banksFile = Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
				return banksFile.isAbsolute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

	}

	public boolean writeDailyBalanceResultToFile(String outputFilePath, List<DailyBalance> dailyBalanceList,String bankCode) {
		File folderPath = new File(outputFilePath);

		if (!folderPath.isDirectory()) {
			folderPath.mkdir();
		}
			Path filePath = Paths.get(folderPath.getAbsolutePath() + folderPath.separatorChar + bankCode+DAILY_BALANCE_RESULT_FILENAME);
			String content = this.toCSV(dailyBalanceList, DEFAULT_SEPARATOR, false);
			try {
				Path banksFile = Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE_NEW,StandardOpenOption.APPEND);
				return banksFile.isAbsolute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

	}
	public boolean writeCategoriesResultToFile(String outputFilePath, List<Statistics> statisticsList,String bankCode) {
		File folderPath = new File(outputFilePath);
		if (!folderPath.isDirectory()) {
			folderPath.mkdir();
		}

			Path filePath = Paths.get(folderPath.getAbsolutePath() + folderPath.separatorChar + bankCode+CATEGORIES_RESULT_FILENAME);
			String content = this.toCSV(statisticsList, DEFAULT_SEPARATOR, false);
			try {
				Path banksFile = Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
				return banksFile.isAbsolute();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	}
	private String toCSV(List<?> objectList, char separator, boolean displayHeader) {

		StringBuilder result = new StringBuilder();
		if (objectList.size() == 0) {
			return result.toString();
		}

		if (displayHeader) {
			result.append(getHeaders(objectList.get(0), separator));
			result.append("\n");
		}

		for (Object obj : objectList) {
			result.append(addObjectRow(obj, separator)).append("\n");
		}

		return result.toString();
	}

	private String getHeaders(Object obj, char seperator) {
		StringBuilder resultHeader = new StringBuilder();
		boolean firstField = true;
		Field fields[] = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String value;
			try {
				value = field.getName();

				if (firstField) {
					resultHeader.append(value);
					firstField = false;
				} else {
					resultHeader.append(seperator).append(value);
				}
				field.setAccessible(false);
			} catch (IllegalArgumentException e) {
				LOGGER.severe(e.toString());
			}
		}
		return resultHeader.toString();

	}

	private String addObjectRow(Object obj, char separator) {

		StringBuilder csvRow = new StringBuilder();
		Field fields[] = obj.getClass().getDeclaredFields();
		boolean firstField = true;
		for (Field field : fields) {
			if (!field.getName().equalsIgnoreCase("rowNumber")) {
				field.setAccessible(true);
				Object value;
				try {
					value = field.get(obj);
					if (value == null)
						value = "";
					if (firstField) {
						csvRow.append(value);
						firstField = false;
					} else {
						csvRow.append(separator).append(value);
					}
					field.setAccessible(false);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.severe(e.toString());
				}
			}
		}
		return csvRow.toString();
	}

}
