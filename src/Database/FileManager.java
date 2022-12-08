package Database;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import Utilities.UserIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author Charlie Timlock
 * @version 2.0
 * This class handles file transfer for the Job Searchie system, and allows for uploading and downloading files from the
 * Job Searchie file storage system.
 */

public abstract class FileManager
{
    public static final String RESUME_DIRECTORY = "resume";
    public static final String COVER_LETTER_DIRECTORY = "coverLetter";
    private static final String JOB_SEARCHIE_FILE_ROOT_DIRECTORY = "./database/fileStorage/";

    /**
     * This method checks whether the requested file exists within the directory provided.
     *
     * @param directory The directory to be accessed within the JS file storage.
     * @param fileName  The file name, including the extension.
     * @return Returns true when the file exists.
     */
    public static Boolean checkFileExistsInStorage(String directory, String fileName)
    {
        File target = new File(JOB_SEARCHIE_FILE_ROOT_DIRECTORY + directory + "/" + fileName);
        return target.exists();
    }

    /**
     * Returns the file extension from a valid filePath.
     *
     * @param filePath The filepath to be analysed
     * @return The file's extension, as a string.
     */
    public static String getExtensionFromPath(String filePath)
    {
        int index = filePath.lastIndexOf('.');
        return filePath.substring(index);
    }

    /**
     * This method returns an array of all files within a given directory in the JobSearchie fileStorage.
     *
     * @param directory The selected directory.
     * @return The names of all files inside the given directory, as an array of Strings.
     */
    public static String[] listFilesFromJSDirectory(String directory)
    {
        File folder = new File(JOB_SEARCHIE_FILE_ROOT_DIRECTORY + directory + "/");
        return folder.list();
    }

