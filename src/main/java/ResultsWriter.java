import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import testCaseDetails.TestCaseDetails;

import java.util.ArrayList;
import java.util.Map;

public class ResultsWriter {

    public static String getHtmlReport() {

        Document doc = Jsoup.parse("<html></html>");
        doc.head().appendElement("title").appendText("Test Results Report");
        doc.body().appendElement("h2").appendText("Results for the test run: " + TestProperties.getInstance().getProperties().getProperty("buildNumber"));
        Element table = doc.body().appendElement("table").attr("border","1").attr("cellspacing","0");
        Element tbody = table.appendElement("tbody");

        for (Map.Entry<TestSuite, ArrayList<FailDetails>> entry : ResultsAnalyser.getFailedSuitesDetailsMap().entrySet()) {

            Element tr = tbody.appendElement("tr");
            tr.appendElement("td").appendText(entry.getKey().getName());
            Element innerTable = tr.appendElement("td").appendElement("table").attr("style","padding: 10px");

            for (FailDetails fd: entry.getValue()){
                Element innerTr = innerTable.appendElement("tr");
                Element innerTd = innerTr.appendElement("td");
                innerTd.appendElement("div").attr("style","padding: 2px").appendText("Test Case " + fd.number + ": ").attr("style","font-weight:bold");
                innerTd.appendElement("div").attr("style","padding: 2px").appendText("Step: "+ fd.step);
                innerTd.appendElement("div").attr("style","padding: 2px").appendText("Error Message: "+ fd.errorMessage);
                //innerTr.appendText("td").appendText();
            }
        }

        return doc.toString();
    }
}
