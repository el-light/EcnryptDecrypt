 # File Encryption and Decryption

This is a Java application that allows users to encrypt and decrypt files using different algorithms and key sizes. The application provides a simple GUI for user interaction.

## Features

- Supports AES, DES, and TripleDES encryption algorithms.
- Allows key sizes of 128, 192, and 256 for AES, 56 for DES, and 112 and 168 for TripleDES.
- Provides a drag-and-drop feature for selecting files.
- Allows users to browse and select files for encryption and decryption.

## How to Use

1. Run the `Main.java` file to start the application.
2. In the GUI, select the input file you want to encrypt.
3. Choose the encryption algorithm and key size.
4. Select the output file for the encrypted data.
5. Click the "Encrypt" button to encrypt the file.
6. To decrypt a file, select the encrypted file as the input file, select the output file for the decrypted data, and click the "Decrypt" button.

## Dependencies

This project uses the Java Swing library for the GUI and the Java Cryptography Extension (JCE) for the encryption and decryption functionality.

## License

This project is licensed under the terms of the GPL-3.0 license.
