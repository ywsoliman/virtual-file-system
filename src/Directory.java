import java.util.ArrayList;

public class Directory {

    private String directoryPath;
    private String directoryName;
    private ArrayList<File> files;
    private ArrayList<Directory> subDirectories;

    public Directory(String directoryPath) {
        files = new ArrayList<>();
        subDirectories = new ArrayList<>();
        this.directoryPath = directoryPath + '/';

        String[] getFolderName = this.getDirectoryPath().split("/");
        this.directoryName = getFolderName[getFolderName.length - 1];
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public void addFile(File file) {
        file.setFilePath(directoryPath + file.getFileName() + '/');
        files.add(file);
    }

    public void addSubDirectory(Directory dir) {
        dir.directoryPath = this.directoryPath + dir.directoryPath;
        subDirectories.add(dir);
    }


    public void printDirectoryStructure(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
        System.out.println(directoryName + "/");
        for (File file : files) {
            for (int i = 0; i < level + 1; i++) {
                System.out.print("\t");
            }
            System.out.println(file.getFileName());
        }
        for (Directory dir : subDirectories) {
            dir.printDirectoryStructure(level + 1);
        }
    }

}