    /**
     * This method will copy a file from a given path into a specified directory in the JobSearchie fileStorage directory.
     *
     * @param directory   The selected directory.
     * @param srcPath     The path of the target file.
     * @param newFileName The selected name for the new file, excluding the file extension.
     */
    public static void moveFileToJSStorage(String directory, String srcPath, String newFileName) throws IOException
    {

        if (srcPath != null && directory != null)
        {
            File src = new File(srcPath);
            String extension = getExtensionFromPath(srcPath);
            newFileName += extension;
            File dest = new File(JOB_SEARCHIE_FILE_ROOT_DIRECTORY + directory + "/" + newFileName);
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

    }

    /**
     * This method locates a file withing a JobSearchie file storage directory, and opens it if it is available.
     *
     * @param directory The directory to be accessed within the JS file storage.
     * @param fileName  The file name, including the extension.
     * @throws IOException Will throw if the file is not found.
     */
    public static void openFileFromJS(String directory, String fileName) throws IOException
    {
        File f = new File(JOB_SEARCHIE_FILE_ROOT_DIRECTORY + directory + "/" + fileName);
        if (f.exists())
        {
            Desktop.getDesktop().open(f);
        }
    }

    private static String readDocFileAsString(String filePath) throws IOException
    {
        File fileObj = new File(filePath);
        InputStream stream = new FileInputStream(filePath);
        HWPFDocument doc = new HWPFDocument(stream);
        return doc.getText().toString();
    }

    private static String readDocxFileAsString(String filePath) throws IOException
    {
        File fileObj = new File(filePath);
        InputStream stream = new FileInputStream(filePath);
        XWPFDocument document = new XWPFDocument(stream);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        return extractor.getText();
    }

    /**
     * This method will read a .pdf, .docx, .doc, or .txt file and return its contents as a string.
     *
     * @param filePath The full path of the file being read.
     * @return The contents of the file, as a String.
     * @throws IOException If the file is not able to be found, or if it is an incorrect file type, an exception will be thrown.
     */
    public static String readFileToString(String filePath) throws IOException
    {
        if (filePath.isEmpty() || filePath == null)
        {
            return "";
        } else
        {
            String extension = getExtensionFromPath(filePath);

            String text;
            switch (extension)
            {
                case ".pdf" -> text = readPDFFileAsString(filePath);
                case ".doc" -> text = readDocFileAsString(filePath);
                case ".docx" -> text = readDocxFileAsString(filePath);
                case ".txt" -> text = readTextFileAsString(filePath);
                default -> throw new IOException();
            }
            return text;
        }
    }

    private static String readPDFFileAsString(String filePath) throws IOException
    {
        File file = new File(filePath);

        FileInputStream stream = new FileInputStream(file);

        PDDocument pdfDocument = PDDocument.load(stream);

        PDFTextStripper pdfStripper = new PDFTextStripper();

        String docText = pdfStripper.getText(pdfDocument);

        pdfDocument.close();

        stream.close();

        return docText;
    }

    private static String readTextFileAsString(String filePath) throws IOException
    {
        File file = new File(filePath);
        if (file.exists())
        {
            Path path = file.toPath();
            return Files.readString(path);
        } else
        {
            throw new IOException();
        }
    }

    /**
     * This method requests the user to select a file in their local environment, and returns the path.
     * Example: selectFilePath("Select a File", {"txt", "pdf"})
     *
     * @param dialogueTitle The dialogue box's title.
     * @param fileTypes     A whitelist of selectable file types. Any file type not in this list will not appear inside the selection window.
     * @return The selected file's location, as a String.
     */
    public static String selectFilePath(String dialogueTitle, String[] fileTypes)
    {
        String path = null;
        String osName = System.getProperty("os.name").toLowerCase();
        String homeDir = System.getProperty("user.home");
        if (osName.contains("mac"))
        {
            Frame frame = new Frame();
            do
            {
                FileDialog fileDialog = new FileDialog(frame, "Choose a .pdf, .docx, .doc, or .txt file", FileDialog.LOAD);
                fileDialog.setDirectory(homeDir);
                fileDialog.setVisible(true);
                fileDialog.setMultipleMode(false);
                fileDialog.toFront();
                path = fileDialog.getFile();
                if (path == null)
                {
                    String retry = UserIO.menuSelectorValue("Upload cancelled. Do you wish to try again?", new String[]{"Yes", "No"});
                    if (retry.equalsIgnoreCase("No"))
                    {
                        return "";
                    }
                }
                path = fileDialog.getDirectory() + path;

                boolean fileIsValid = false;
                for (String fileType : fileTypes)
                {
                    if (path.toLowerCase().endsWith(fileType.toLowerCase()))
                    {
                        fileIsValid = true;
                        break;
                    }
                }

                if (!fileIsValid)
                {
                    UserIO.displayBody("Invalid file type selected. Please try again.");
                    path = null;
                }
            } while (path == null);
        } else
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setDialogTitle(dialogueTitle);
            String description = "." + String.join(", .", fileTypes);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter(description, fileTypes);
            fileChooser.addChoosableFileFilter(restrict);
            int returnCode = fileChooser.showOpenDialog(null);
            switch (returnCode)
            {
                case JFileChooser.APPROVE_OPTION -> path = fileChooser.getSelectedFile().getAbsolutePath();
                case JFileChooser.CANCEL_OPTION -> {
                    String retry = UserIO.menuSelectorValue("Upload cancelled. Do you wish to try again?", new String[]{"Yes", "No"});
                    if (retry.equalsIgnoreCase("Yes"))
                    {
                        path = FileManager.selectFilePath(dialogueTitle, fileTypes);
                    } else if (retry.equalsIgnoreCase("no"))
                    {
                        path = "";
                    }
                }

            }
            fileChooser.setVisible(false);
        }
        return path;
    }

    public static String returnSelectedFileAsString(String dialogueTitle) throws IOException
    {
        String path = FileManager.selectFilePath(dialogueTitle, new String[]{"pdf", "docx", "doc", "txt"});
        return FileManager.readFileToString(path);
    }
}
