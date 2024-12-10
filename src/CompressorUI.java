import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompressorUI extends JFrame
{
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel homePanel, compressPanel, decompressPanel;

    private JTextField sourceFileField = new JTextField(35);
    private JTextField destinationFileField = new JTextField(45);

    private JTextField sourceFileField1 = new JTextField(45);
    private JTextField destinationFileField1 = new JTextField(45);

    public CompressorUI() throws HeadlessException
    {
        super("File Compressor");
        setBounds(100, 100, 750, 150);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50)); // FlowLayout with spacing
        JButton compressButton = new JButton("Compress a File");
        JButton decompressButton = new JButton("Decompress a File");
        homePanel.add(compressButton);
        homePanel.add(decompressButton);

        compressPanel = new JPanel();
        compressPanel.setLayout(new FlowLayout());

        decompressPanel = new JPanel();
        decompressPanel.setLayout(new FlowLayout());

        JButton sourceFileChooser = new JButton("Browse");
        JButton destinationFileChooser = new JButton("Browse");
        JLabel sourceFileLabel = new JLabel("Source File Name: ");
        JLabel destinationFileLabel = new JLabel("Destination Folder Name: ");
        JButton compress = new JButton("Compress");
        JLabel message = new JLabel("");
        message.setForeground(new Color(18, 181, 34));
        compress.setBackground(new Color(69, 64, 248));
        sourceFileField.setFont(new Font("Verdana", Font.ITALIC, 16));
        compressPanel.add(sourceFileLabel);
        compressPanel.add(sourceFileField);
        compressPanel.add(sourceFileChooser);
        compressPanel.add(destinationFileLabel);
        compressPanel.add(destinationFileField);
        compressPanel.add(destinationFileChooser);
        compressPanel.add(compress);
        compressPanel.add(message);

        JButton sourceFileChooser1 = new JButton("Browse");
        JButton destinationFileChooser1 = new JButton("Browse");
        JLabel sourceFileLabel1 = new JLabel("Source File Name: ");
        JLabel destinationFileLabel1 = new JLabel("Destination Folder Name: ");
        JButton decompress = new JButton("Decompress");
        JLabel message1 = new JLabel("");
        message1.setForeground(new Color(21, 67, 129));
        decompress.setBackground(new Color(69, 64, 248));
        sourceFileField.setFont(new Font("Verdana", Font.ITALIC, 16));
        decompressPanel.add(sourceFileLabel1);
        decompressPanel.add(sourceFileField1);
        decompressPanel.add(sourceFileChooser1);
        decompressPanel.add(destinationFileLabel1);
        decompressPanel.add(destinationFileField1);
        decompressPanel.add(destinationFileChooser1);
        decompressPanel.add(decompress);
        decompressPanel.add(message1);

        // Compress action listener
        compress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sourcePath = sourceFileField.getText();
                String destPath = destinationFileField.getText() + "\\Compressed.txt";
                HuffCompression.compress(sourcePath, destPath);
                message.setText("Compressed successfully");
            }
        });

        // Browse for source file for compression
        sourceFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jfc = new JFileChooser("D:\\Java");
                int userChoice = jfc.showOpenDialog(CompressorUI.this);
                if (userChoice == JFileChooser.APPROVE_OPTION)
                {
                    String selectedFilePath = jfc.getSelectedFile().getPath();
                    sourceFileField.setText(selectedFilePath);
                }
                if (userChoice == JFileChooser.CANCEL_OPTION)
                    sourceFileField.setText("No File Selected");
            }
        });

        // Browse for destination folder for compression
        destinationFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jfc = new JFileChooser("D:\\Java");
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int userChoice = jfc.showOpenDialog(CompressorUI.this);

                if (userChoice == JFileChooser.APPROVE_OPTION)
                {
                    String selectedFolderPath = jfc.getSelectedFile().getPath();
                    destinationFileField.setText(selectedFolderPath);
                }
                else if (userChoice == JFileChooser.CANCEL_OPTION)
                {
                    destinationFileField.setText("No Folder Selected");
                }
            }
        });

        // Decompress action listener
        decompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sourcePath = sourceFileField1.getText();
                String destPath = destinationFileField1.getText() + "\\Decompressed.txt";
                HuffCompression.decompress(sourcePath, destPath);
                message1.setText("Decompressed successfully");
            }
        });

        // Browse for source file for decompression
        sourceFileChooser1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jfc = new JFileChooser("D:\\Java");
                int userChoice = jfc.showOpenDialog(CompressorUI.this);
                if (userChoice == JFileChooser.APPROVE_OPTION)
                {
                    String selectedFilePath = jfc.getSelectedFile().getPath();
                    sourceFileField1.setText(selectedFilePath);
                }
                if (userChoice == JFileChooser.CANCEL_OPTION)
                    sourceFileField1.setText("No File Selected");
            }
        });

        // Browse for destination folder for decompression
        destinationFileChooser1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jfc = new JFileChooser("D:\\Java");
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int userChoice = jfc.showOpenDialog(CompressorUI.this);

                if (userChoice == JFileChooser.APPROVE_OPTION)
                {
                    String selectedFolderPath = jfc.getSelectedFile().getPath();
                    destinationFileField1.setText(selectedFolderPath);
                }
                else if (userChoice == JFileChooser.CANCEL_OPTION)
                {
                    destinationFileField1.setText("No Folder Selected");
                }
            }
        });

        mainPanel.add(homePanel, "Home");
        mainPanel.add(compressPanel, "Compress");
        mainPanel.add(decompressPanel, "Decompress");

        cardLayout.show(mainPanel, "Home");

        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Compress");
            }
        });

        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Decompress");
            }
        });

        add(mainPanel);
        setVisible(true);
    }
}
