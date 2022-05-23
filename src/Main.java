import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        
        Directory root = new Directory("root");
        AllocationScheme alloc = null;
        int diskSize = 0;
        
        File myObj = new File("DiskStructure.txt");
        if (myObj.createNewFile()) {  //VFS file is created for the first time
            int allocationMethod;
            System.out.println("Choose an allocation method: ");
            System.out.print("1- Contiguous Allocation\n2- Indexed Allocation\n3- Linked Allocation\n");
            allocationMethod = sc.nextInt();
            System.out.print("Enter number of blocks (each block size is 1 KB): ");
            diskSize = sc.nextInt();
            sc.nextLine();

            if (allocationMethod == 1)
                alloc = new ContiguousAllocation(diskSize, root);
            else if (allocationMethod == 2)
            	alloc = new IndexedAllocation(diskSize, root);
            else if (allocationMethod == 3)
            	alloc = new LinkedAllocation(diskSize, root);
            
        } else { //Loading the data from VFS file
        	
        	ArrayList<String> restOfVFS = new ArrayList<String>();
       
        	BufferedReader bf = new BufferedReader(new FileReader("file.txt"));
       
        	String data = bf.readLine();
        	
        	diskSize = Integer.parseInt(data);
        	
        	data = bf.readLine();
        	
            if (data.equalsIgnoreCase("Contiguous"))
                alloc = new ContiguousAllocation(diskSize, root);
            else if (data.equalsIgnoreCase("Indexed"))
            	alloc = new IndexedAllocation(diskSize, root);
            else if (data.equalsIgnoreCase("Linked"))
            	alloc = new LinkedAllocation(diskSize, root);
       
        	while (data != null) {
        		restOfVFS.add(data);
        		data = bf.readLine();
        	}
       
        	bf.close();
        	alloc.loadVFS(restOfVFS);
        	
        	/*
        	Scanner myReader = new Scanner(myObj);
        	int counter=0;
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              if(counter ==0)
              {
            	  diskSize = Integer.parseInt(data);
              }
              else if(counter ==1)
              {
                  if (data.equalsIgnoreCase("Contiguous"))
                      alloc = new ContiguousAllocation(diskSize, root);
                  else if (data.equalsIgnoreCase("Indexed"))
                  	alloc = new IndexedAllocation(diskSize, root);
                  else if (data.equalsIgnoreCase("Linked"))
                  	alloc = new LinkedAllocation(diskSize, root);
              }
              else if(counter ==2)
              {
            	  alloc.setSpaceManager(data);
              }
              else
              {
            	  
              }
              counter++;
            }
            myReader.close();*/
        }
        



        





        

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
                    root.deleteFile(command[1]);
                }
                else
                    System.out.println("Invalid arguments.");
            }
            else if (command.length == 3)
            {
                root.createFile(command);
            }
            else
                System.out.println("Something went wrong. Try again.");

        }
        
        
        alloc.saveVFS(myObj);

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
