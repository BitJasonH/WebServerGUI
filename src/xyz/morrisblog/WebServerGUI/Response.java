package xyz.morrisblog.WebServerGUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

class Response {
    private String resourceLocation;
    private int responseCode;

    static Response getNewResponseInstance() {
        return new Response();
    }

    void sendResourceToOutput(String rootDir, Request request, OutputStream responseStream) {
        resourceLocation = rootDir + request.getUri();
        File resource = new File(resourceLocation);

        if (!resource.exists()) {
            resource = new File(rootDir, "\\404.html");
            responseCode = 404;
        } else {
            responseCode = 200;
        }

        try (FileInputStream resourceStream = new FileInputStream(resource)) {
            int resourceLength = resourceStream.available();
            String message = getResponseHeader(
                    request.getHttpVersion(),
                    responseCode,
                    "text/html",
                    resourceLength
            );
            responseStream.write(message.getBytes());

            byte[] buffer = new byte[resourceLength];
            resourceStream.read(buffer);
            responseStream.write(buffer);
        } catch (IOException e) {
            System.out.println("Server Closed");
        }
    }

    private String getResponseHeader(String httpVersion, int responseCode, String contentType, int contentLength) {
        String responseCodeStr = null;
        switch (responseCode) {
            case 404:
                responseCodeStr = " 404 Not Found";
                break;
            case 200:
                responseCodeStr = " 200 OK";
                break;
        }
        return httpVersion + responseCodeStr + "\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + contentLength + "\r\n"
                + "\r\n";
    }
}
