import java.util.ArrayList;

public class File {

    private String fileName;
    private String filePath;
    private ArrayList<Integer> allocatedBlocks;

    public File(String fileName) {
        allocatedBlocks = new ArrayList<>();
        this.fileName = fileName;
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

    @Override
    public String toString() {
        return fileName;
    }
}
