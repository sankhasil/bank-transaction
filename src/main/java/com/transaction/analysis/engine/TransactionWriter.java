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

/**
 * @author Sankha
 *
 */
public class TransactionWriter {

    private static final Logger LOGGER = Logger.getLogger(TransactionWriter.class .getName());
    private final static char DEFAULT_SEPARATOR = ' ';
    private final static String BANK_RESULT_FILENAME= "banks.csv";

    
    
    public boolean writeBankResultToFile(String outputFilePath,List<Bank> bankDataList) {
    	File folderPath = new File(outputFilePath);
    	if(folderPath.isDirectory()) {
    		
    		Path filePath = Paths.get(folderPath.getAbsolutePath()+folderPath.separatorChar+BANK_RESULT_FILENAME);
    		String content = this.toCSV(bankDataList, ',', false);
    		try {
				Path banksFile = Files.write(filePath, content.getBytes(), StandardOpenOption.APPEND);
				return banksFile.isAbsolute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
    	}
    	
    	return false;
    }
    
    	
    private String toCSV(List<?> objectList, char separator, boolean displayHeader) {

        StringBuilder result =new StringBuilder();  
        if (objectList.size() == 0) {
            return result.toString();
        }   

        if(displayHeader){
            result.append(getHeaders(objectList.get(0),separator)); 
            result.append("\n");
        }

        for (Object obj : objectList) {
            result.append(addObjectRow(obj, separator)).append("\n");
        }

        return result.toString();
    }

    private String getHeaders(Object obj,char seperator) {
        StringBuilder resultHeader = new StringBuilder(); 
        boolean firstField = true;
        Field fields[] = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String value;
            try {
                value = field.getName();

                if(firstField){
                     resultHeader.append(value);
                     firstField = false;
                 }
                 else{
                    resultHeader.append(seperator).append(value);
                 }
                field.setAccessible(false);
            } catch (IllegalArgumentException  e) {
                LOGGER.severe(e.toString());
            }
        }           
      return resultHeader.toString();

    }


    private String addObjectRow(Object obj, char separator) {

        StringBuilder csvRow =new StringBuilder();  
        Field fields[] = obj.getClass().getDeclaredFields();
        boolean firstField = true;
        for (Field field : fields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(obj);
                if(value == null)
                    value = "";
                if(firstField){
                    csvRow.append(value);
                    firstField = false;
                }
                else{
                    csvRow.append(separator).append(value);
                }
                field.setAccessible(false);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                LOGGER.severe(e.toString());
            }
        }
        return csvRow.toString();
    }

}
