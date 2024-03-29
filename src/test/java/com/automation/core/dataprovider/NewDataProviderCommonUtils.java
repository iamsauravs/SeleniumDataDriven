// THIS CLASS ISN'T USED ANY WHERE 

package com.automation.core.dataprovider;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.automation.core.dataprovider.DataProviderParameter;

public class NewDataProviderCommonUtils {
	public static Map<String, String> resolveDataProviderArguments(
			Method testMethod) throws Exception {
		if (testMethod == null)
			throw new IllegalArgumentException(
					"Test Method context cannot be null.");

		DataProviderParameter args = testMethod
				.getAnnotation(DataProviderParameter.class);
		if (args == null)
			throw new IllegalArgumentException(
					"Test Method context has no DataProviderArguments annotation.");
		if (args.value() == null || args.value().length == 0)
			throw new IllegalArgumentException(
					"Test Method context has a malformed DataProviderArguments annotation.");
		Map<String, String> arguments = new HashMap<String, String>();
		for (int i = 0; i < args.value().length; i++) {
			// String[] parts = args.value()[i].split("=");
			// arguments.put(parts[0], parts[1]);

			String[] parts = args.value()[i].split(";");

			for (int j = 0; j < parts.length; j++) {

				String[] mparts = parts[j].split("=");
				arguments.put(mparts[0], mparts[1]);
			}
		}
		return arguments;
	}
}