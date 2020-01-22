package com.automation.core.dataprovider;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.log4testng.Logger;

import com.automation.core.utilities.ExcelSheetDriver;
import com.automation.core.utilities.ReadPropertiesFile;
import com.automation.core.dataprovider.DataProviderCommonUtils;
import com.automation.core.dataprovider.ExcelFileDataProvider;

public class ExcelFileDataProvider {
	private static final Logger log = Logger.getLogger(ExcelFileDataProvider.class);

	@DataProvider(name = "getDataFromExcelFile")
	public static Object[][] fileDataProvider(Method testMethod)
			throws Exception {

		ExcelSheetDriver xlsUtil;
		Map<String, String> arguments = DataProviderCommonUtils
				.resolveDataProviderArguments(testMethod);
		
		String fileName = arguments.get("FileName");
		String sSheet = arguments.get("Sheet");
		File file = new File(ReadPropertiesFile.getProperty("TestDataPath"));
		xlsUtil = new ExcelSheetDriver(file.getCanonicalPath()+"/"+ fileName, sSheet);
		
		//	JExcel Data Fetch Code
		/*int ROWS = xlsUtil.RowCount() - 1;
		int COLS = xlsUtil.colCount();

		log.info("\n Rows " + ROWS + "\n Cols" + COLS);

		String[][] dataset = new String[ROWS][COLS];
		for (int rowCnt = 0; rowCnt < ROWS; rowCnt++) {

			for (int colCnt = 0; colCnt < COLS; colCnt++) {
				dataset[rowCnt][colCnt] = xlsUtil.ReadCell(colCnt, rowCnt + 1);
				// log.info("\n vals "+dataset [rowCnt][colCnt]);
			}
		}
		return dataset;
		*/
		System.out.println("Size:"+xlsUtil.retrieveTestData(0).length);
		return xlsUtil.retrieveTestData(0);
	}

}