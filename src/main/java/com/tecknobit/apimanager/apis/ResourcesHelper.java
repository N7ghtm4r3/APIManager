package com.tecknobit.apimanager.apis;

import java.io.*;

import static java.util.Objects.requireNonNull;

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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(requireNonNull(
                    ResourcesHelper.class.getClassLoader().getResourceAsStream(resourceName))
            ));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                fileWriter.write(line);
        }
        return file;
    }

}
