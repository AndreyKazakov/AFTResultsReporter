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

        for (Map.Entry<TestSuite, ArrayList<TestCaseDetails>> entry : ResultsAnalyser.getFailedSuitesMap().entrySet()) {

            Element tr = tbody.appendElement("tr");
            tr.appendElement("td").appendText(entry.getKey().getName());
            tr.appendElement("td").appendElement("table");
        }

        return doc.toString();
    }
}
