import java.util.ArrayList;

public class Directory {

    private String directoryPath;
    private String directoryName;
    private ArrayList<File> files;
    private ArrayList<Directory> subDirectories;

    public Directory(String directoryName) {
        files = new ArrayList<>();
        subDirectories = new ArrayList<>();
        this.directoryName = directoryName;
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

    public Directory searchForSubDirectory(String name) {
        for (Directory dir : subDirectories)
            if (dir.getDirectoryName().equalsIgnoreCase(name))
                return dir;
        return null;
    }

    private Directory getPreviousDirectory(String[] path) {
        Directory current = this;
        for (int i = 0; i < path.length - 2; i++) {
            if (path[i].equalsIgnoreCase(current.getDirectoryName())) {
                if (current.searchForSubDirectory(path[i + 1]) != null) {
                    current = current.searchForSubDirectory(path[i + 1]);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return current;
    }

    public void createFolder(String command) {

        String[] splitPath = command.split("/");
        Directory directory = getPreviousDirectory(splitPath);

        if (directory == null) {
            System.out.println("Path Not Found");
            return;
        }

        Directory subDirectory = new Directory(splitPath[splitPath.length - 1]);
        for (Directory dir : directory.subDirectories) {
            if (dir.getDirectoryName().equalsIgnoreCase(subDirectory.getDirectoryName())) {
                System.out.println("Folder already exists");
                return;
            }
        }
        directory.subDirectories.add(subDirectory);
        System.out.println("Folder created successfully");

    }

    public void deleteSubDirectories() {
        files.clear();
        for (Directory dir : subDirectories) {
            dir.deleteSubDirectories();
        }
    }

    public void deleteFolder(String command) {
        String[] splitPath = command.split("/");
        for (Directory sub : subDirectories) {
            if (sub.getDirectoryName().equalsIgnoreCase(splitPath[splitPath.length - 1])) {
                sub.deleteSubDirectories();
                subDirectories.remove(sub);
                System.out.println("Folder removed successfully");
                return;
            }
        }
        System.out.println("Folder Not Found");
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

    @Override
    public String toString() {
        return "Directory{" +
                "directoryPath='" + directoryPath + '\'' +
                ", directoryName='" + directoryName + '\'' +
                '}';
    }
}
