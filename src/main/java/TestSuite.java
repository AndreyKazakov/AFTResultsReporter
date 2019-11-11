import java.util.List;

public class TestSuite {

    private String uid;
    private String name;
    private String title;
    private Time time;
    private TestSuiteStatistic statistic;
    private String description;
    private List<TestCase> testCases;

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public TestSuiteStatistic getStatistic() {
        return statistic;
    }

}
