package com.tecknobit.apimanager.apis;

import java.io.*;

/**
 * The {@code ResourcesHelper} class is useful to manage the {@code "resources"} folder and its contents
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/ResourcesHelper.md">ResourcesHelper.md</a>
 * @since 2.1.1
 **/
public class ResourcesHelper {

    /**
     * Method to create a resource from a stream
     *
     * @param context:      context to invoke this method
     * @param resourceName: the name of the resource to fetch
     * @return resource created as {@link File}
     **/
    public static <T> File getResourceFromStream(Class<T> context, String resourceName) throws IOException {
        File file = File.createTempFile("apimanager" + System.currentTimeMillis(), "");
        file.deleteOnExit();
        try (FileWriter fileWriter = new FileWriter(file)) {
            InputStreamReader stream;
            try {
                stream = new InputStreamReader(context.getResourceAsStream(resourceName));
            } catch (NullPointerException e) {
                stream = new InputStreamReader(context.getClassLoader().getResourceAsStream(resourceName));
            }
            BufferedReader bufferedReader = new BufferedReader(stream);
            String line;
            while ((line = bufferedReader.readLine()) != null)
                fileWriter.write(line);
        }
        return file;
    }

}
