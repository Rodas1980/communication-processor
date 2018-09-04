package communicationprocessor.controller;

import java.io.File;
import java.io.IOException;

import communicationprocessor.services.ksip.Ksip;
import communicationprocessor.services.metric.Metric;
import communicationprocessor.services.MetricKpisCalculator;
import communicationprocessor.utility.HttpDownloadUtility;
import communicationprocessor.utility.JacksonUtil;
import communicationprocessor.utility.JsonDeparserUtil;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunicationController {

    @Autowired
    Ksip ksip;

    @Autowired
    JacksonUtil jacksonUtil;

    @Value("${file.url}")
    String url;

    @Value("${file.extension}")
    String extension;

    @RequestMapping("/")
    public String home(@RequestParam(value="date",required = false) String date) {

        return "Use  /processfile?date=YYYYMMDD  to  load  the  metrics  from  a  JSON  file  of  the  specific  Date." + "<br>" +
                "After loading a metric file you can use /metric to see the metrics of the date you loaded through  /processfile , " + "<br>" +
                "or use  /kpis to see the overal attributes of the use of this platform";

    }




    @RequestMapping("/processfile")
    public String processFile(@RequestParam(value="date",required = false) String date) {

        if(date == null){
           return  "The file was not found . The log file for the date may not exist or input date was in the " +
                    "wrong format." + System.getProperty("line.separator") + " Date format should be YYYYMMDD." +
                    "Example: /?date = 19910811";
        }

        String fileURL = url + date + extension;
        LinkedList<JSONObject> jsonObjectsParsed;
        InputStreamReader streamReader;
        MetricKpisCalculator metricCalculator;
        File fileKsip = new File("Ksip.json");



        try {

            if (fileKsip.exists()){
                ksip = jacksonUtil.readObjectFromFile(Ksip.class ,"Kpis.json");
            }

            streamReader = HttpDownloadUtility.downloadFile(fileURL);


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

        }catch (IOException e){
            e.printStackTrace();
            return "Unexpected Error!";
        }

        return "The file was sucessfully read and parsed use /metrics to show metrics or /ksip to show the ksip";
    }



    @RequestMapping("/metrics")
    public String showMetrics() {

        Metric metric = null;


        try {

            metric = jacksonUtil.readObjectFromFile(Metric.class ,"Metric.json");

            if(metric == null){
                return "You havent load any jason file!";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(metric == null) {
            return "You havent load any jason file!";
        }

        return metric.toString();

    }


    @RequestMapping("/kpis")
    public String showKpis() {

        Ksip kpis = null;

        try {

            kpis = jacksonUtil.readObjectFromFile(Ksip.class ,"Kpis.json");

            if(kpis == null){
                return "You havent load any jason file!";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return kpis.toString();

    }
}
