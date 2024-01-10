package com.tecknobit.apimanager.apis;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ResourcesUtils {

    private static final String RESOURCES_FOLDER_PATH = "/resources/";

    public static <T> InputStream getResourceStream(String pathname, Class<T> context) throws IOException {
        InputStream resource = context.getClassLoader().getResourceAsStream(pathname);
        if (resource == null)
            throw new IOException(pathname + " not found!");
        return resource;
    }

    /**
     * Method to get a file from the resources folder
     *
     * @param pathname: the pathname of the file to get from the resources folder
     * @param context:  the context where the function has been invoked
     * @return file from the resources folder as {@link File}
     * @throws IOException when an error occurred during the reading of the file
     */
    public static <T> File getResource(String pathname, Class<T> context) throws IOException {
        //return new File(getResourceStream(pathname, context))
        System.out.println(context.getClassLoader().getResource(RESOURCES_FOLDER_PATH + pathname).getFile());
        InputStream inputStream = context.getClassLoader().getResourceAsStream(pathname);
        if (inputStream == null)
            throw new IOException(pathname + " not found!");
        String text = new String(inputStream.readAllBytes(), UTF_8);

        //System.out.println(text);
        /*BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder resourceContent = new StringBuilder();
        String line;
        while ((line = buffer.readLine()) != null)
            resourceContent.append(line);
        try (FileWriter fileWriter = new FileWriter(pathname)) {
            fileWriter.write(resourceContent.toString());
            fileWriter.flush();
        }*/
        return new File("resources\\" + pathname);
    }

    public static <T> String getResourceContent(String pathname, Class<T> context) throws IOException {
        return new String(getResourceStream(pathname, context).readAllBytes(), UTF_8);
    }

}
