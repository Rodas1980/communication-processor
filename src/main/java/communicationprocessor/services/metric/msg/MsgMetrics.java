package communicationprocessor.services.metric.msg;

import communicationprocessor.communicationtypes.msg.Msg;
import communicationprocessor.services.metric.CountryCodeMetrics;

public class MsgMetrics {

    private Msg msg;

    private int missingFieldsRows = 0 ;

    private int blankMessages = 0;

    private int fieldErrosRows = 0;

    private WordRanking wordRanking;

    private CountryCodeMetrics numberOfCallsbyCountry;

    public MsgMetrics(Msg msg){
        this.msg = msg;
        calcMsgMetrics();
    }


    private void calcMsgMetrics() {

        if(!msg.isComplete()){missingFieldsRows++;}
        if(!msg.msgContentIsBlank()){blankMessages++;}
        if(msg.isFieldError()){fieldErrosRows++;}
        wordRanking = new WordRanking(msg.getMessage());
        numberOfCallsbyCountry = new CountryCodeMetrics(msg);

    }

    public int getMissingFieldsRows() {
        return missingFieldsRows;
    }

    public int getBlankMessages() {
        return blankMessages;
    }

    public int getFieldErrosRows() {
        return fieldErrosRows;
    }

    public WordRanking getWordRanking() {
        return wordRanking;
    }

}
