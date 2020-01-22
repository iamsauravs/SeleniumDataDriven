package com.automation.core.customtestng;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import com.automation.core.customtestng.TestListenerAdapter;

public class CustomReport extends TestListenerAdapter implements IReporter {
	private static PrintWriter f_out;
	private static final String OUT_FOLDER = "custom-test-report";

	public void generateReport(List<XmlSuite> arg0, List<ISuite> suites,
			String outdir) {
		try {
			f_out = createWriter(OUT_FOLDER);
		} catch (IOException e) {
			e.printStackTrace();
		}

		startHtmlPage(f_out);

		try {
			generateAdditionalInfoReport(suites);
		} catch (Exception e) {
			e.printStackTrace();
		}

		endHtmlPage(f_out);

		f_out.flush();
		f_out.close();
	}

	private void generateAdditionalInfoReport(List<ISuite> suites)
			throws Exception {
		f_out.println("<b><font color=\"#333300\">Configuration Details</font></b><br/><br/>");
		f_out.println("<table class=\"details\" cellspacing=0 cellpadding=0");
		f_out.println("<tr><td>Demo Test</td></tr>");
		f_out.println("</table>");
		f_out.println("<br/>");

		String testName = "";

		int totalPassedMethods = 0;
		int totalFailedMethods = 0;
		int totalSkippedMethods = 0;
		int totalSkippedConfigurationMethods = 0;
		int totalFailedConfigurationMethods = 0;
		int totalMethods = 0;
		String passPercentage = "";
		int suite_totalPassedMethods = 0;
		int suite_totalFailedMethods = 0;
		int suite_totalSkippedMethods = 0;
		int suite_totalSkippedConfigurationMethods = 0;
		int suite_totalFailedConfigurationMethods = 0;
		int suite_totalMethods = 0;
		String suite_passPercentage = "";
		String suiteName = "";

		ITestContext overview = null;
		HashMap<String, String> dashboardReportMap = new HashMap<String, String>();
		for (ISuite suite : suites) {
			suiteName = suite.getName();
			// logger.info(">> " + suiteName + " <<");
			// f_out.println(">> " + suiteName + " <<");
			Map<String, ISuiteResult> tests = suite.getResults();

			// Iterate through all the tests in testng.xml file
			for (ISuiteResult r : tests.values()) {
				overview = r.getTestContext();
				testName = overview.getName();

				totalPassedMethods = overview.getPassedTests().getAllMethods()
						.size();
				totalFailedMethods = overview.getFailedTests().getAllMethods()
						.size();
				totalSkippedMethods = overview.getSkippedTests()
						.getAllMethods().size();
				totalSkippedConfigurationMethods = overview
						.getSkippedConfigurations().getAllMethods().size();
				totalFailedConfigurationMethods = overview
						.getFailedConfigurations().getAllMethods().size();
				totalMethods = overview.getAllTestMethods().length;

				f_out.println("Total Passed Methods: " + totalPassedMethods);
				f_out.println("Total Failed Methods: " + totalFailedMethods);

			}
		}
		f_out.flush();

	}

	private PrintWriter createWriter(String outdir) throws IOException {
		new File(outdir).mkdirs();
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(
				OUT_FOLDER, "emailable-test-run-report.html"))));
	}

	/** Starts HTML Stream */
	private void startHtmlPage(PrintWriter out) {
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		out.println("<head>");
		out.println("<title>Automation Test Results Summary</title>");

		out.println("<style type=\"text/css\">");
		out.println("body { margin-left:2cm; margin-right:2cm; background: white; color: #333300 ; font-size: 100%; font-family: Georgia, \"Times New Roman\", Times, serif; }");
		out.println("table.details{ margin-bottom:10px;border:0px solid white;border-collapse:collapse;empty-cells:hide;width=\"40%\"; }");
		out.println("table.details td { border:0px solid white; color: #333300;font-family:Georgia, \"Times New Roman\", Times, serif; font-size: 100%; text-align: left; }");
		out.println("table.param td { border:1px solid #333300; color: #333300 ;font-family:Georgia, \"Times New Roman\", Times, serif; font-size: 100%; text-align: center; }");
		out.println("table.param tr{ height: 35px;}");
		out.println("table.param th { border:0px solid #333300; color: white;font-family:Georgia, \"Times New Roman\", Times, serif; font-size: 100%; text-align: center; background-color: #333300; }");
		out.println("#content {margin-left:1.5cm; margin-right:1.5cm;margin-top:1.5; margin-bottom:1.5cm}");
		out.println("</style>");

		out.println("</head>");
		out.println("<body>");
		// out.println("<div id=\"content\">");
		Calendar cal = Calendar.getInstance();
		out.println("<br/><h1>AUTOMATION TEST REPORT</h1>");
		out.println("<br/><div align=\"right\">Report generated on: "
				+ cal.getTime() + "</div><br/><br/>");

		out.flush();
	}

	/** Finishes HTML Stream */
	private void endHtmlPage(PrintWriter out) {
		out.println("</body></html>");
	}

}
