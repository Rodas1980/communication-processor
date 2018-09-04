package communicationprocessor.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import communicationprocessor.services.FileProcessor;
import communicationprocessor.services.ksip.Ksip;
import communicationprocessor.services.metric.Metric;
import communicationprocessor.utility.JacksonUtil;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunicationController {

    @Autowired
    JacksonUtil jacksonUtil;

    @Autowired
    FileProcessor fileProcessor;

    @RequestMapping("/")
    public String home(@RequestParam(value = "date", required = false) String date) {

        return "Use  /processfile?date=YYYYMMDD  to  load  the  metrics  from  a  JSON  file  of  the  specific  Date." + "<br>" +
                "After loading a metric file you can use /metric to see the metrics of the date you loaded through  /processfile , " + "<br>" +
                "or use  /kpis to see the overal attributes of the use of this platform";

    }


    @RequestMapping("/processfile")
    public ResponseEntity<String> processFile(@RequestParam(value = "date", required = false) String date) {

        if (date == null) {
            return new ResponseEntity("The file was not found . The log file for the date may not exist or input date was in the " +
                    "wrong format." + System.getProperty("line.separator") + " Date format should be YYYYMMDD." +
                    "Example: /?date = 19910811", HttpStatus.BAD_REQUEST);
        }

        try {

            fileProcessor.process(date);
            String successMessage = "The file was sucessfully read and parsed use /metrics to show metrics or /ksip to show the ksip";
            return new ResponseEntity<String>(successMessage, HttpStatus.OK);
        } catch (ParseException e) {
            return new ResponseEntity<String>("There was an error parsing the json.", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (IOException e) {
            Map<String, String> map  =new HashMap<String, String>(){{
                put("message", e.getMessage());
            }};
            System.out.println("PARSE OR IO EXCEPTION!!");
            return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Unexpected Error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @RequestMapping("/metrics")
    public String showMetrics() {

        Metric metric = null;


        try {

            metric = jacksonUtil.readObjectFromFile(Metric.class, "Metric.json");

            if (metric == null) {
                return "You havent load any json file!";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (metric == null) {
            return "You havent load any jason file!";
        }

        return metric.toString();

    }


    @RequestMapping("/kpis")
    public String showKpis() {

        Ksip kpis = null;

        try {

            kpis = jacksonUtil.readObjectFromFile(Ksip.class, "Kpis.json");

            if (kpis == null) {
                return "You havent load any jason file!";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return kpis.toString();

    }
}
