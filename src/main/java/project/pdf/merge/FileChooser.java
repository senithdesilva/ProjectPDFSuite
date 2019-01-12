package project.pdf.merge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooser extends JPanel {

    private JLabel label;
    private JTextField textField;
    private JButton button;

    private JFileChooser fileChooser;

    private int mode;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;

    public static int count = 0;

    public FileChooser(String textFieldLabel, String buttonLabel) {

        fileChooser = new JFileChooser();

        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // creates the GUI
        label = new JLabel(textFieldLabel);

        textField = new JTextField(30);
        button = new JButton(buttonLabel);

        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setAcceptAllFileFilterUsed(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        add(label);
        add(textField);
        add(button);

    }

    private void buttonActionPerformed(ActionEvent evt) {
        if (mode == MODE_OPEN) {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File[] files = fileChooser.getSelectedFiles();

                textField.setText("");
                while (count++ < files.length) {
                    textField.setText(textField.getText() + " " + files[count - 1].getName());
                }
            } else {
                textField.setText("Operation Cancelled!");
            }
        } else if (mode == MODE_SAVE) {
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

                File[] files = fileChooser.getSelectedFiles();

                textField.setText("");

                while (count++ < files.length) {
                    textField.setText(textField.getText() + " " + files[count - 1].getName());
                }

            }  else {
                textField.setText("Operation Cancelled!");
            }
        }
    }

    void addFileTypeFilter(String extension, String description) {
        FileTypeFilter filter = new FileTypeFilter(extension, description);
        fileChooser.addChoosableFileFilter(filter);
    }

    void setMode(int mode) {
        this.mode = mode;
    }

    public JFileChooser getFileChooser() {
        return this.fileChooser;
    }
}
