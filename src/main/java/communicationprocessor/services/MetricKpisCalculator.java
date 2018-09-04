package communicationprocessor.services;

import communicationprocessor.communicationtypes.call.Call;
import communicationprocessor.communicationtypes.msg.Msg;
import communicationprocessor.services.metric.Metric;
import communicationprocessor.services.metric.call.CallMetrics;
import communicationprocessor.services.metric.msg.MsgMetrics;
import communicationprocessor.utility.JacksonUtil;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;


public class MetricKpisCalculator {

    Metric metrics;
    LinkedList<JSONObject> jsonObjects;
    JacksonUtil jacksonUtil;
    int numberCalls;
    int numberMsg;
    HashSet<Long> originCodeList;
    HashSet<Long> destinationCodeList;

    public MetricKpisCalculator(LinkedList<JSONObject> jsonObjects){
       this.jsonObjects = jsonObjects;
       this.metrics = new Metric();
       this.jacksonUtil =  new JacksonUtil();
       destinationCodeList = new HashSet<>();
       originCodeList = new HashSet<>();
    }

    public Metric getJsonMetrics() throws IOException {

        for (JSONObject jsonObject : jsonObjects) {

            if (jsonObject.containsKey("message_type")) {

                switch (jsonObject.get("message_type").toString()) {

                    case "CALL":
                        this.metrics.sumCallMetrics(getCallMetrics(jsonObject));
                       numberCalls++;
                        break;
                    case "MSG":
                        this.metrics.sumMSGMetrics(getMSGMetrics(jsonObject));
                        numberMsg++;
                        break;
                    default:
                        continue;
                }

            }

        }

        originCodeList = metrics.getCountryCodeMetrics().getOriginByCountry();
        destinationCodeList = metrics.getCountryCodeMetrics().getDestinationByCountry();

        return this.metrics;
    }

    private MsgMetrics getMSGMetrics(JSONObject jsonObject) {

        Msg msg = new Msg();

        if(jsonObject.containsKey("timestamp")){msg.setTimestamp(jsonObject.get("timestamp"));}
        if(jsonObject.containsKey("origin")){msg.setOrigin(jsonObject.get("origin"));}
        if(jsonObject.containsKey("destination")){msg.setDestination(jsonObject.get("destination"));}
        if(jsonObject.containsKey("message_content")){msg.setMessage(jsonObject.get("message_content"));}
        if(jsonObject.containsKey("message_status")){msg.setMsgStatusCode(jsonObject.get("message_status"));}

        MsgMetrics msgMetrics = new MsgMetrics(msg);

        return msgMetrics;

    }

    private CallMetrics getCallMetrics(JSONObject jsonObject) {

        Call call = new Call();

        if(jsonObject.containsKey("timestamp")){call.setTimestamp(jsonObject.get("timestamp"));}
        if(jsonObject.containsKey("origin")){call.setOrigin(jsonObject.get("origin"));}
        if(jsonObject.containsKey("destination")){call.setDestination(jsonObject.get("destination"));}
        if(jsonObject.containsKey("duration")){call.setDuration(jsonObject.get("duration"));}
        if(jsonObject.containsKey("status_code")){call.setCallStatusCode(jsonObject.get("status_code"));}
        if(jsonObject.containsKey("status_description")){call.setStatusDescription(jsonObject.get("status_description"));}

        CallMetrics callMetrics = new CallMetrics(call);

        return callMetrics;

    }


    public LinkedList<JSONObject> getJsonObjects() {
        return jsonObjects;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (JSONObject jsonObject: jsonObjects) {
            stringBuilder.append((jsonObject));
            stringBuilder.append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }

    public int getNumberCalls() {
        return numberCalls;
    }

    public int getNumberMsg() {
        return numberMsg;
    }

    public HashSet<Long> getOriginCodeList() {
        return originCodeList;
    }

    public HashSet<Long> getDestinationCodeList() {
        return destinationCodeList;
    }
}
