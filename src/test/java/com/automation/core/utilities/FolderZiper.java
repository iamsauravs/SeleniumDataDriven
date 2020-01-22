package com.automation.core.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.automation.core.constants.*;
import com.automation.core.constants.Constants;
import com.automation.core.utilities.FolderZiper;

public class FolderZiper {

	private static Logger log = Logger.getLogger(FolderZiper.class.getName());

	public static void main(String[] a) throws Exception {
		zipFolder(Constants.REPORT_SOURCE_PATH, Constants.REPORT_ZIP_PATH);
	}

	public static void zipFolder(String srcFolder, String destZipFile) {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		try {
			fileWriter = new FileOutputStream(destZipFile);
			zip = new ZipOutputStream(fileWriter);

			addFolderToZip("", srcFolder, zip);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} finally {
			try {
				zip.flush();
				zip.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}

	private static void addFileToZip(String path, String srcFile,
			ZipOutputStream zip) {

		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = null;
			try {
				in = new FileInputStream(srcFile);
				zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
				while ((len = in.read(buf)) > 0) {
					zip.write(buf, 0, len);
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			} catch (Exception e) {
				log.error(e.getMessage());
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}

		}
	}

	private static void addFolderToZip(String path, String srcFolder,
			ZipOutputStream zip) {
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/"
						+ fileName, zip);
			}
		}
	}
}
