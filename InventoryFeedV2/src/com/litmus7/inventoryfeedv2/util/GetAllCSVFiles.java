package com.litmus7.inventoryfeedv2.util;

import java.io.File;

public class GetAllCSVFiles {
	public static File[] getCSVFiles(String folderPath) {
        File inputFolder = new File(folderPath);
        return inputFolder.listFiles((dir, name) -> name.endsWith(".csv"));
    }
}
