package xyz.morrisblog.WebServerGUI;

import java.io.InputStream;
import java.util.Scanner;

enum HttpMethod {OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT}

class Request {
    private HttpMethod method;
    private String uri;
    private String httpVersion;
    private String host;
    private String connection;
    private String userAgent;
    private String accept;
    private String acceptEncoding;
    private String acceptLanguage;
    private int DNT;

    /**
     * Parse fields from request
     *
     * @param requestStream input stream which contains request from client
     */
    void parse(InputStream requestStream) {
        Scanner in = new Scanner(requestStream);
        while (in.hasNextLine()) {
            String tempStr = in.nextLine();
//            System.out.println(tempStr);
            if ("".equals(tempStr)) {
                break;
            }
            String[] tempArr = tempStr.split(": ");
            if (tempArr.length == 1 && tempArr[0].length() != 0) {
                tempArr = tempStr.split(" ");
                method = HttpMethod.valueOf(tempArr[0]);
                uri = tempArr[1];
                httpVersion = tempArr[2];
                System.out.println(uri);
            } else {
                String index = tempArr[0];
                if ("Host".equals(index)) {
                    host = tempArr[1];
//                    System.out.println(host);
                } else if ("Connection".equals(index)) {
                    connection = tempArr[1];
//                    System.out.println(connection);
                } else if ("User-Agent".equals(index)) {
                    userAgent = tempArr[1];
//                    System.out.println(userAgent);
                } else if ("Accept".equals(index)) {
                    accept = tempArr[1];
//                    System.out.println(accept);
                } else if ("Accept-Encoding".equals(index)) {
                    acceptEncoding = tempArr[1];
//                    System.out.println(acceptEncoding);
                } else if ("Accept-Language".equals(index)) {
                    acceptLanguage = tempArr[1];
//                    System.out.println(acceptLanguage);
                } else if ("DNT".equals(index)) {
                    DNT = Integer.parseInt(tempArr[1]);
//                    System.out.println(DNT);
                } else {

                }
            }
        }
        System.out.println("OVER");
    }


    public HttpMethod getMethod() {
        return method;
    }

    String getUri() {
        return uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public String getAccept() {
        return accept;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getConnection() {
        return connection;
    }

    public String getHost() {
        return host;
    }
}
