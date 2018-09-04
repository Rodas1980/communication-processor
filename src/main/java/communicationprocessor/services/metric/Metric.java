package communicationprocessor.services.metric;

import communicationprocessor.services.metric.call.CallMetrics;
import communicationprocessor.services.metric.msg.MsgMetrics;
import communicationprocessor.services.metric.msg.WordRanking;

public class Metric {

    private int numberRowsMissingFields;

    private int blankMessages;

    private int fieldErrors;

    private WordRanking messageRanking;

    private CountryCodeMetrics countryCodeMetrics;

    private int okCalls;

    private int koCalls;

    public Metric () {}

    public void sumMSGMetrics(MsgMetrics msgMetrics) {

        if(messageRanking == null){
            messageRanking = new WordRanking();
        }

        if(countryCodeMetrics == null){
            countryCodeMetrics = new CountryCodeMetrics();
        }

        numberRowsMissingFields += msgMetrics.getMissingFieldsRows();
        blankMessages += msgMetrics.getBlankMessages();
        fieldErrors += msgMetrics.getFieldErrosRows();
        messageRanking.sum(msgMetrics.getWordRanking());
    }

    public void sumCallMetrics(CallMetrics callMetrics) {

        if(countryCodeMetrics == null){
            countryCodeMetrics = new CountryCodeMetrics();
        }

        numberRowsMissingFields += callMetrics.getMissingFieldsRows();
        fieldErrors += callMetrics.getFieldErrosRows();
        countryCodeMetrics.sum(callMetrics.getNumberOfCallsbyCountry());
        okCalls += callMetrics.getOkCalls();
        koCalls += callMetrics.getKoCalls();

    }

    public int getNumberRowsMissingFields() {
        return numberRowsMissingFields;
    }

    public void setNumberRowsMissingFields(int numberRowsMissingFields) {
        this.numberRowsMissingFields = numberRowsMissingFields;
    }

    public int getBlankMessages() {
        return blankMessages;
    }

    public void setBlankMessages(int blankMessages) {
        this.blankMessages = blankMessages;
    }

    public int getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(int fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public WordRanking getMessageRanking() {
        return messageRanking;
    }

    public void setMessageRanking(WordRanking messageRanking) {
        this.messageRanking = messageRanking;
    }

    public CountryCodeMetrics getCountryCodeMetrics() {
        return countryCodeMetrics;
    }

    public void setCountryCodeMetrics(CountryCodeMetrics countryCodeMetrics) {
        this.countryCodeMetrics = countryCodeMetrics;
    }

    public int getOkCalls() {
        return okCalls;
    }

    public void setOkCalls(int okCalls) {
        this.okCalls = okCalls;
    }

    public int getKoCalls() {
        return koCalls;
    }

    public void setKoCalls(int koCalls) {
        this.koCalls = koCalls;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Rows missing: " + numberRowsMissingFields);
        stringBuilder.append("<br>");
        stringBuilder.append("Blank messages:" + blankMessages);
        stringBuilder.append("<br>");
        stringBuilder.append("Field Errors:" + fieldErrors);
        stringBuilder.append("<br>");
        stringBuilder.append("Message Ranking: <br>" + messageRanking);
        stringBuilder.append("<br>");
        stringBuilder.append("OK/KO Calls: " + okCalls + "/" + koCalls );
        stringBuilder.append("<br>");
        stringBuilder.append("Country Code / Number of Call or Msg");
        stringBuilder.append("<br>");
        stringBuilder.append(countryCodeMetrics.printCodebyCountry());
        stringBuilder.append("<br>");
        stringBuilder.append(countryCodeMetrics.printAverageDurationByCountryCode());


        return stringBuilder.toString();
    }
}
