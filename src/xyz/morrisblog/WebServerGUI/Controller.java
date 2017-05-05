package xyz.morrisblog.WebServerGUI;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;

public class Controller {
    private boolean isServerOn = false;

    @FXML
    private JFXTextField hostInput0;
    @FXML
    private JFXTextField hostInput1;
    @FXML
    private JFXTextField hostInput2;
    @FXML
    private JFXTextField hostInput3;
    @FXML
    private JFXTextField portInput;
    @FXML
    private JFXTextField rootInput;
    @FXML
    private JFXToggleButton startToggle;
    @FXML
    private TableView requestTable;
    @FXML
    private JFXTextArea responseDisplay;

    private Server server;

    @FXML
    public void initialize() {
        hostInput0.setOnKeyReleased(new CheckHostEventHandler());
        hostInput1.setOnKeyReleased(new CheckHostEventHandler());
        hostInput2.setOnKeyReleased(new CheckHostEventHandler());
        hostInput3.setOnKeyReleased(new CheckHostEventHandler());
    }

    @FXML
    void serverControl() {
        String customHost0 = hostInput0.getText();
        String customHost1 = hostInput1.getText();
        String customHost2 = hostInput2.getText();
        String customHost3 = hostInput3.getText();
        String customPort = portInput.getText();
        String customRoot = rootInput.getText();

        byte[] actualHost = new byte[]{127, 0, 0, 1};
        int actualPort = 3000;
        String actualRoot = "C:\\WebServer";

        if (!("".equals(customHost0))) {
            actualHost[0] = Byte.parseByte(customHost0);
        }
        if (!("".equals(customHost1))) {
            actualHost[1] = Byte.parseByte(customHost1);
        }
        if (!("".equals(customHost2))) {
            actualHost[2] = Byte.parseByte(customHost2);
        }
        if (!("".equals(customHost3))) {
            actualHost[3] = Byte.parseByte(customHost3);
        }

        if (!("".equals(customPort))) {
            actualPort = Integer.parseInt(customPort);
        }

        if (!("".equals(customRoot))) {
            actualRoot = customRoot;
        }

        isServerOn = !isServerOn;
        if (isServerOn) {
            server = Server.getNewServerByIp(actualPort, actualHost);
            server.setRootDir(actualRoot);
            server.start();
            responseDisplay.setText("On");
        } else {
            server.stopServer();
            responseDisplay.setText("Off");
        }
    }

    @FXML
    void checkPort() {
        String portStr = portInput.getText();
        if (!("".equals(portStr))) {
            int portNum = -1;
            try {
                portNum = Integer.parseInt(portStr);
            } catch (NumberFormatException e) {
                portNum = -1;
            } finally {
                if (portNum > 65535 || portNum < 0) {
                    portInput.setText("65535");
                }
            }
        }
    }

    private class CheckHostEventHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            String hostStr = ((JFXTextField) event.getSource()).getText();
            if (!("".equals(hostStr))) {
                int hostNum = -1;
                try {
                    hostNum = Integer.parseInt(hostStr);
                } catch (NumberFormatException e) {
                    hostNum = -1;
                } finally {
                    if (hostNum > 255 || hostNum < 0) {
                        ((JFXTextField) event.getSource()).setText("255");
                    }
                }
            }
        }
    }
}
