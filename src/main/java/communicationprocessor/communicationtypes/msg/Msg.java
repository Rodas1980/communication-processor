package communicationprocessor.communicationtypes.msg;

import communicationprocessor.communicationtypes.PhoneNumber;
import communicationprocessor.utility.PhoneNumberDecode;

public class Msg {

    private Long timestamp;

    private PhoneNumber origin;

    private PhoneNumber destination ;

    private String message;

    private boolean fieldError;

    private MsgStatusCode msgStatusCode;


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        try {
            this.timestamp = Long.parseLong(timestamp.toString());
        }catch (NumberFormatException e){
            timestamp = null;
        }

    }

    public PhoneNumber getOrigin() {
        return origin;
    }

    public void setOrigin(Object origin) {
        try {
            this.origin = PhoneNumberDecode.getPhoneNumber(Long.parseLong(origin.toString()));
        }catch (NumberFormatException e){
            fieldError = true;
        }
    }

    public PhoneNumber getDestination() {
        return destination;
    }

    public void setDestination(Object destination) {
        try {
            this.destination = PhoneNumberDecode.getPhoneNumber(Long.parseLong(destination.toString()));
        }catch (NumberFormatException e){
            fieldError = true;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        try {
            this.message = String.valueOf(message);
        }catch (Exception e){
            fieldError = true;
        }
    }

    public MsgStatusCode getMsgStatusCode() {
        return msgStatusCode;
    }

    public void setMsgStatusCode(Object msgStatusCode) {

        try {
            if(msgStatusCode.toString()  == null){
                return;
            }

            this.msgStatusCode = MsgStatusCode.getStatusCodeFromString(msgStatusCode.toString());
            if(this.msgStatusCode == MsgStatusCode.INVALIDSTATUSCODE){
                fieldError = true;
            }
        }catch (Exception e){
            fieldError=true;
        }
    }

    public boolean isComplete(){

        if(timestamp == null || origin ==null || destination == null || message == null || msgStatusCode == null) {
            return false;
        }
        return true;
    }

    public boolean msgContentIsBlank(){

        if(message == null){
            return true;
        }

        if(message.isEmpty()) {
            return false;
        }
        return true;
    }


    public boolean isFieldError(){
        return fieldError;
    }
}
