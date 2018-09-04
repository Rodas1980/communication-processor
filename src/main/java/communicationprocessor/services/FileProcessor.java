package communicationprocessor.services;

import communicationprocessor.services.ksip.Ksip;
import communicationprocessor.services.metric.Metric;
import communicationprocessor.utility.HttpDownloadUtility;
import communicationprocessor.utility.JacksonUtil;
import communicationprocessor.utility.JsonDeparserUtil;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class FileProcessor {

    private Ksip ksip;
    private JacksonUtil jacksonUtil;
    private String url;

    public FileProcessor(Ksip ksip , JacksonUtil jacksonUtil , String url) {
        this.ksip = ksip;
        this.jacksonUtil = jacksonUtil;
        this.url = url;
    }

    public String processFile() throws IOException{


        LinkedList<JSONObject> jsonObjectsParsed;
        InputStreamReader streamReader;
        MetricKpisCalculator metricCalculator;
        streamReader = HttpDownloadUtility.downloadFile(url);
        File fileKsip = new File("Ksip.json");

        if (fileKsip.exists()){
            ksip = jacksonUtil.readObjectFromFile(Ksip.class ,"Kpis.json");
        }



        if(streamReader == null){
            return "The file was not found . The log file for the date may not exist or input date was in the " +
                    "wrong format." + System.getProperty("line.separator") + " Date format should be YYYYMMDD." +
                    "Example: /?date = 19910811";
        }


        long start_time = System.nanoTime();
        jsonObjectsParsed = JsonDeparserUtil.getJsonObjectsFromFile(streamReader);
        long end_time = System.nanoTime();

        long difference = TimeUnit.NANOSECONDS.toMillis(end_time - start_time);

        if(jsonObjectsParsed == null){
            return "There was an error parsing the json.";
        }

        metricCalculator = new MetricKpisCalculator(jsonObjectsParsed);

        Metric metrics =  metricCalculator.getJsonMetrics();
        jacksonUtil.writeObjectToFile(metrics , "Metric.json");

        ksip.setJsonRead((ksip.getJsonRead())+1);
        ksip.setTimeToParseJsonFile(difference);
        ksip.setNumberOfRows((ksip.getNumberOfRows())+ jsonObjectsParsed.size());
        ksip.setNumberOfCalls((ksip.getNumberOfCalls())+ metricCalculator.getNumberCalls());
        ksip.setNumberOfMSG((ksip.getNumberOfMSG())+ metricCalculator.getNumberMsg());
        ksip.getDestinationCodeList().addAll(metricCalculator.getDestinationCodeList());
        ksip.getOriginCodeList().addAll(metricCalculator.getOriginCodeList());



        jacksonUtil.writeObjectToFile(ksip , "Kpis.json");

        return "The file was sucessfully read and parsed use /metrics to show metrics or /ksip to show the ksip";

    }
}
