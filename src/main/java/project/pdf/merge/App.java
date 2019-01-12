package project.pdf.merge;

import project.pdf.merge.suite.Merge;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends JFrame implements ActionListener {
    private final static Logger logger = Logger.getLogger(App.class.getName());

    private static final String MERGE = "Merge";
    private static final String SPLIT = "Split";

    private static FileChooser filePicker;

    private App() {
        super("PDF suite");

        setLayout(new FlowLayout());

        // set up a file picker component
        filePicker = new FileChooser("Select", "Browse...");
        filePicker.setMode(FileChooser.MODE_SAVE);
        filePicker.addFileTypeFilter(".pdf", "PDF Document");


        // access JFileChooser class directly
        JFileChooser fileChooser = filePicker.getFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        // add the component to the frame
        add(filePicker);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 200);
        setLocationRelativeTo(null);    // center on screen

        JButton mergeButton = createButton(MERGE);
        JButton splitButton = createButton(SPLIT);

        // Add action listener for buttons
        mergeButton.addActionListener(this);
        splitButton.addActionListener(this);

        getContentPane().add(mergeButton);
        getContentPane().add(splitButton);
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);

        if (MERGE.equals(text)) {
            button.setVerticalTextPosition(AbstractButton.CENTER);
            button.setHorizontalTextPosition(AbstractButton.LEADING);
            button.setMnemonic(KeyEvent.VK_D);
            button.setActionCommand(MERGE);

            button.setToolTipText("Click here to Merge the PDFs");

        } else if (SPLIT.equals(text)) {
            button.setVerticalTextPosition(AbstractButton.BOTTOM);
            button.setHorizontalTextPosition(AbstractButton.CENTER);
            button.setMnemonic(KeyEvent.VK_M);
            button.setActionCommand(SPLIT);

            button.setToolTipText("Click here to Split the PDF");
        }
        return button;
    }

    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame informationPanel;
        if (MERGE.equals(e.getActionCommand())) {
            informationPanel = new JFrame();

            String fileName = getMergeFileName();
            String filePath = getMergeFilePath();

            try {
                Merge.mergePDFs(filePicker, fileName, filePath);
            } catch (IOException e1) {
                logger.log(Level.WARNING, "Exception");

            }

            JOptionPane.showMessageDialog(informationPanel, "Merge Successful!");
        }
    }

    private String getMergeFileName() {
        return JOptionPane.showInputDialog("Please input new PDF file name!");
    }

    private String getMergeFilePath() {
        String defaultFilePath = ".";
        JFileChooser filePath = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        filePath.setDialogTitle("Choose a directory to save your PDF file: ");
        filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = filePath.showSaveDialog(null);

        if (returnValue != JFileChooser.APPROVE_OPTION) {
            return defaultFilePath;
        }

        if (filePath.getSelectedFile().isDirectory()) {
            return String.valueOf(filePath.getSelectedFile());
        }

        return defaultFilePath;
    }
}
