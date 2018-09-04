package communicationprocessor.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component("jacksonWriterReader")
public class JacksonUtil {

	@Value("${save.dir:.}")
	String saveDir;

	public <T> void writeObjectToFile(T object, String filename) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		File file = new File((saveDir + System.getProperty("file.separator") + filename));

        if(!file.exists()){
            file.createNewFile();
        }

		// Object to JSON in file
		mapper.writeValue(file, object);


	}

	public <T> T readObjectFromFile(Class<T> clazz, String filename) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		File file = new File((saveDir + System.getProperty("file.separator") + filename));

		if(!file.exists()){
			throw new IOException("Unable to find kpis file!");
		}

		// JSON from file to Object
		return mapper.readValue(file, clazz);

	}

}
