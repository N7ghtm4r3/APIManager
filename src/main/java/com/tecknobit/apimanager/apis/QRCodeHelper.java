package com.tecknobit.apimanager.apis;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.tecknobit.apimanager.annotations.Wrapper;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.zxing.BarcodeFormat.QR_CODE;

/**
 * The {@code QRCodeHelper} class is useful to manage the QRCode
 *
 * @author N7ghtm4r3 - Tecknobit
 * @implNote this class is based on the official <a href="https://github.com/zxing/zxing">zxing library</a>
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/QRCodeHelper.md">QRCodeHelper.md</a>
 * @since 2.0.4
 **/
public class QRCodeHelper {

    /**
     * {@code HTML_REPLACER} replacer to use if you need to use these methods:
     * <ul>
     *     <li>
     *         {@link #hostQRCode(int, Object, String, int, boolean, File)}
     *     </li>
     *     <li>
     *         {@link #hostQRCode(int, Object, String, int, int, boolean, File)}
     *     </li>
     * </ul>
     **/
    public static final String HTML_REPLACER = "<qrcode>";

    /**
     * {@code hosts} list of current active hosts
     **/
    private static final ConcurrentHashMap<Integer, SocketManager> hosts = new ConcurrentHashMap<>();

    /**
     * Method to create a squared QRCode file
     *
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param squareDimension: dimensions of the square
     * @throws IOException     when an error is occurred during creation of the file
     * @throws WriterException when an error is occurred during creation of the QRCODE
     **/
    @Wrapper
    public <T> void createQRCode(T data, String path, int squareDimension) throws IOException, WriterException {
        createQRCode(data, path, squareDimension, squareDimension);
    }

    /**
     * Method to create a QRCode file
     *
     * @param data:   data to create the QRCode
     * @param path:   path where create the file, included the suffix
     * @param width:  width of the QRCode
     * @param height: height of the QRCode
     * @throws IOException     when an error is occurred during creation of the file
     * @throws WriterException when an error is occurred during creation of the QRCODE
     **/
    public <T> void createQRCode(T data, String path, int width, int height) throws IOException, WriterException {
        if (path.contains(".")) {
            MatrixToImageWriter.writeToPath(new QRCodeWriter().encode(data.toString(), QR_CODE, width, height),
                    path.split("\\.")[1], Path.of(path));
        } else
            throw new IllegalArgumentException("Path must specific its suffix");
    }

    /**
     * Method to read QRCode
     *
     * @param QRCodePath: path where find the QRCode
     * @return content of the QRCode as {@link String}
     * @throws IOException when an error is occurred during reading of the QRCODE
     **/
    @Wrapper
    public String readQRCode(String QRCodePath) throws IOException {
        return readQRCode(new File(QRCodePath));
    }

