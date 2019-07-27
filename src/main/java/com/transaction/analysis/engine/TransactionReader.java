package com.transaction.analysis.engine;

import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class TransactionReader {

	public void ReadFiles(URI filePath) {
		try (Reader reader = Files.newBufferedReader(
				Paths.get(filePath.getPath().isEmpty() ? ClassLoader.getSystemResource("csv/transactionLog.csv").toURI()
						: filePath));) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
			
			
		} catch (Exception e) {

		}
	}

}
