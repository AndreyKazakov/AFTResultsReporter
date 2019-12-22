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
        doc.body().appendElement("h2").appendText("Test run: " + TestProperties.getInstance().getProperties().getProperty("buildNumber") + ". Failed tests: " + ResultsAnalyser.getFailedSuitesDetailsMap().size());
        Element table = doc.body().appendElement("table").attr("border","1").attr("cellspacing","0").attr("bordercolor","#e3e2e1");
        Element tbody = table.appendElement("tbody");

        int testSuitesCounter = 0;
        for (Map.Entry<TestSuite, ArrayList<FailDetails>> entry : ResultsAnalyser.getFailedSuitesDetailsMap().entrySet()) {
            Element tr = tbody.appendElement("tr");
            Element td = tr.appendElement("td").attr("style", "padding: 5px");
            td.appendElement("div").attr("style", "font-weight:bold; padding-bottom: 5px;").appendText(++testSuitesCounter +". "+ entry.getKey().getName());

            for (FailDetails fd: entry.getValue()){
                td.appendElement("div").attr("style","font-weight:bold; padding-bottom: 2px; padding-top: 5px;").appendText("Launch " + fd.number + ": ");
                td.appendElement("div").attr("style","padding-bottom: 2px").appendText("Step: "+ fd.step);
                td.appendElement("div").attr("style","padding-bottom: 4px").appendText("Error Message: "+ fd.errorMessage);

                if (fd.screenshotsList.size()>0) {
                    String suffix = "";
                    int tmpNum = 0;
                    for (String src : fd.screenshotsList) {
                        if (fd.screenshotsList.size()>1) {
                            tmpNum++;
                            suffix = " " + tmpNum;
                        }
                        td.appendElement("div").attr("style","padding-bottom: 2px").appendElement("a").attr("href", src).attr("target","_blank").appendText("Screenshot" + suffix);
                    }
                } else {
                    td.appendElement("div").attr("style","padding-bottom: 2px").appendText("Screenshots were not detected");
                }

                if (!fd.har.isEmpty()) {
                    td.appendElement("div").attr("style","padding-bottom: 2px").appendElement("a").attr("href", fd.har).attr("target","_blank").appendText("HAR archive");
                } else {
                    td.appendElement("div").attr("style","padding-bottom: 2px").appendText("HAR archive was not detected");
                }
            }

//            tr.appendElement("td").appendText(entry.getKey().getName());
//            Element innerTable = tr.appendElement("td").appendElement("table").attr("style","padding: 10px");

//            for (FailDetails fd: entry.getValue()){
//                Element innerTr = innerTable.appendElement("tr");
//                Element innerTd = innerTr.appendElement("td");
//                innerTd.appendElement("div").attr("style","padding: 2px").appendText("Test Case " + fd.number + ": ").attr("style","font-weight:bold");
//                innerTd.appendElement("div").attr("style","padding: 2px").appendText("Step: "+ fd.step);
//                innerTd.appendElement("div").attr("style","padding: 2px").appendText("Error Message: "+ fd.errorMessage);
//                //innerTr.appendText("td").appendText();
//            }

        }

        return doc.toString();
    }
}
