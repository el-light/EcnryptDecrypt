import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.*;

import javax.swing.*;
// import java.awt.GridBagLayout;
// import java.awt.GridBagConstraints;

// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
import java.io.File;
import javax.crypto.BadPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import javax.crypto.NoSuchPaddingException;

// import java.awt.dnd.DropTarget;
// import java.awt.dnd.DropTargetAdapter;
// import java.awt.dnd.DropTargetDropEvent;
// import java.awt.dnd.DnDConstants;
// import java.awt.datatransfer.DataFlavor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionDecryption {
    static SecretKey k;

    public static SecretKey encryptFile(String inputFilePath,String outputFilePath, String algorithm, int  keysize){
        SecretKey key = null; 
        try{
        //Generate a key
        KeyGenerator keyGenerator  = KeyGenerator.getInstance(algorithm); 
        keyGenerator.init(keysize);
        key = keyGenerator.generateKey(); 

        //Create a cipher 
        Cipher cipher = Cipher.getInstance(algorithm); 

        //Encrypt the text
        cipher.init(Cipher.ENCRYPT_MODE, key);

        //we use Stream to effctively manage memory
        try (FileInputStream fis = new FileInputStream(inputFilePath);
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

            byte[] buffer = new byte[1024];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, read);
       }
   }


    } catch (NoSuchPaddingException e) {
        JOptionPane.showMessageDialog(null, "Padding problem during the encryption process: " + e.getMessage());
    } catch (NoSuchAlgorithmException e) {
        JOptionPane.showMessageDialog(null, "The selected algorithm is not supported during the encryption process: " + e.getMessage());
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "An IO error occurred during the encryption process: " + e.getMessage());
    } catch (InvalidKeyException e) {
        JOptionPane.showMessageDialog(null, "Invalid key used during the encryption process: " + e.getMessage());
    }
    return key; 

}

public static void decryptFile(String outputFilePath, String output2FilePath ,String algorithm, SecretKey key) {
    try {
        Cipher cipher = Cipher.getInstance(algorithm); 
        cipher.init(Cipher.DECRYPT_MODE, key);
        
        try (FileInputStream fis = new FileInputStream(outputFilePath);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            FileOutputStream fos = new FileOutputStream(output2FilePath)){

            byte[] buffer = new byte[1024]; 
            int read; 

            while((read = cis.read(buffer)) != -1){
                fos.write(buffer, 0, read);
            }
            }
        

    } catch (NoSuchAlgorithmException e) {
        JOptionPane.showMessageDialog(null, "The selected algorithm is not supported.");
    } catch (InvalidKeyException e) {
        JOptionPane.showMessageDialog(null, "The decryption key does not match the encryption key.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "An error occurred while reading or writing the file.");
    }catch(NoSuchPaddingException e){
        JOptionPane.showMessageDialog(null, "An error occurred during the encryption or decryption process. The padding scheme specified is not available.");
    }
}
}
