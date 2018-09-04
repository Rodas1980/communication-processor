package communicationprocessor.services.ksip;

import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class Ksip {

    private int jsonRead = 0;

    private int numberOfRows = 0;

    private int numberOfCalls = 0;

    private int numberOfMSG = 0;

    private Long timeToParseJsonFile = 0L;

    private HashSet<Long> originCodeList = new HashSet<>();

    private HashSet<Long> destinationCodeList = new HashSet<>();

    public Long getTimeToParseJsonFile() {return  timeToParseJsonFile;}

    public HashSet<Long> getOriginCodeList() {
        return originCodeList;
    }

    public void setOriginCodeList(HashSet<Long> originCodeList) {
        this.originCodeList = originCodeList;
    }

    public HashSet<Long> getDestinationCodeList() {
        return destinationCodeList;
    }

    public void setDestinationCodeList(HashSet<Long> destinationCodeList) {
        this.destinationCodeList = destinationCodeList;
    }

    public void setTimeToParseJsonFile(Long timeToParseJsonFile){this.timeToParseJsonFile = timeToParseJsonFile;}

    public int getJsonRead() {
        return jsonRead;
    }

    public void setJsonRead(int jsonRead) {
        this.jsonRead = jsonRead;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(int numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }

    public int getNumberOfMSG() {
        return numberOfMSG;
    }

    public void setNumberOfMSG(int numberOfMSG) {
        this.numberOfMSG = numberOfMSG;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder =  new StringBuilder();
        stringBuilder.append("Number of JSON read : " + jsonRead);
        stringBuilder.append("<br>");
        stringBuilder.append("Number of rows read : " + numberOfRows);
        stringBuilder.append("<br>");
        stringBuilder.append("Number of Calls deparsed: " + numberOfCalls);
        stringBuilder.append("<br>");
        stringBuilder.append("Number of messages deparsed: " + numberOfMSG);
        stringBuilder.append("<br>");
        stringBuilder.append("Number of diferents origin Codes in this JSON: " + originCodeList.size()) ;
        stringBuilder.append("<br>");
        stringBuilder.append("Number of diferents destination codes in this JSON: " + destinationCodeList.size());
        stringBuilder.append("<br>");
        stringBuilder.append("Time taken to parse the last Json: " + timeToParseJsonFile + "Millisecond's");
        return stringBuilder.toString();
    }
}
