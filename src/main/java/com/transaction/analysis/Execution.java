/**
 * 
 */
package com.transaction.analysis;

import java.net.URI;
import java.net.URISyntaxException;

import com.transaction.analysis.engine.TransactionReader;

/**
 * @author Sankha
 *
 */
public class Execution {

static URI fileInputPath,fileOutputPath;
	public static void main(String[] args) {
		try {
			processCommandLineArguments(args);
			TransactionReader transReader = new TransactionReader();
			transReader.ReadFiles(fileInputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void processCommandLineArguments(String... args) throws URISyntaxException {
		for (String arg : args) {
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
			case "-i=":
			case "-I=":
			case "-inputpath=":
			case "-InputPath=":
			case "-INPUTPATH=":
				setPath(arg,fileInputPath);
				break;
			case "-o=":
			case "-O=":
			case "-outputpath=":
			case "-OutputPath=":
			case "-OUTPUTPATH=":
				setPath(arg,fileOutputPath);
				break;


			default:
				break;
			}
		}
	}

	/**
	 * @param path
	 * @param arg
	 * @throws URISyntaxException
	 */
	private static void setPath(String arg,URI path) throws URISyntaxException {
		String[] keyArr = arg.split("=");
		if(keyArr.length > 1 && !keyArr[1].isEmpty()) {
			path = new URI(keyArr[1]);
		}
	}
}
