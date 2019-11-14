package testCaseDetails;


import java.util.List;

public class Step {

    private String name;
    private String title;
//    private TestTime time;
    private String status;
    private List<Attachment> attachments;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

//    public TestTime getTime() {
//        return time;
//    }

    public String getStatus() {
        return status;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }
}
