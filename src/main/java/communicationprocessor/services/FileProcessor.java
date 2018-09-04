package communicationprocessor.services;

import communicationprocessor.services.ksip.Ksip;
import communicationprocessor.services.metric.Metric;
import communicationprocessor.utility.HttpDownloadUtil;
import communicationprocessor.utility.JacksonUtil;
import communicationprocessor.utility.JsonDeparserUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@Component
public class FileProcessor {

    @Autowired
    Ksip kpis;

    @Autowired
    JacksonUtil jacksonUtil;

    @Value("${file.url}")
    String url;

    @Value("${file.extension}")
    String extension;


    public void process(String date) throws Exception{


        String fileURL = url + date + extension;
        LinkedList<JSONObject> jsonObjectsParsed;
        InputStreamReader streamReader;
        MetricKpisCalculator metricCalculator;
        streamReader = HttpDownloadUtil.downloadFile(fileURL);
        File fileKsip = new File("Ksip.json");

        if (fileKsip.exists()){
            kpis = jacksonUtil.readObjectFromFile(Ksip.class ,"Kpis.json");
        }



        if(streamReader == null){
            throw new IOException("The file was not found . The log file for the date may not exist or input date was in the " +
                    "wrong format." + System.getProperty("line.separator") + " Date format should be YYYYMMDD." +
                    "Example: /?date = 19910811");
        }


        long start_time = System.nanoTime();
        jsonObjectsParsed = JsonDeparserUtil.getJsonObjectsFromFile(streamReader);
        long end_time = System.nanoTime();

        long difference = TimeUnit.NANOSECONDS.toMillis(end_time - start_time);


        metricCalculator = new MetricKpisCalculator(jsonObjectsParsed);

        Metric metrics =  metricCalculator.getJsonMetrics();
        jacksonUtil.writeObjectToFile(metrics , "Metric.json");


        kpis.setJsonRead((kpis.getJsonRead())+1);
        kpis.setTimeToParseJsonFile(difference);
        kpis.setNumberOfRows((kpis.getNumberOfRows())+ jsonObjectsParsed.size());
        kpis.setNumberOfCalls((kpis.getNumberOfCalls())+ metricCalculator.getNumberCalls());
        kpis.setNumberOfMSG((kpis.getNumberOfMSG())+ metricCalculator.getNumberMsg());
        kpis.getDestinationCodeList().addAll(metricCalculator.getDestinationCodeList());
        kpis.getOriginCodeList().addAll(metricCalculator.getOriginCodeList());



        jacksonUtil.writeObjectToFile(kpis, "Kpis.json");

    }
}
