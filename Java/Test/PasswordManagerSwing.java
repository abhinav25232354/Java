import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ArrayList;

public class PasswordManagerSwing {

    private static final String FILE_NAME = "passwords.dat";
    private static SecretKey secretKey;

    // Swing components
    private JFrame frame;
    private JList<String> passwordList;
    private DefaultListModel<String> listModel;
    private JTextField serviceField;
    private JTextField passwordField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                secretKey = generateSecretKey();
                new PasswordManagerSwing().createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // AES key size
        return keyGen.generateKey();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Create the list model and list to display services
        listModel = new DefaultListModel<>();
        passwordList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(passwordList);
        passwordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(listScrollPane, BorderLayout.CENTER);

        // Text fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Service:"));
        serviceField = new JTextField();
        inputPanel.add(serviceField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        inputPanel.add(passwordField);

        JButton addButton = new JButton("Add Password");
        addButton.addActionListener(this::addPassword);
        inputPanel.add(addButton);

        JButton retrieveButton = new JButton("Retrieve Password");
        retrieveButton.addActionListener(this::retrievePassword);
        inputPanel.add(retrieveButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Load passwords from file when the application starts
        loadStoredPasswords();

        frame.setVisible(true);
    }

    private void addPassword(ActionEvent event) {
        String service = serviceField.getText();
        String password = passwordField.getText();

        if (service.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Service and Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String encryptedPassword = encrypt(password);
            savePassword(service, encryptedPassword);
            listModel.addElement(service); // Update the list
            serviceField.setText(""); // Clear input fields
            passwordField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error saving password: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void retrievePassword(ActionEvent event) {
        String selectedService = passwordList.getSelectedValue();
        if (selectedService == null) {
            JOptionPane.showMessageDialog(frame, "Please select a service from the list.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String encryptedPassword = getPassword(selectedService);
            if (encryptedPassword != null) {
                String decryptedPassword = decrypt(encryptedPassword);
                JOptionPane.showMessageDialog(frame, "Password for " + selectedService + ": " + decryptedPassword, "Password Retrieved", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No password found for this service.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error retrieving password: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void savePassword(String service, String encryptedPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(service + ":" + encryptedPassword);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving password: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getPassword(String service) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(service)) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading passwords: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private static String encrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return bytesToHex(encryptedBytes);
    }

    private static String decrypt(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = hexToBytes(encryptedPassword);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    private static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

    private void loadStoredPasswords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String service = line.split(":")[0];
                listModel.addElement(service);
            }
        } catch (IOException e) {
            // If the file doesn't exist or an error occurs, simply return.
        }
    }
}
