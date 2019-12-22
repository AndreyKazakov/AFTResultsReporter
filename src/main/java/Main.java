import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        //ResultsAnalyser.printResultsToConsole();
        System.out.println(ResultsWriter.getHtmlReport());

        try {
            FileWriter writer = new FileWriter(TestProperties.getInstance().getProperties().getProperty("resultsFolder")+"report.html", false);
            writer.write(ResultsWriter.getHtmlReport());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
