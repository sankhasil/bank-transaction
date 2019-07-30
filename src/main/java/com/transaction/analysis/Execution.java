/**
 * 
 */
package com.transaction.analysis;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.util.logging.Logger;

import com.transaction.analysis.engine.AnalysisEngine;
import com.transaction.analysis.engine.TransactionReader;
import com.transaction.analysis.engine.TransactionWriter;
import com.transaction.analysis.model.input.Transaction;

/**
 * @author Sankha
 *
 */
public class Execution {

	static String inputPath, outputPath;
	static Logger logger = Logger.getLogger(Execution.class.getName());

	public static void main(String[] args) {
		try {
			AnalysisEngine analysisEngine = AnalysisEngine.getInstance();
			processCommandLineArguments(args);
			TransactionReader transReader = new TransactionReader();
			transReader.readTransactions(inputPath);
			TransactionWriter transWriter = new TransactionWriter();
			analysisEngine.analyzeBankData();
			if (transWriter.writeBankResultToFile(outputPath, analysisEngine.getBanksList())) {
				logger.info("Bank File created");
			}
			
			
			if (analysisEngine.getBankLogsMap().size() == 0) {
				for (Transaction trans : analysisEngine.getListOfTransaction()) {
					String fullPath = inputPath + FileSystems.getDefault().getSeparator()
							+ trans.getLogFilePath().replaceFirst("./", "");
					transReader.readBankLogs(fullPath, trans.getBankCode());
				}
			}
			analysisEngine.analyzeStatisticsData();
			for (String bankCode : analysisEngine.getStatisticMap().keySet()) {
				if (transWriter.writeCategoriesResultToFile(outputPath, analysisEngine.getStatisticMap().get(bankCode),
						bankCode)) {
					logger.info(bankCode + " Categories created.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void processCommandLineArguments(String... args) throws URISyntaxException {
		for (String arg : args) {
			if (arg.contains("=")) {
				switch (arg.split("=")[0]) {
				case "-i":
				case "-I":
				case "-inputpath":
				case "-InputPath":
				case "-INPUTPATH":
					String[] keyArr = arg.split("=");
					if (keyArr.length > 1 && !keyArr[1].isEmpty()) {
						inputPath = keyArr[1];
					}
					break;
				case "-o":
				case "-O":
				case "-outputpath":
				case "-OutputPath":
				case "-OUTPUTPATH":
					String[] keyArr1 = arg.split("=");
					if (keyArr1.length > 1 && !keyArr1[1].isEmpty()) {
						outputPath = keyArr1[1];
					}
					break;
				default:
					break;
				}

			} else {
				switch (arg) {
				case "-h":
				case "-H":
				case "-help":
				case "-HELP":
					System.out.println("Write help of the execution");
					System.exit(0);
					break;
				case "-v":
				case "-V":
				case "-version":
				case "-VERSION":
					System.out.println("1.0.0-beta");
					System.exit(0);
					break;
				default:
					break;
				}
			}
		}
	}

}
