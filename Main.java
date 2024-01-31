import java.util.*;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import javax.crypto.NoSuchPaddingException;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DnDConstants;
import java.awt.datatransfer.DataFlavor;

 import javax.crypto.SecretKey;

public class Main {

     static SecretKey k; 
    public static void main(String[] args) {

//handling with GUI

        JFrame frame = new JFrame("File Encryption");
        frame.setSize(550, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel panel = new JPanel();
        frame.add(panel);


        // Set a layout manager
panel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();

// Add padding around components
int padding = 5;
panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

// Use labels and text fields for file paths
JLabel inputLabel = new JLabel("Input file:");
c.gridx = 0;
c.gridy = 0;
panel.add(inputLabel, c);

JTextField inputFile = new JTextField(20);
c.gridx = 1;
c.gridy = 0;
panel.add(inputFile, c);

JButton browseButton = new JButton("Browse");
c.gridx = 2;
c.gridy = 0;
panel.add(browseButton, c);

// Use combo boxes for options
JLabel aLabel = new JLabel("Algorithm:");
c.gridx = 0;
c.gridy = 1;
panel.add(aLabel, c);

JComboBox<String> algBox = new JComboBox<>(new String[] {"AES", "DES", "TripleDES"});
c.gridx = 1;
c.gridy = 1;
panel.add(algBox, c);


      
// a method to allow user to drag and drop the file in order 
// to write its path in the respective section

new DropTarget(panel, new DropTargetAdapter() {
    public void drop(DropTargetDropEvent dtde) {
        try {
            // Accept the drop first
            dtde.acceptDrop(DnDConstants.ACTION_COPY);
        
            // Get the files that are dropped as java.util.List
            List files = (List) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
        
            // Handle the files and write these files' path in inputFile field
            for (Object file : files) {
                inputFile.setText(((File) file).getAbsolutePath());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
});

        JLabel outputLabel = new JLabel("Output file for encrypted data: ");
        c.gridx = 0;
        c.gridy = 2;
        panel.add(outputLabel, c);
        
        JTextField outputFile = new JTextField(20);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(outputFile, c);

        JButton browseButton2 = new JButton("Browse");
        c.gridx = 2;
        c.gridy = 2;
        panel.add(browseButton2, c);
        
        JLabel outputLabel2 = new JLabel("Output file for decrypted data: ");
        c.gridx = 0;
        c.gridy = 3;
        panel.add(outputLabel2, c);

        JTextField outputFile2 = new JTextField(20);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(outputFile2, c);

        JButton browseButton3 = new JButton("Browse");
        c.gridx = 2;
        c.gridy = 3;
        panel.add(browseButton3, c);
       

        JLabel keysLabel = new JLabel("Key Size:");
        c.gridx = 0;
        c.gridy = 4;
        panel.add(keysLabel, c);
        
        JComboBox<Integer> keySizeBox = new JComboBox<>(new Integer[] {128, 192, 256});
        c.gridx = 1;
        c.gridy = 4;
        panel.add(keySizeBox, c);

        //buttons for encryption and decryption

        JButton button = new JButton("Encrypt");
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1.0; 
        c.gridwidth = 3;  // Span across 3 columns
        panel.add(button, c);

        JButton button2 = new JButton("Decrypt");
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 1.0; 
        c.gridwidth = 3;  // Span across 3 columns
        panel.add(button2, c);


        //handle what happens when the Browse button is tapped

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                //simple instance which allows user to choose the file
                JFileChooser fileChooser = new JFileChooser(); 

                //store the information whether the user chose the file or not
                int returnValue = fileChooser.showOpenDialog(null); 

                //if user selected the file
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    //we store the chosen file in variable and then copy its path into the 
                    //inputFile text field
                    File selectedFile = fileChooser.getSelectedFile();
                    inputFile.setText(selectedFile.getAbsolutePath());
                }
            }
        });


        //handling for second browse button
        browseButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser(); 
                int returnValue = fileChooser.showOpenDialog(null); 

                if(returnValue == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    inputFile.setText(selectedFile.getAbsolutePath());
                }
            }
        });


        //handling for third one
        browseButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser(); 
                int returnValue = fileChooser.showOpenDialog(null); 

                if(returnValue == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    inputFile.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        //when algorithm button is pressed we handle each case of choosen algorithm
        //Since each algorithm supports its specific key size
         algBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {   //when action is perfromed 
                String selectedAlg = (String) algBox.getSelectedItem();
                keySizeBox.removeAllItems();

                switch(selectedAlg){
                    case "AES":
                        keySizeBox.addItem(128);
                        keySizeBox.addItem(192);
                        keySizeBox.addItem(256);
                        break;
                    case "DES":
                        keySizeBox.addItem(56);
                        break;
                    case "TripleDES":
                        keySizeBox.addItem(112);
                        keySizeBox.addItem(168); 
                        break;
                }
               
            }
        
        });

        //handle the case when "Encrypt" button is tapped
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputFilePath = inputFile.getText();
                String outputFilePath = outputFile.getText();
                String algorithm =(String) algBox.getSelectedItem(); 
                Integer keySize = (Integer) keySizeBox.getSelectedItem();

                // Check if input file exists
                File file = new File(inputFilePath);
                if (!file.exists() || file.isDirectory()) {
                    JOptionPane.showMessageDialog(frame, "Input file does not exist");
                    return;
                }

                // Calling encryption method 
                 k = EncryptionDecryption.encryptFile(inputFilePath, outputFilePath, algorithm, keySize);
            }
        });

        //handle the case when "Decrypt" button is tapped
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String algorithm =(String) algBox.getSelectedItem(); 
                String outputFilePath = outputFile.getText();
                String output2FilePath = outputFile2.getText();

                //call the decrypt file method
                EncryptionDecryption.decryptFile(outputFilePath, output2FilePath, algorithm, k);
            }
        });

        frame.setVisible(true);
    }
}