package com.tecknobit.apimanager.apis;

import java.io.*;

import static java.util.Objects.requireNonNull;

public class ResourcesHelper {

    public static <T> File getResourceFromStream(Class<T> context, String resourceName) throws IOException {
        File file = File.createTempFile("apimanager" + System.currentTimeMillis(), "");
        file.deleteOnExit();
        try (FileWriter fileWriter = new FileWriter(file)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(requireNonNull(context
                    .getClassLoader().getResourceAsStream(resourceName))));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                fileWriter.write(line);
        }
        return file;
    }

}
