import java.util.ArrayList;
import java.util.LinkedList;

public class Myfile {

    private String fileName;
    private String filePath;
    private int fileSize;
    
    public LinkedList<Integer> linkedBlocksList = new LinkedList<>();
    public int startBlock;
    public int endBlock;
   
    public Myfile(String fileName, int fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return fileName;
    }
}
