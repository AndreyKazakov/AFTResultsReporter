package testCaseDetails;

import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import exceptions.DeserializationException;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import testCaseDetails.Step;

public class TestCaseDetails {

//    public TestCaseDetails(String uid) {
//
//    }

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
