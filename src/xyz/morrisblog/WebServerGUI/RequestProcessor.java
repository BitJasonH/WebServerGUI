package xyz.morrisblog.WebServerGUI;

import java.io.InputStream;
import java.io.OutputStream;

public class RequestProcessor implements Runnable {
    private InputStream requestStream;
    private OutputStream responseStream;
    private String workingDir;
    private Request request;
    private Response response;

    static RequestProcessor getNewRequestContainer() {
        return new RequestProcessor();
    }

    void setRequestStream(InputStream requestStream) {
        this.requestStream = requestStream;
    }

    void setResponseStream(OutputStream responseStream) {
        this.responseStream = responseStream;
    }

    void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    @Override
    public void run() {
        System.out.println("New Thread established");
        request = new Request();
        request.parse(requestStream);
        response = Response.getNewResponseInstance();
        response.sendResourceToOutput(workingDir, request, responseStream);
    }
}
