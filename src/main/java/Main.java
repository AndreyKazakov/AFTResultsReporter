import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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


        ExchangeService service = new ExchangeService();
        ExchangeCredentials credentials = new WebCredentials("", "", "");
        service.setCredentials(credentials);

        try {
            service.setUrl(new URI("https://webmail.tele2.ru/EWS/Exchange.asmx"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        try {
            EmailMessage msg = new EmailMessage(service);
            msg.setSubject("Hello world!");
            String bodyText = "<b>test message</b>";
            msg.setBody(new MessageBody(BodyType.HTML, bodyText));
            msg.getToRecipients().add(new EmailAddress("andrey.kazakov@aplana.com"));
           // msg.setFrom(new EmailAddress("andrey.kazakov@aplana.com"));
            msg.send();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
