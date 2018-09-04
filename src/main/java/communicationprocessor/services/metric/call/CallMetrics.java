package communicationprocessor.services.metric.call;

import communicationprocessor.communicationtypes.call.Call;
import communicationprocessor.communicationtypes.call.CallStatusCode;
import communicationprocessor.services.metric.CountryCodeMetrics;

public class CallMetrics {

    private Call call;
    private int missingFieldsRows;
    private int fieldErrosRows;
    private int okCalls;
    private int koCalls;
    private CountryCodeMetrics numberOfCallsbyCountry;

    public CallMetrics(Call call){
        this.call = call;
        calcCallMetrics();
    }

    private void calcCallMetrics() {

        if(!call.isComplete()){missingFieldsRows++;}
        if(call.isFieldError()){fieldErrosRows++;}
        if(call.getCallStatusCode()== CallStatusCode.OK){okCalls++;}
        if(call.getCallStatusCode()== CallStatusCode.KO){koCalls++;}
        numberOfCallsbyCountry = new CountryCodeMetrics(call);
    }


    public int getMissingFieldsRows() {
        return missingFieldsRows;
    }

    public int getFieldErrosRows() {
        return fieldErrosRows;
    }

    public int getOkCalls() {
        return okCalls;
    }

    public int getKoCalls() {
        return koCalls;
    }

    public CountryCodeMetrics getNumberOfCallsbyCountry() {
        return numberOfCallsbyCountry;
    }
}
