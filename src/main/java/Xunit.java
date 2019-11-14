import com.google.gson.Gson;
import exceptions.DeserializationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Xunit {

    private static Xunit INSTANCE = null;

    private static Xunit getInstance() {
        if (INSTANCE == null) {
            String jsonString = null;
            try {
                jsonString = new String(Files.readAllBytes(Paths.get(TestProperties.getInstance().getProperties().getProperty("xunitPath"))));
                Gson g = new Gson();
                INSTANCE = g.fromJson(jsonString, Xunit.class);
                return INSTANCE;
            } catch (IOException e) {
                throw new DeserializationException("Произошла ошибка при получении объекта из JSON."+"\n"+ e.getMessage());
            }
        }
        return INSTANCE;
    }

    private List<TestSuite> testSuites;

    public static List<TestSuite> getTestSuitesList() {
        return getInstance().testSuites;
    }

    public static List<TestSuite> getTestSuitesListByNumberOfFailedTestCases(int failedNumber) {
        List<TestSuite> testSuitesWithFailedTestCases = new ArrayList<TestSuite>();
        for (TestSuite ts: getInstance().testSuites) {
            if (ts.getStatistic().getFailed() == failedNumber) {
                testSuitesWithFailedTestCases.add(ts);
            }
        }
        return testSuitesWithFailedTestCases;
    }




}
