package com.tecknobit.apimanager.apis;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.tecknobit.apimanager.annotations.Wrapper;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.zxing.BarcodeFormat.QR_CODE;
import static com.google.zxing.client.j2se.MatrixToImageConfig.BLACK;
import static com.google.zxing.client.j2se.MatrixToImageConfig.WHITE;
import static java.awt.Color.decode;

/**
 * The {@code QRCodeHelper} class is useful to manage the QRCode
 *
 * @author N7ghtm4r3 - Tecknobit
 * @implNote this class is based on the official <a href="https://github.com/zxing/zxing">zxing library</a>
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/QRCodeHelper.md">QRCodeHelper.md</a>
 * @since 2.0.4
 */
public class QRCodeHelper {

    /**
     * {@code HTML_REPLACER} replacer to use if you need to customize your html page
     */
    public static final String HTML_REPLACER = "<qrcode>";

    /**
     * {@code hosts} list of current active hosts
     */
    private static final ConcurrentHashMap<Integer, SocketManager> hosts = new ConcurrentHashMap<>();

    /**
     * Method to create a squared QRCode file
     *
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param squareDimension: dimensions of the square
     * @throws IOException     when an error is occurred during creation of the file
     * @throws WriterException when an error is occurred during creation of the QRCODE
     */
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
     */
    public <T> void createQRCode(T data, String path, int width, int height) throws IOException, WriterException {
        createQRCode(data, path, width, height, null, null);
    }

    /**
     * Method to create a squared QRCode file
     *
     * @param foregroundColor: color of the QRCode pattern
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param squareDimension: dimensions of the square
     * @throws IOException     when an error is occurred during creation of the file
     * @throws WriterException when an error is occurred during creation of the QRCODE
     */
    @Wrapper
    public <T> void createQRCode(String foregroundColor, T data, String path,
                                 int squareDimension) throws IOException, WriterException {
        createQRCode(foregroundColor, data, path, squareDimension, squareDimension);
    }

    /**
     * Method to create a QRCode file
     *
     * @param foregroundColor: color of the QRCode pattern
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param width:           width of the QRCode
     * @param height:          height of the QRCode
     * @throws IOException     when an error is occurred during creation of the file
     * @throws WriterException when an error is occurred during creation of the QRCODE
     */
    public <T> void createQRCode(String foregroundColor, T data, String path, int width,
                                 int height) throws IOException, WriterException {
        createQRCode(data, path, width, height, foregroundColor, null);
    }

    /**
     * Method to create a squared QRCode file
     *
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param squareDimension: dimensions of the square
     * @param backgroundColor: background color of the QRCode pattern
     * @throws IOException     when an error is occurred during creation of the file
     * @throws WriterException when an error is occurred during creation of the QRCODE
     */
    @Wrapper
    public <T> void createQRCode(T data, String path, int squareDimension,
                                 String backgroundColor) throws IOException, WriterException {
        createQRCode(data, path, squareDimension, squareDimension, backgroundColor);
    }

    /**
     * Method to create a QRCode file
     *
     * @param data:   data to create the QRCode
     * @param path:   path where create the file, included the suffix
     * @param width:  width of the QRCode
     * @param height: height of the QRCode
     * @param backgroundColor: background color of the QRCode pattern
     * @throws IOException     when an error is occurred during creation of the file
     * @throws WriterException when an error is occurred during creation of the QRCODE
     */
    public <T> void createQRCode(T data, String path, int width, int height,
                                 String backgroundColor) throws IOException, WriterException {
        createQRCode(data, path, width, height, null, backgroundColor);
    }

    /**
     * Method to create a squared QRCode file
     *
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param squareDimension: dimensions of the square
     * @param foregroundColor: color of the QRCode pattern
     * @param backgroundColor: background color of the QRCode pattern
     * @throws IOException     when an error is occurred during creation of the file
     * @throws WriterException when an error is occurred during creation of the QRCODE
     */
    @Wrapper
    public <T> void createQRCode(T data, String path, int squareDimension, String foregroundColor,
                                 String backgroundColor) throws IOException, WriterException {
        createQRCode(data, path, squareDimension, squareDimension, foregroundColor, backgroundColor);
    }

