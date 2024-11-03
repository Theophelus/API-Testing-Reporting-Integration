package org.anele.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    //define extent Report object
    protected static ExtentReports extent;

    //method response for getting recently created extent Report.
    public synchronized static ExtentReports getReport() {
        if (extent == null) {
            //get current timestamps
//            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
//            String current_date = format.format(new Date());

            //create report name using current date
            String file_name = "products-report" +".html";
            File dir = new File("reports/");
            if (!dir.exists()) dir.mkdirs();

            //create a full path of the report file
            String get_path = Paths.get(dir.getAbsolutePath(), file_name).toString();
            //create ExtentSparkReport object to generate html report
            ExtentSparkReporter reporter = new ExtentSparkReporter(get_path);
            //initialize extent report object
            extent = new ExtentReports();
            //attach report
            extent.attachReporter(reporter);
        }

        return extent;
    }

    //flush report after each execution
    public synchronized static void flushExtentReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
