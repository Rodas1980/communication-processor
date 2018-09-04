package communicationprocessor.communicationtypes.call;

public enum CallStatusCode {

    OK,
    KO,
    INVALIDSTATUSCODE;

    public static CallStatusCode getStatusCodeFromString(String statuscode){

        switch (statuscode){
            case "OK" : return OK ;
            case "KO" : return KO ;
        }

        return INVALIDSTATUSCODE;

    }
}
