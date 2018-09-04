package communicationprocessor.services.metric;

import communicationprocessor.communicationtypes.call.Call;
import communicationprocessor.communicationtypes.msg.Msg;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class CountryCodeMetrics {

    private Call call;
    private Msg msg;
    private Long originCountryCode;
    private Long  destinationCountryCode;
    private HashMap<Long,Integer> callsByCountry;
    private HashMap<Long, LinkedList<Long>> averageDurationByCountryCode;
    private HashSet<Long> originByCountry;
    private HashSet<Long> destinationByCountry;
    private boolean  callDurationAdded;

    public CountryCodeMetrics(Call call){

        this.call = call;
        averageDurationByCountryCode = new HashMap<>();
        callsByCountry = new HashMap<>();

        if(this.call.getOrigin() != null) {
            originCountryCode = this.call.getOrigin().getCountryCode();
            destinationCountryCode = this.call.getDestination().getCountryCode();
        }

        if(this.call.getDestination() != null) {
            originCountryCode = this.call.getDestination().getCountryCode();
            destinationCountryCode = this.call.getDestination().getCountryCode();
        }

        createMetricsAverage(call);
        createMetricsNumberOfCalls(originCountryCode);
        createMetricsNumberOfCalls(destinationCountryCode);

        originByCountry = new HashSet<>();
        destinationByCountry = new HashSet<>();
        createCountrysCodes();

    }

    public CountryCodeMetrics(Msg msg){

        this.msg = msg;
        callsByCountry = new HashMap<>();
        averageDurationByCountryCode = new HashMap<>();

        if(this.msg.getOrigin() != null) {
            originCountryCode = this.msg.getOrigin().getCountryCode();
            createMetricsNumberOfCalls(originCountryCode);
        }

        if(this.msg.getDestination() != null) {
            destinationCountryCode = this.msg.getDestination().getCountryCode();
            createMetricsNumberOfCalls(destinationCountryCode);
        }

        originByCountry = new HashSet<>();
        destinationByCountry = new HashSet<>();
        createCountrysCodes();

    }

    public CountryCodeMetrics(){
        callsByCountry = new HashMap<>();
        originByCountry = new HashSet<>();
        destinationByCountry = new HashSet<>();
        averageDurationByCountryCode = new HashMap<>();
    }

    public HashMap<Long, Integer> getCallsByCountry() {
        return callsByCountry;
    }

    public HashSet<Long> getOriginByCountry() {
        return originByCountry;
    }

    public HashSet<Long> getDestinationByCountry() {
        return destinationByCountry;
    }

    public HashMap<Long,  LinkedList <Long>> getAverageDurationByCountryCode() {
        return averageDurationByCountryCode;
    }

    public void createCountrysCodes(){
        originByCountry.add(originCountryCode);
        destinationByCountry.add(destinationCountryCode);
    }



    public void createMetricsNumberOfCalls(Long originCountryCode){

        if(callsByCountry.get(originCountryCode)== null) {
            callsByCountry.put(originCountryCode,1);
            return;
        }

        int value = callsByCountry.get(originCountryCode);
        value++;
        callsByCountry.put(originCountryCode,value);

    }

    public void createMetricsAverage (Call call){

        Long origin = null;
        Long destination = null;
        Long callduration = call.getDuration();
        LinkedList <Long> listDurations = new LinkedList<>();
        listDurations.add(callduration);


        if(call.getOrigin() != null){
            origin = call.getOrigin().getCountryCode();
        }

        if(call.getDestination() != null) {
            destination = call.getDestination().getCountryCode();
        }

        if(origin == destination) {
                addNewCountryAndDuration(origin, listDurations);
                return;
        }
        addNewCountryAndDuration(origin, listDurations);
        addNewCountryAndDuration(destination,listDurations);


    }

    public void addNewCountryAndDuration(Long countryCode , LinkedList<Long> listDurations){

        if(averageDurationByCountryCode.get(countryCode) == null){

            averageDurationByCountryCode.put(countryCode,listDurations);
            return;
        }
            LinkedList <Long> value = averageDurationByCountryCode.get(countryCode);
            value.addAll(listDurations);
            averageDurationByCountryCode.put(countryCode,value);
    }

    public void sum(CountryCodeMetrics countryCodeMetrics){

        for (Long l : countryCodeMetrics.averageDurationByCountryCode.keySet()){

            LinkedList <Long> sumValue = countryCodeMetrics.getAverageDurationByCountryCode().get(l);
            LinkedList <Long> value  = this.averageDurationByCountryCode.get(l);

            if(value == null){
                this.averageDurationByCountryCode.put(l,sumValue);
                continue;
            }
            value.addAll(sumValue);
            this.averageDurationByCountryCode.put(l,value);
        }

        for (Long l : countryCodeMetrics.callsByCountry.keySet()){

            Integer value = this.callsByCountry.get(l);
            Integer sumValue = countryCodeMetrics.callsByCountry.get(l);

            if(value == null){
                this.callsByCountry.put(l,sumValue);
                continue;
            }
            value += countryCodeMetrics.getCallsByCountry().get(l);
            this.callsByCountry.put(l,value);
        }

        for (Long l : countryCodeMetrics.getOriginByCountry()) {
            originByCountry.add(l);
        }


        for (Long l : countryCodeMetrics.getDestinationByCountry()) {
            destinationByCountry.add(l);
        }

    }

    public String printCodebyCountry(){

        StringBuilder stringBuilder = new StringBuilder();

        for (Long l : this.callsByCountry.keySet()){

            Integer value = this.callsByCountry.get(l);
            stringBuilder.append(l + " / " + value);
            stringBuilder.append("<br>");
        }

        return  stringBuilder.toString();
    }

    public String printAverageDurationByCountryCode(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CountryCode / AverageCallDuration");
        stringBuilder.append("<br>");

        for (Long l : this.averageDurationByCountryCode.keySet()){

            Long average = 0L;
            LinkedList<Long> durations = this.averageDurationByCountryCode.get(l);
            Integer size = durations.size();

            for (Long v : durations){
                average += v;
            }

            average = average / size;

            stringBuilder.append(l + " / " + average);
            stringBuilder.append("<br>");
        }

        return  stringBuilder.toString();
    }



}