    /**
     * Method to read QRCode
     *
     * @param QRCode: QRCode file to read
     * @return content of the QRCode as {@link String}
     * @throws IOException when an error is occurred during reading of the QRCODE
     **/
    public String readQRCode(File QRCode) throws IOException {
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(QRCode))));
        try {
            return new MultiFormatReader().decode(bitmap).getText();
        } catch (NotFoundException e) {
            throw new IOException("Invalid QR-Code");
        }
    }

    /**
     * Method to create and host a squared QRCode file
     *
     * @param port:            port where host the QRCode file
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param squareDimension: dimensions of the square
     * @param perpetual:       whether the hosting must accept requests perpetuity or accept a single one request
     * @throws IOException when an error is occurred
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     **/
    @Wrapper
    public <T> void hostQRCode(int port, T data, String path, int squareDimension, boolean perpetual) throws IOException {
        hostQRCode(port, data, path, squareDimension, squareDimension, perpetual);
    }

    /**
     * Method to create and host a QRCode file
     *
     * @param port:      port where host the QRCode file
     * @param data:      data to create the QRCode
     * @param path:      path where create the file, included the suffix
     * @param width:     width of the QRCode
     * @param height:    height of the QRCode
     * @param perpetual: whether the hosting must accept requests perpetuity or accept a single one request
     * @throws IOException when an error is occurred
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     **/
    public <T> void hostQRCode(int port, T data, String path, int width, int height, boolean perpetual) throws IOException {
        if (path.contains("."))
            hostQRCode(port, data, path, width, height, perpetual, (String) null);
        else
            throw new IllegalArgumentException("Path must specific its suffix");
    }

    /**
     * Method to create and host a squared QRCode file
     *
     * @param port:            port where host the QRCode file
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param squareDimension: dimensions of the square
     * @param perpetual:       whether the hosting must accept requests perpetuity or accept a single one request
     * @param htmlPage:        custom html page where insert the QRCode
     * @throws IOException when an error is occurred
     * @implNote to successfully realize that <b>you must include in a custom section of your page this tag: </b>
     * {@link #HTML_REPLACER}
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     **/
    @Wrapper
    public <T> void hostQRCode(int port, T data, String path, int squareDimension, boolean perpetual,
                               File htmlPage) throws IOException {
        hostQRCode(port, data, path, squareDimension, squareDimension, perpetual, htmlPage);
    }

    /**
     * Method to create and host a QRCode file
     *
     * @param port:      port where host the QRCode file
     * @param data:      data to create the QRCode
     * @param path:      path where create the file, included the suffix
     * @param width:     width of the QRCode
     * @param height:    height of the QRCode
     * @param perpetual: whether the hosting must accept requests perpetuity or accept a single one request
     * @param htmlPage:  custom html page where insert the QRCode
     * @throws IOException when an error is occurred
     * @implNote to successfully realize that <b>you must include in a custom section of your page this tag: </b>
     * {@link #HTML_REPLACER}
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     **/
    public <T> void hostQRCode(int port, T data, String path, int width, int height, boolean perpetual,
                               File htmlPage) throws IOException {
        if (path.contains(".")) {
            StringBuilder html = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(htmlPage));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                html.append(line);
            String sHtml = html.toString();
            if (sHtml.contains(HTML_REPLACER))
                hostQRCode(port, data, path, width, height, perpetual, sHtml);
            else
                throw new IllegalArgumentException("To customize the html page you must include the <qrcode> tag");
        } else
            throw new IllegalArgumentException("Path must specific its suffix");
    }

    /**
     * Method to create and host a QRCode file
     *
     * @param port:      port where host the QRCode file
     * @param data:      data to create the QRCode
     * @param path:      path where create the file, included the suffix
     * @param width:     width of the QRCode
     * @param height:    height of the QRCode
     * @param perpetual: whether the hosting must accept requests perpetuity or accept a single one request
     * @param html:      html text to create the host page
     * @throws IOException when an error is occurred
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     **/
    private <T> void hostQRCode(int port, T data, String path, int width, int height, boolean perpetual,
                                String html) throws IOException {
        SocketManager socketManager = new SocketManager(false);
        socketManager.startListener(port, () -> {
            if (perpetual) {
                hosts.put(port, socketManager);
                while (socketManager.continueListening()) {
                    try {
                        hostQRCode(data, path, width, height, socketManager, html);
                        socketManager.closeCommunication();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                hostQRCode(data, path, width, height, socketManager, html);
                socketManager.stopListener();
            }
        });
    }

    /**
     * Method to create and host a QRCode file
     *
     * @param data:          data to create the QRCode
     * @param path:          path where create the file, included the suffix
     * @param width:         width of the QRCode
     * @param height:        height of the QRCode
     * @param socketManager: manager of the hosting
     * @param html:          html text to create the host page
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     **/
    private <T> void hostQRCode(T data, String path, int width, int height, SocketManager socketManager, String html) {
        try {
            System.out.println(SocketManager.getIpAddress(socketManager.acceptRequest()));
            String suffix = "." + path.split("\\.")[1];
            File tmpQR = File.createTempFile(path.replace(suffix, ""), suffix);
            MatrixToImageWriter.writeToStream(new QRCodeWriter().encode(data.toString(), QR_CODE, width, height),
                    suffix.replace(".", ""), new FileOutputStream(tmpQR));
            String content;
            if (html == null) {
                content = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n\n" +
                        "<html>\r\n" +
                        "<body>\r\n" +
                        "<center>\r\n" +
                        "<img src=" + APIRequest.createDataURIScheme(tmpQR) +
                        " width=" + width + " height=" + height + ">\r\n" +
                        "</center>\r\n" +
                        "</body>\r\n" +
                        "</html>\r\n";
            } else {
                content = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n\n" +
                        html.replace(HTML_REPLACER, "<img src=" + APIRequest.createDataURIScheme(tmpQR) +
                                " width=" + width + " height=" + height + ">");
            }
            socketManager.writeContent(content);
        } catch (Exception e) {
            if (!e.getLocalizedMessage().contains("Socket closed"))
                throw new RuntimeException(e);
        }
    }

    /**
     * Method to stop a single hosting <br>
     * Any params required
     *
     * @throws IllegalStateException when there are multiple listeners which can be stopped
     * @throws IOException           when an error occurred
     **/
    @Wrapper
    public void stopHosting() throws IOException {
        if (hosts.size() == 1)
            stopHostingOn(hosts.keys().nextElement());
        else
            throw new IllegalStateException("There are multiple listeners which can be stopped, you must insert the port");
    }

    /**
     * Method to stop a single hosting
     *
     * @param port: port of the host to stop
     * @throws IOException           when an error occurred
     * @throws IllegalStateException when there are no-any host on that port found
     **/
    public void stopHostingOn(int port) throws IOException {
        SocketManager socketManager = hosts.get(port);
        if (socketManager != null) {
            socketManager.getActiveServerSocket().close();
            socketManager.stopListener();
            hosts.remove(port);
        } else
            throw new IllegalStateException("No-any host on that port found");
    }

    /**
     * Method to stop all the active hosting<br>
     * Any params required
     *
     * @throws IOException when an error occurred
     **/
    public void stopAllHosting() throws IOException {
        for (SocketManager manager : hosts.values()) {
            manager.getActiveServerSocket().close();
            manager.stopListener();
        }
        hosts.clear();
    }

}
