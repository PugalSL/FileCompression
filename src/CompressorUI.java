import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompressorUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel homePanel, compressPanel, decompressPanel;

    private JTextField sourceFileField = new JTextField(35);
    private JTextField destinationFileField = new JTextField(45);
    private JTextField sourceFileField1 = new JTextField(45);
    private JTextField destinationFileField1 = new JTextField(45);

    public CompressorUI() {
        super("File Compressor");
        setBounds(100, 100, 750, 200);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Home Panel
        homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
        JButton compressButton = new JButton("Compress a File      ");
        JButton decompressButton = new JButton("Decompress a File");
        homePanel.add(compressButton);
        homePanel.add(decompressButton);

        // Compress Panel
        compressPanel = new JPanel();
        compressPanel.setLayout(new FlowLayout());
        JButton sourceFileChooser = new JButton("Browse");
        JButton destinationFileChooser = new JButton("Browse");
        JLabel sourceFileLabel = new JLabel("Source File Name: ");
        JLabel destinationFileLabel = new JLabel("Destination Folder Name: ");
        JButton compress = new JButton("Compress");
        JButton backButton1 = new JButton("Go Back");
        JLabel message = new JLabel("");
        message.setForeground(new Color(18, 181, 34));
        compress.setBackground(new Color(69, 64, 248));

        compressPanel.add(sourceFileLabel);
        compressPanel.add(sourceFileField);
        compressPanel.add(sourceFileChooser);
        compressPanel.add(destinationFileLabel);
        compressPanel.add(destinationFileField);
        compressPanel.add(destinationFileChooser);
        compressPanel.add(compress);
        compressPanel.add(message);
        compressPanel.add(backButton1);

        // Decompress Panel
        decompressPanel = new JPanel();
        decompressPanel.setLayout(new FlowLayout());
        JButton sourceFileChooser1 = new JButton("Browse");
        JButton destinationFileChooser1 = new JButton("Browse");
        JLabel sourceFileLabel1 = new JLabel("Source File Name: ");
        JLabel destinationFileLabel1 = new JLabel("Destination Folder Name: ");
        JButton decompress = new JButton("Decompress");
        JButton backButton2 = new JButton("Go Back");
        JLabel message1 = new JLabel("");
        message1.setForeground(new Color(21, 67, 129));
        decompress.setBackground(new Color(69, 64, 248));

        decompressPanel.add(sourceFileLabel1);
        decompressPanel.add(sourceFileField1);
        decompressPanel.add(sourceFileChooser1);
        decompressPanel.add(destinationFileLabel1);
        decompressPanel.add(destinationFileField1);
        decompressPanel.add(destinationFileChooser1);
        decompressPanel.add(decompress);
        decompressPanel.add(message1);
        decompressPanel.add(backButton2);

        // Compress action listener
        compress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sourcePath = sourceFileField.getText();
                String base = sourcePath.substring(0, sourcePath.lastIndexOf('.'));
                base = base.substring(base.lastIndexOf('\\') + 1);
                String destPath = destinationFileField.getText() + "\\" + base + "-comp.txt";
                HuffCompression.compress(sourcePath, destPath);
                message.setText("Compressed successfully");
            }
        });

        // Decompress action listener
        decompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sourcePath = sourceFileField1.getText();
                String base = sourcePath.substring(0, sourcePath.lastIndexOf('-'));
                String base1 = base.substring(base.lastIndexOf('\\') + 1);
                String destPath = destinationFileField1.getText() + "\\" + base1 + "-decomp.txt";
                HuffCompression.decompress(sourcePath, destPath);
                message1.setText("Decompressed successfully");
            }
        });

        // "Go Back" Button Listeners
        backButton1.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        backButton2.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        // Browse for source file for compression
        sourceFileChooser.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser("D:\\Java");
            int userChoice = jfc.showOpenDialog(CompressorUI.this);
            if (userChoice == JFileChooser.APPROVE_OPTION) {
                sourceFileField.setText(jfc.getSelectedFile().getPath());
            } else {
                sourceFileField.setText("No File Selected");
            }
        });

        // Browse for destination folder for compression
        destinationFileChooser.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser("D:\\Java");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int userChoice = jfc.showOpenDialog(CompressorUI.this);
            if (userChoice == JFileChooser.APPROVE_OPTION) {
                destinationFileField.setText(jfc.getSelectedFile().getPath());
            } else {
                destinationFileField.setText("No Folder Selected");
            }
        });

        // Browse for source file for decompression
        sourceFileChooser1.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser("D:\\Java");
            int userChoice = jfc.showOpenDialog(CompressorUI.this);
            if (userChoice == JFileChooser.APPROVE_OPTION) {
                sourceFileField1.setText(jfc.getSelectedFile().getPath());
            } else {
                sourceFileField1.setText("No File Selected");
            }
        });

        // Browse for destination folder for decompression
        destinationFileChooser1.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser("D:\\Java");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int userChoice = jfc.showOpenDialog(CompressorUI.this);
            if (userChoice == JFileChooser.APPROVE_OPTION) {
                destinationFileField1.setText(jfc.getSelectedFile().getPath());
            } else {
                destinationFileField1.setText("No Folder Selected");
            }
        });

        mainPanel.add(homePanel, "Home");
        mainPanel.add(compressPanel, "Compress");
        mainPanel.add(decompressPanel, "Decompress");

        cardLayout.show(mainPanel, "Home");

        compressButton.addActionListener(e -> cardLayout.show(mainPanel, "Compress"));
        decompressButton.addActionListener(e -> cardLayout.show(mainPanel, "Decompress"));

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CompressorUI::new);
    }
}
