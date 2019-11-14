package testCaseDetails;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;


import java.util.List;

public class TestCaseDetails {

    private String uid;
    private String name;
    private String title;
    //private TestTime time;
    private Failure failure;
    private String status;
    private List<Step> steps;

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

//    public TestTime getTime() {
//        return time;
//    }

    public Failure getFailure() {
        return failure;
    }

    public String getStatus() {
        return status;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
