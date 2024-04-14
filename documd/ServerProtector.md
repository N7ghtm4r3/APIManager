## ServerProtector

### Usage/Examples

#### Implementation in your backend

```java
public class Backend {

  /**
   * Main method to start the backend
   *
   * @param args: custom arguments to share with the {@link #protector}
   * @apiNote the arguments scheme:
   * <ul>
   *     <li>
   *         <b>rss</b> -> launch your java application with "rss" to recreate the server secret <br>
   *          e.g java -jar Backend.jar rss
   *     </li>
   *     <li>
   *         <b>dss</b> -> launch your java application with "dss" to delete the current server secret <br>
   *          e.g java -jar Backend.jar dss
   *      </li>
   *      <li>
   *         <b>dssi</b> -> launch your java application with "dssi" to delete the current server secret and interrupt
   *         the current workflow of the server <br>
   *         e.g java -jar Backend.jar dssi
   *      </li>
   * </ul>
   */
  public static void main(String[] args) throws NoSuchAlgorithmException, SaveData {
    ServerProtector protector = new ServerProtector("path_to_storage_the_server_secret",
            "message_to_print_when_the_server_secret_has_been_generated");

    // launche the protector service
    protector.launch(args);

    // your code here

  }

}
```

#### Launch the service

When you have to start the service you will have different scenarios:

- At the first launch the server will be interrupted and will be thrown the
  **SaveData** exception to store the server secret to manage the user accesses to
  the server, share it **only to the users that you retains allowed to access to your server**
  ``` java
  Exception in thread "main" com.tecknobit.apimanager.exceptions.SaveData: Note: is not an error, but is an alert!
  Please you should safely save: the_server_secret_generated your_saveMessage
  ```
- If is not the first launch the service will start directly
- If you need to recreate the server secret you need to launch the service with the **rss** command like this:
  ``` java
  java -jar Backend.jar rss // this will generate a new server secret overwriting the current server secret
  ```
- If you need to delete the server secret, just note that when the service will be launched again will be generated a
  new
  server secret to work correctly, you need to launch the service with the **dss** or **dssi** command like this:
  ``` java
  // dss command
  java -jar Backend.jar dss // this will delete the current server secret
  
   // dssi command
  java -jar Backend.jar dssi // this will delete the current server secret and interrupts the server workflow right next
  ```

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/APIManager/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

[![](https://jitpack.io/v/N7ghtm4r3/APIManager.svg)](https://jitpack.io/#N7ghtm4r3/APIManager)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                        | Network  |
|-----------------------------------------------------------------------------------------------------|------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**         | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4** | Ethereum |

If you want support project and developer with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">
PayPal</a>

Copyright © 2024 Tecknobit
