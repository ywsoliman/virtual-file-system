import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

//        int allocationMethod;
//        int diskSize;
//
//        System.out.println("Choose an allocation method: ");
//        System.out.print("1- Contiguous Allocation\n2- Indexed Allocation\n3- Linked Allocation\n");
//        allocationMethod = sc.nextInt();
//        System.out.print("Enter number of blocks (each block size is 1 KB): ");
//        diskSize = sc.nextInt();
//        sc.nextLine();
//
//        Directory root = null;
//        if (allocationMethod == 1)
//            root = new Directory("root", new Contiguous(diskSize));
//        if (allocationMethod == 2)
//            root = new Directory("root", new Indexed(diskSize));
//        if (allocationMethod == 3)
//            root = new Directory("root", new Linked(diskSize));

        Directory root = new Directory("root");

        while (true) {
            System.out.println("Enter command (type exit to quit):");
            String[] command = sc.nextLine().split(" ");

            if (command.length == 1)
            {
                if (command[0].equalsIgnoreCase("exit"))
                    break;
                if (command[0].equalsIgnoreCase("DisplayDiskStatus")) {

                }
                else if (command[0].equalsIgnoreCase("DisplayDiskStructure")) {
                    root.printDirectoryStructure(0);
                }
            }
            else if (command.length == 2)
            {
                if (command[0].equalsIgnoreCase("CreateFolder")) {
                    root.createFolder(command[1]);
                }
                else if (command[0].equalsIgnoreCase("DeleteFolder")) {
                    root.deleteFolder(command[1]);
                }
                else if (command[0].equalsIgnoreCase("DeleteFile")) {

                }
            }
            else if (command.length == 3)
            {

            }

        }

        /*
        File rootFile = new File("test.pdf");
        root.addFile(rootFile);

        Directory sub1 = new Directory("folder1");
        Directory sub2 = new Directory("folder2");

        root.addSubDirectory(sub1);
        root.addSubDirectory(sub2);

        File file1 = new File("welly.txt");
        File file2 = new File("youssef.txt");

        sub1.addFile(file1);
        sub2.addFile(file2);

        Directory sub3 = new Directory("FINAL");

        root.addSubDirectory(sub3);

        File file3 = new File("V1.txt");
        File file4 = new File("V2.txt");

        sub3.addFile(file3);
        sub3.addFile(file4);

        Directory insideSub3 = new Directory("A-Directory-In-Final-Directory");
        sub3.addSubDirectory(insideSub3);
        */


    }

}
