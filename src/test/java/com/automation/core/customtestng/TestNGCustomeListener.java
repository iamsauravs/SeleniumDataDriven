package com.automation.core.customtestng;

import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;

import com.automation.core.customtestng.AssertCustom;
import com.automation.core.customtestng.TestListenerAdapter;

public class TestNGCustomeListener extends TestListenerAdapter {

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {

		Reporter.setCurrentTestResult(result);

		if (method.isTestMethod()) {

			List<Throwable> verificationFailures = AssertCustom
					.getVerificationFailures();

			// if there are verification failures...
			if (verificationFailures.size() > 0) {

				// set the test to failed
				result.setStatus(ITestResult.FAILURE);
				int size = verificationFailures.size();
				// if there is an assertion failure add it to
				// verificationFailures
				if (result.getThrowable() != null) {
					if(verificationFailures.size() <0){
						verificationFailures.add(result.getThrowable());
						StringBuilder failureMessage = new StringBuilder("Failures Message (").append(size).append("):\n\n");
						for (int i = 0; i < size - 1; i++) {
							failureMessage.append("Failure ").append(i + 1)
							.append(" of ").append(size).append(":\n");
							Throwable t = verificationFailures.get(i);
							String fullStackTrace = Utils.stackTrace(t, false)[1];
							failureMessage.append(fullStackTrace).append("\n\n");
						}
						System.out.println(failureMessage.toString());

					}
				}


				// if there's only one failure just set that
				if (size == 1) {
					result.setThrowable(verificationFailures.get(0));
				} else {
					// create a failure message with all failures and stack
					// traces (except last failure)
					StringBuilder failureMessage = new StringBuilder("Multiple failures (").append(size)
							.append("):\n\n");

					for (int i = 0; i < size - 1; i++) {
						failureMessage.append("Failure ").append(i + 1)
						.append(" of ").append(size).append(":\n");
						Throwable t = verificationFailures.get(i);
						String fullStackTrace = Utils.stackTrace(t, false)[1];
						failureMessage.append(fullStackTrace).append("\n\n");
					}
					System.out.println(failureMessage.toString());
					// final failure
					Throwable last = verificationFailures.get(size - 1);
					failureMessage.append("Failure ").append(size)
					.append(" of ").append(size).append(":\n");
					failureMessage.append(last.toString());

					// set merged throwable
					Throwable merged = new Throwable(failureMessage.toString());
					merged.setStackTrace(last.getStackTrace());

					result.setThrowable(merged);
				}
			}
		}
	}
}