import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Directory {

    private String directoryPath;
    private String directoryName;
    public ArrayList<Myfile> files; 
    public ArrayList<Directory> subDirectories;

    public Directory() {}
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

    public void createFile(String[] args, AllocationScheme alloc) {

        String[] path = args[args.length - 2].split("/");
        String fileName = path[path.length - 1];
        int fileSize = Integer.parseInt(args[args.length - 1]);

        Myfile createdFile = new Myfile(fileName, fileSize);
        createdFile.setFilePath(args[args.length - 2]);
        Directory parentDirectory = getPreviousDirectory(path);

        for (Myfile file : parentDirectory.files) {
            if (file.getFileName().equalsIgnoreCase(fileName)) {
                System.out.println("File already existed");
                return;
            }
        }
        
        if(alloc.searchForSpace(createdFile)) 						//Allocation
        	parentDirectory.files.add(createdFile);
        
        
        //System.out.println(parentDirectory.directoryName);
        System.out.println("File created successfully");

    }

    public void deleteFile(String command, AllocationScheme alloc) {
        String[] splitPath = command.split("/");
        Directory parentDirectory = getPreviousDirectory(splitPath);
        for (Myfile file : parentDirectory.files) {
            if (file.getFileName().equalsIgnoreCase(splitPath[splitPath.length - 1])) {
            	alloc.deallocateBlocks(file); 					// Deallocation
                parentDirectory.files.remove(file);
                System.out.println("File removed successfully");
                return;
            }
        }
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
        subDirectory.directoryPath = command;
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
        Directory parentDirectory = getPreviousDirectory(splitPath);
        for (Directory sub : parentDirectory.subDirectories) {
            if (sub.getDirectoryName().equalsIgnoreCase(splitPath[splitPath.length - 1])) {
                sub.deleteSubDirectories();
                parentDirectory.subDirectories.remove(sub);
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
        for (Myfile file : files) {
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
