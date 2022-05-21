import java.util.ArrayList;

public class File {

    private String fileName;
    private String filePath;
    private int fileSize;
    private ArrayList<Integer> allocatedBlocks;

    public File(String fileName, int fileSize) {
        allocatedBlocks = new ArrayList<>();
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