    /**
     * Method to create a QRCode file
     *
     * @param data:   data to create the QRCode
     * @param path:   path where create the file, included the suffix
     * @param width:  width of the QRCode
     * @param height: height of the QRCode
     * @param foregroundColor: color of the QRCode pattern
     * @param backgroundColor: background color of the QRCode pattern
     * @throws IOException     when an error is occurred during creation of the file
     * @throws WriterException when an error is occurred during creation of the QRCODE
     */
    public <T> void createQRCode(T data, String path, int width, int height, String foregroundColor,
                                 String backgroundColor) throws IOException, WriterException {
        if (path.contains(".")) {
            MatrixToImageWriter.writeToPath(new QRCodeWriter().encode(data.toString(), QR_CODE, width, height),
                    path.split("\\.")[1], Path.of(path), createQRConfig(foregroundColor, backgroundColor));
        } else
            throw new IllegalArgumentException("Path must specific its suffix");
    }

    /**
     * Method to read QRCode
     *
     * @param QRCodePath: path where find the QRCode
     * @return content of the QRCode as {@link String}
     * @throws IOException when an error is occurred during reading of the QRCODE
     */
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
     */
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
     */
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
     */
    public <T> void hostQRCode(int port, T data, String path, int width, int height, boolean perpetual) throws IOException {
        hostQRCode(port, data, path, width, height, perpetual, (String) null, null);
    }

    /**
     * Method to create and host a squared QRCode file
     *
     * @param foregroundColor: color of the QRCode pattern
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
     */
    @Wrapper
    public <T> void hostQRCode(String foregroundColor, int port, T data, String path, int squareDimension,
                               boolean perpetual) throws IOException {
        hostQRCode(foregroundColor, port, data, path, squareDimension, squareDimension, perpetual);
    }

    /**
     * Method to create and host a QRCode file
     *
     * @param foregroundColor: color of the QRCode pattern
     * @param port:            port where host the QRCode file
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param width:           width of the QRCode
     * @param height:          height of the QRCode
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
     */
    public <T> void hostQRCode(String foregroundColor, int port, T data, String path, int width, int height,
                               boolean perpetual) throws IOException {
        hostQRCode(port, data, path, width, height, perpetual, foregroundColor, null);
    }

    /**
     * Method to create and host a squared QRCode file
     *
     * @param port:            port where host the QRCode file
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param squareDimension: dimensions of the square
     * @param perpetual:       whether the hosting must accept requests perpetuity or accept a single one request
     * @param backgroundColor: background color of the QRCode pattern
     * @throws IOException when an error is occurred
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     */
    @Wrapper
    public <T> void hostQRCode(int port, T data, String path, int squareDimension, boolean perpetual,
                               String backgroundColor) throws IOException {
        hostQRCode(port, data, path, squareDimension, squareDimension, perpetual, backgroundColor);
    }

    /**
     * Method to create and host a QRCode file
     *
     * @param port:            port where host the QRCode file
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param width:           width of the QRCode
     * @param height:          height of the QRCode
     * @param perpetual:       whether the hosting must accept requests perpetuity or accept a single one request
     * @param backgroundColor: background color of the QRCode pattern
     * @throws IOException when an error is occurred
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     */
    public <T> void hostQRCode(int port, T data, String path, int width, int height, boolean perpetual,
                               String backgroundColor) throws IOException {
        hostQRCode(port, data, path, width, height, perpetual, (String) null, backgroundColor);
    }

