package communicationprocessor.utility;

import communicationprocessor.communicationtypes.PhoneNumber;

public class PhoneNumberDecode {

    public static PhoneNumber  getPhoneNumber (Long phoneNumber){

        PhoneNumber decodedhoneNumber = new PhoneNumber();
        String stringPhone = phoneNumber.toString();
        int lengthPhoneNumber = stringPhone.length();

        if(lengthPhoneNumber < 9 && lengthPhoneNumber > 12){
            return  null;
        }

        // Reversing String to get Last 9 number witch refer to the Phone Number
        StringBuilder stringBuilder = new StringBuilder(stringPhone);
        stringBuilder =  stringBuilder.reverse();
        String stringPhoneNumber =  stringBuilder.substring(0,9);

        //Reversing again to get the number in the right order
        stringBuilder = new StringBuilder(stringPhoneNumber);
        stringBuilder.reverse();
        stringPhoneNumber = stringBuilder.toString();
        decodedhoneNumber.setPhoneNumber(Long.parseLong(stringPhoneNumber));


        // Assuming the number are in the max 12 digts ( 9 for the number and 1-3 to country code
        switch(lengthPhoneNumber) {

            case 10:
                decodedhoneNumber.setCountryCode(Long.parseLong(stringPhone.substring(0, 1)));
                break;
            case 11:
                decodedhoneNumber.setCountryCode(Long.parseLong(stringPhone.substring(0, 2)));
                break;
            case 12:
                decodedhoneNumber.setCountryCode(Long.parseLong(stringPhone.substring(0, 3)));
        }

        return decodedhoneNumber;
    }



}
