package communicationprocessor.communicationtypes.msg;

public enum MsgStatusCode {

    DELIVERED,
    SEEN,
    INVALIDSTATUSCODE;


    public static MsgStatusCode getStatusCodeFromString(String statuscode){

        if(statuscode == null){
            return  INVALIDSTATUSCODE;
        }


        switch (statuscode){
            case "DELI" : return DELIVERED ;
            case "SEEN" : return SEEN ;
        }

        return INVALIDSTATUSCODE;

    }



}