    /**
     * Method to create and host a squared QRCode file
     *
     * @param port:            port where host the QRCode file
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param squareDimension: dimensions of the square
     * @param perpetual:       whether the hosting must accept requests perpetuity or accept a single one request
     * @param foregroundColor: color of the QRCode pattern
     * @param backgroundColor: background color of the QRCode pattern
     * @throws IOException when an error is occurred
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     */
    @Wrapper
    public <T> void hostQRCode(int port, T data, String path, int squareDimension, boolean perpetual,
                               String foregroundColor, String backgroundColor) throws IOException {
        hostQRCode(port, data, path, squareDimension, squareDimension, perpetual, foregroundColor, backgroundColor);
    }

    /**
     * Method to create and host a QRCode file
     *
     * @param port:            port where host the QRCode file
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param width:           width of the QRCode
     * @param height:          height of the QRCode
     * @param perpetual:       whether the hosting must accept requests perpetuity or accept a single one request
     * @param foregroundColor: color of the QRCode pattern
     * @param backgroundColor: background color of the QRCode pattern
     * @throws IOException when an error is occurred
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     */
    public <T> void hostQRCode(int port, T data, String path, int width, int height, boolean perpetual,
                               String foregroundColor, String backgroundColor) throws IOException {
        if (path.contains("."))
            hostQRCode(port, data, path, width, height, perpetual, (String) null, foregroundColor, backgroundColor);
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
     */
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
     */
    public <T> void hostQRCode(int port, T data, String path, int width, int height, boolean perpetual,
                               File htmlPage) throws IOException {
        hostQRCode(port, data, path, width, height, perpetual, htmlPage, null, null);
    }

    /**
     * Method to create and host a squared QRCode file
     *
     * @param foregroundColor: color of the QRCode pattern
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
     */
    @Wrapper
    public <T> void hostQRCode(String foregroundColor, int port, T data, String path, int squareDimension,
                               boolean perpetual, File htmlPage) throws IOException {
        hostQRCode(foregroundColor, port, data, path, squareDimension, squareDimension, perpetual, htmlPage);
    }

    /**
     * Method to create and host a QRCode file
     *
     * @param foregroundColor: color of the QRCode pattern
     * @param port:            port where host the QRCode file
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param width:           width of the QRCode
     * @param height:          height of the QRCode
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
     */
    public <T> void hostQRCode(String foregroundColor, int port, T data, String path, int width, int height,
                               boolean perpetual, File htmlPage) throws IOException {
        hostQRCode(port, data, path, width, height, perpetual, htmlPage, foregroundColor, null);
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
     * @param backgroundColor: background color of the QRCode pattern
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
     */
    @Wrapper
    public <T> void hostQRCode(int port, T data, String path, int squareDimension, boolean perpetual, File htmlPage,
                               String backgroundColor) throws IOException {
        hostQRCode(port, data, path, squareDimension, squareDimension, perpetual, htmlPage, backgroundColor);
    }

    /**
     * Method to create and host a QRCode file
     *
     * @param port:            port where host the QRCode file
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param width:           width of the QRCode
     * @param height:          height of the QRCode
     * @param perpetual:       whether the hosting must accept requests perpetuity or accept a single one request
     * @param htmlPage:        custom html page where insert the QRCode
     * @param backgroundColor: background color of the QRCode pattern
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
     */
    public <T> void hostQRCode(int port, T data, String path, int width, int height, boolean perpetual, File htmlPage,
                               String backgroundColor) throws IOException {
        hostQRCode(port, data, path, width, height, perpetual, htmlPage, backgroundColor, null);
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
     * @param foregroundColor: color of the QRCode pattern
     * @param backgroundColor: background color of the QRCode pattern
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
     */
    @Wrapper
    public <T> void hostQRCode(int port, T data, String path, int squareDimension, boolean perpetual,
                               File htmlPage, String foregroundColor, String backgroundColor) throws IOException {
        hostQRCode(port, data, path, squareDimension, squareDimension, perpetual, htmlPage, foregroundColor,
                backgroundColor);
    }

    /**
     * Method to create and host a QRCode file
     *
     * @param port:            port where host the QRCode file
     * @param data:            data to create the QRCode
     * @param path:            path where create the file, included the suffix
     * @param width:           width of the QRCode
     * @param height:          height of the QRCode
     * @param perpetual:       whether the hosting must accept requests perpetuity or accept a single one request
     * @param htmlPage:        custom html page where insert the QRCode
     * @param foregroundColor: color of the QRCode pattern
     * @param backgroundColor: background color of the QRCode pattern
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
     */
    public <T> void hostQRCode(int port, T data, String path, int width, int height, boolean perpetual,
                               File htmlPage, String foregroundColor, String backgroundColor) throws IOException {
        if (path.contains(".")) {
            String sHtml = new Scanner(htmlPage).useDelimiter("\\Z").next();
            if (sHtml.contains(HTML_REPLACER))
                hostQRCode(port, data, path, width, height, perpetual, sHtml, foregroundColor, backgroundColor);
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
     * @param foregroundColor: color of the QRCode pattern
     * @param backgroundColor: background color of the QRCode pattern
     * @throws IOException when an error is occurred
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     */
    private <T> void hostQRCode(int port, T data, String path, int width, int height, boolean perpetual,
                                String html, String foregroundColor, String backgroundColor) throws IOException {
        SocketManager socketManager = new SocketManager(false);
        socketManager.startListener(port, () -> {
            if (perpetual) {
                hosts.put(port, socketManager);
                while (socketManager.continueListening()) {
                    try {
                        hostQRCode(data, path, width, height, socketManager, html, foregroundColor, backgroundColor);
                        socketManager.closeCommunication();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                hostQRCode(data, path, width, height, socketManager, html, foregroundColor, backgroundColor);
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
     * @param foregroundColor: color of the QRCode pattern
     * @param backgroundColor: background color of the QRCode pattern
     * @apiNote <ul>
     * <li>
     * if you need access by localhost you can find the QRCode in localhost:{@code "port"}
     * </li>
     * <li>
     * if you need access by an external network you can find the QRCode in {@code "external_network_address"}:{@code "port"}
     * </li>
     * </ul>
     */
    private <T> void hostQRCode(T data, String path, int width, int height, SocketManager socketManager, String html,
                                String foregroundColor, String backgroundColor) {
        try {
            socketManager.acceptRequest();
            String suffix = "." + path.split("\\.")[1];
            File tmpQR = File.createTempFile(path.replace(suffix, ""), suffix);
            MatrixToImageWriter.writeToStream(new QRCodeWriter().encode(data.toString(), QR_CODE, width, height),
                    suffix.replace(".", ""), new FileOutputStream(tmpQR), createQRConfig(foregroundColor,
                            backgroundColor));
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
     * Method to create a matrix config for customize the QRCode
     *
     * @param foregroundColor: color of the QRCode pattern
     * @param backgroundColor: background color of the QRCode pattern
     * @return matrix config as {@link MatrixToImageConfig}
     */
    private MatrixToImageConfig createQRConfig(String foregroundColor, String backgroundColor) {
        MatrixToImageConfig config;
        if (foregroundColor == null && backgroundColor == null)
            config = new MatrixToImageConfig();
        else {
            if (foregroundColor != null && backgroundColor != null)
                config = new MatrixToImageConfig(decode(foregroundColor).getRGB(), decode(backgroundColor).getRGB());
            else if(backgroundColor == null)
                config = new MatrixToImageConfig(decode(foregroundColor).getRGB(), WHITE);
            else
                config = new MatrixToImageConfig(BLACK, decode(backgroundColor).getRGB());
        }
        return config;
    }

    /**
     * Method to stop a single hosting <br>
     * Any params required
     *
     * @throws IllegalStateException when there are multiple listeners which can be stopped
     * @throws IOException           when an error occurred
     */
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
     */
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
     */
    public void stopAllHosting() throws IOException {
        for (SocketManager manager : hosts.values()) {
            manager.getActiveServerSocket().close();
            manager.stopListener();
        }
        hosts.clear();
    }

}
