package communicationprocessor.communicationtypes.call;

import communicationprocessor.communicationtypes.PhoneNumber;
import communicationprocessor.utility.PhoneNumberDecode;

public class Call  {

    private Long timestamp;

    private PhoneNumber origin;

    private PhoneNumber destination;

    private Long  duration;

    private CallStatusCode callStatusCode;

    private String statusDescription;

    private boolean fieldError;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        try {
            this.timestamp = Long.parseLong(timestamp.toString());
        }catch (NumberFormatException e){
            fieldError = true;
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Object duration) {
        try {
            this.duration = Long.parseLong(duration.toString());
        }catch (NumberFormatException e){
            fieldError = true;
        }
    }

    public CallStatusCode getCallStatusCode() {
        return callStatusCode;
    }

    public void setCallStatusCode(Object callStatusCode) {
        try {

            if(callStatusCode.toString() == null){
                return;
            }

            this.callStatusCode = CallStatusCode.getStatusCodeFromString(callStatusCode.toString());

            if(this.callStatusCode == CallStatusCode.INVALIDSTATUSCODE){
                fieldError = true;
            }

        }catch (Exception e){
            fieldError = true;
        }
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(Object statusDescription) {
        try {
            this.statusDescription = String.valueOf(statusDescription.toString());
        }catch (Exception e){
            fieldError = true;
        }
    }


    public boolean isComplete(){

        if(timestamp == null || origin ==null || destination == null || duration == null || callStatusCode == null || statusDescription == null) {
            return false;
        }
        return true;
    }

    public boolean isFieldError(){
        return fieldError;
    }




}
