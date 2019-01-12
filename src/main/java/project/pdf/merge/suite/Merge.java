package project.pdf.merge.suite;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import project.pdf.merge.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Merge {

    private static PDDocument document;

    private Merge() { }

    public static void mergePDFs(FileChooser filePicker, String fileName, String filePath) throws IOException {
        JFileChooser fileChooser = filePicker.getFileChooser();

        File[] files = fileChooser.getSelectedFiles();

        //Instantiating PDFMergerUtility class
        PDFMergerUtility mergePDF = new PDFMergerUtility();
        //Setting the destination file
        mergePDF.setDestinationFileName(filePath + "/"+ fileName + ".pdf");

        for (File file: files) {
            document = PDDocument.load(file);
            //adding the source files
            mergePDF.addSource(file);
        }

        //Merging the documents
        mergePDF.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

        document.close();
    }
}
