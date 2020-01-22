package com.automation.core.utilities;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.automation.core.dataprovider.DataProviderCommonUtils;

public class JSONFileDataProvider {

	@DataProvider(name = "getDataFromJSONResponse")
	public static Object[][] fileDataProvider(Method testMethod)
			throws Exception {

		ReadJSON jsonReader;
		Map<String, String> arguments = DataProviderCommonUtils
				.resolveDataProviderArguments(testMethod);
		
		String jsonPath = arguments.get("jsonPath");
		String url = arguments.get("url");
		jsonReader = new ReadJSON();
		
		return jsonReader.getTestData(url, jsonPath);
	}

}
