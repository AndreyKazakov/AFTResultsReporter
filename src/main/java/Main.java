import com.google.gson.Gson;
import exceptions.DeserializationException;
import testCaseDetails.TestCaseDetails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        //если кол-во перезапусков 1 или более, т.е. запусков 2 и более, то берем testSuites с 2мя и более падениями.
        //иначе c одним и более падением
        int failedCount;
        if (Integer.valueOf(TestProperties.getInstance().getProperties().getProperty("restartTimes")) >= 1) {
            failedCount = 2; }
            else { failedCount = 1; }

        //выбираем testSuites с нужным количеством падений и сохраняем их в отдельном листе
        List<TestSuite> testSuitesWithFailedTestCases = new ArrayList<TestSuite>();

        for (TestSuite ts: Xunit.getTestSuitesList()) {
            if (ts.getStatistic().getFailed() + ts.getStatistic().getBroken() + ts.getStatistic().getCanceled() + ts.getStatistic().getPending() >= failedCount) {
                testSuitesWithFailedTestCases.add(ts);
                //System.out.println(ts.getName());
            }
        }

        //System.out.println(testSuitesWithFailedTestCases.size());


        //в отобранных testSuites находим id каждого зафейленного testCase. а по нему находим файл с деталями тесткейса и переводим его в java объект.
        // на выходе имеем карту TestSuite - FailedTestCaseDetails
        HashMap<String, ArrayList<TestCaseDetails>> failedSuitesMap = new HashMap<String, ArrayList<TestCaseDetails>>();

        for (TestSuite ts : testSuitesWithFailedTestCases) {

            ArrayList<TestCaseDetails> failedTestCases = new ArrayList<>();
            for (TestCase tc : ts.getTestCases()) {
                if (tc.getStatus().equalsIgnoreCase("failed") || tc.getStatus().equalsIgnoreCase("broken") ||
                        tc.getStatus().equalsIgnoreCase("pending") || tc.getStatus().equalsIgnoreCase("cancelled")) {

                        String jsonString = null;
                        TestCaseDetails tcd = null;
                        try {
                            jsonString = new String(Files.readAllBytes(Paths.get(TestProperties.getInstance().getProperties().getProperty("resultsFolder")+tc.getUid()+"-testcase.json")));
                            Gson g = new Gson();
                            tcd = g.fromJson(jsonString, TestCaseDetails.class);
                        } catch (IOException e) {
                            throw new DeserializationException("Произошла ошибка при получении объекта из JSON."+"\n"+ e.getMessage());
                        }
                    failedTestCases.add(tcd);
                }
            }
            failedSuitesMap.put(ts.getName(), failedTestCases);
        }





    }
}
