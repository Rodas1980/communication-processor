package communicationprocessor.utility;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.LinkedList;

public class JsonDeparserUtil {

    public static LinkedList<JSONObject> getJsonObjectsFromFile(InputStreamReader inputStreamReader){

        LinkedList<JSONObject> jsonObjects = new LinkedList<>();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        String line = null;

        try {
            BufferedReader bReader = new BufferedReader(inputStreamReader);
            while((line = bReader.readLine()) != null) {
                jsonObject = (JSONObject) jsonParser.parse(line);
                jsonObjects.add(jsonObject);
            }


        } catch (FileNotFoundException e) {
            return null ;
        } catch (ParseException e) {
            return null;
        } catch (IOException e) {
            return null;
        }


        for (JSONObject j:jsonObjects) {
            if(j.get("message_type").toString().equals("MSG"))
                System.out.println(j.get("message_content").toString());
            }

        return jsonObjects;
    }

}


