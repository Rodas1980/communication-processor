package communicationprocessor.utility;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class JsonDeparserUtil {

    public static LinkedList<JSONObject> getJsonObjectsFromFile(InputStreamReader inputStreamReader) throws Exception{

        LinkedList<JSONObject> jsonObjects = new LinkedList<>();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        String line = null;

        BufferedReader bReader = new BufferedReader(inputStreamReader);
            while((line = bReader.readLine()) != null) {
                jsonObject = (JSONObject) jsonParser.parse(line);
                jsonObjects.add(jsonObject);
            }


        return jsonObjects;
    }

}


