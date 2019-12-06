import com.google.gson.Gson;
import exceptions.DeserializationException;
import testCaseDetails.Attachment;
import testCaseDetails.TestCaseDetails;
import testCaseDetails.Step;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsAnalyser {

    private static HashMap<TestSuite, ArrayList<TestCaseDetails>> failedSuitesMap;

    private static List<TestSuite> testSuitesWithFailedTestCases;

    public static List<TestSuite> getTestSuitesWithFailedTestCases() {
        //если кол-во перезапусков 1 или более, т.е. запусков 2 и более, то берем testSuites с 2мя и более падениями.
        //иначе c одним и более падением
        int failedCount;
        if (Integer.valueOf(TestProperties.getInstance().getProperties().getProperty("restartTimes")) >= 1) {
            failedCount = 2;
        } else {
            failedCount = 1;
        }

        //выбираем testSuites с нужным количеством падений и сохраняем их в отдельном листе
        testSuitesWithFailedTestCases = new ArrayList<TestSuite>();

        for (TestSuite ts : Xunit.getTestSuitesList()) {
            if (ts.getStatistic().getFailed() + ts.getStatistic().getBroken() + ts.getStatistic().getCanceled() + ts.getStatistic().getPending() >= failedCount) {
                testSuitesWithFailedTestCases.add(ts);
                //System.out.println(ts.getName());
            }
        }

        return testSuitesWithFailedTestCases;
    }




    public static HashMap<TestSuite, ArrayList<TestCaseDetails>> getFailedSuitesMap() {
        //в отобранных testSuites находим id каждого зафейленного testCase. а по нему находим файл с деталями тесткейса и переводим его в java объект.
        //на выходе имеем карту TestSuite - FailedTestCaseDetails
        failedSuitesMap = new HashMap<TestSuite, ArrayList<TestCaseDetails>>();

        for (TestSuite ts : getTestSuitesWithFailedTestCases()) {

            ArrayList<TestCaseDetails> failedTestCases = new ArrayList<>();
            for (TestCase tc : ts.getTestCases()) {
                if (tc.getStatus().equalsIgnoreCase("failed") || tc.getStatus().equalsIgnoreCase("broken") ||
                        tc.getStatus().equalsIgnoreCase("pending") || tc.getStatus().equalsIgnoreCase("cancelled")) {

                    String jsonString = null;
                    TestCaseDetails tcd = null;
                    try {
                        jsonString = new String(Files.readAllBytes(Paths.get(TestProperties.getInstance().getProperties().getProperty("resultsFolder") + tc.getUid() + "-testcase.json")));
                        Gson g = new Gson();
                        tcd = g.fromJson(jsonString, TestCaseDetails.class);
                    } catch (IOException e) {
                        throw new DeserializationException("Произошла ошибка при получении объекта из JSON." + "\n" + e.getMessage());
                    }
                    failedTestCases.add(tcd);
                }
            }
            failedSuitesMap.put(ts, failedTestCases);
        }
        return failedSuitesMap;
    }




    //печатаем результаты
    public static void printResultsToConsole() {
        for (Map.Entry<TestSuite, ArrayList<TestCaseDetails>> entry : getFailedSuitesMap().entrySet()) {
                System.out.println("TestSuite: " + entry.getKey().getName());

                int i = 1;
                for (TestCaseDetails tcd : entry.getValue()) {
                    System.out.println("TestCase " + i + ": ");

                    for (Step s : tcd.getSteps()) {
                        if (s.getStatus().equalsIgnoreCase("failed") || s.getStatus().equalsIgnoreCase("broken")) {
                            System.out.println("Step: " + s.getName());


                            for (Attachment att : s.getAttachments()) {
                                String attachmentUrl = TestProperties.getInstance().getProperties().getProperty("reportServer") +
                                        "/reports/" + TestProperties.getInstance().getProperties().getProperty("buildNumber") +
                                        "/#xUnit/" + entry.getKey().getUid() + "/" + tcd.getUid() + "/" + att.getUid() + "?expanded=true";
                                if (att.getType().contains("image")) {
                                    System.out.println("Screenshot: " + attachmentUrl);
                                }
                                if (att.getType().contains("text")) {
                                    System.out.println("HAR: " + attachmentUrl);
                                }
                            }
                            break;
                        }
                    }

                    System.out.println("Message: " + tcd.getFailure().getMessage());

                    i++;

                }

                System.out.println("---------------------------------------------");
            }
    }

}
