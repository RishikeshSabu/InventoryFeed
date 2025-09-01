package com.litmus7.inventoryfeedv3.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.inventoryfeedv3.constants.Constants;
import com.litmus7.inventoryfeedv3.exceptions.CSVFileAccessException;

public class ReadCSV {
	public static List<String[]> readCSV(File file) throws CSVFileAccessException{
		List<String[]> fileReader=new ArrayList<>();
		try(BufferedReader bf=new BufferedReader(new FileReader(file))){
			bf.readLine();
			String line;
			while((line=bf.readLine())!=null) {
				fileReader.add(line.split(","));
			}
			return fileReader;
		}catch(IOException e) {
			throw new CSVFileAccessException(Constants.CSV_ERROR);
		}
	}
}
