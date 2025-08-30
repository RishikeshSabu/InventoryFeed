package com.litmus7.inventoryfeedv2.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MoveFile {
	public static void moveFile(File file,String folderDir) {
		try {
			Files.createDirectories(Paths.get(folderDir));
			Files.move(file.toPath(), Paths.get(folderDir, file.getName()), StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
