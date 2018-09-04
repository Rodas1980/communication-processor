package communicationprocessor.utility;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpDownloadUtility {
    private static final int BUFFER_SIZE = 4096;
 
    /**
     * Downloads a file from a URL
     * @param fileURL HTTP URL of the file to be downloaded
     * @throws IOException
     */
    public static InputStreamReader downloadFile(String fileURL)
            throws IOException {
        File file ;
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader((inputStream));

            System.out.println("File downloaded");
            return  inputStreamReader;
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);

        }
        httpConn.disconnect();
        return  null;
    }
}