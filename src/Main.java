import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        
        UserDB userDB = new UserDB();
        
        Directory root = new Directory("root");
        AllocationScheme alloc = null;
        int diskSize = 0;
        
        File myObj = new File("DiskStructure.txt");
        File userFile = new File("User.txt");
        File CapFile = new File("Capabilities.txt");
        
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
            
        	userFile.createNewFile();
        	CapFile.createNewFile();
            
        } else { //Loading the data from VFS file
        	
        	ArrayList<String> restOfVFS = new ArrayList<String>();
       
        	BufferedReader bf = new BufferedReader(new FileReader("DiskStructure.txt"));
               	
        	diskSize = Integer.parseInt(bf.readLine());
        	        	
        	String data = bf.readLine();
        	
            if (data.equalsIgnoreCase("Contiguous"))
            	alloc = new ContiguousAllocation(diskSize, root);                
            else if (data.equalsIgnoreCase("Indexed"))
            	alloc = new IndexedAllocation(diskSize, root);
            else if (data.equalsIgnoreCase("Linked"))
            	alloc = new LinkedAllocation(diskSize, root);
            
            alloc.setSpaceManager(bf.readLine());
            
            data = bf.readLine();
           
        	while (data != null) {
        		restOfVFS.add(data);
        		data = bf.readLine();
        	}
        	bf.close();
        	alloc.loadVFS(restOfVFS, root);
        	
        	userDB.loadUsers(userFile);
        	userDB.loadCapabilities(CapFile);
        }
        
        //CUser ahmed pass123
        //CUser hassan pass123
        //CreateFolder root/folder1
        //Grant ahmed root/folder1 10
        //Grant hassan root/folder1 10
        //Login ahmed pass123
        //CreateFile root/folder1/file.txt 
        //DeleteFile root/folder1/file.txt ---can't---

        

        while (true) {
            System.out.println("Enter command (type exit to quit):");
            String[] command = sc.nextLine().split(" ");

            if (command.length == 1)
            {
                if (command[0].equalsIgnoreCase("exit"))
                    break;
                if (command[0].equalsIgnoreCase("DisplayDiskStatus")) {
                	alloc.DisplayDiskStatus();
                }
                else if (command[0].equalsIgnoreCase("DisplayDiskStructure")) {
                    root.printDirectoryStructure(0);
                }
                else //TellUser
                	System.out.println(userDB.getCurrUser());
                	
            }
            else if (command.length == 2)
            {
                if (command[0].equalsIgnoreCase("CreateFolder")) {
                	if(userDB.creationCheck(command[1]))
                		root.createFolder(command[1]);
                }
                else if (command[0].equalsIgnoreCase("DeleteFolder")) {
                	if(userDB.deletionCheck(command[1]))
                		root.deleteFolder(command[1], alloc);
                }
                else if (command[0].equalsIgnoreCase("DeleteFile")) {
                	if(userDB.deletionCheck(command[1]))
                		root.deleteFile(command[1], alloc);
                }
                else
                    System.out.println("Invalid arguments.");
            }
            else if (command.length == 3)
            {
            	if(command[0].equalsIgnoreCase("CUser")) //CUser
            		userDB.createUser(command[1], command[2]);
            	else if (command[0].equalsIgnoreCase("Login")) //Login
            		userDB.login(command[1], command[2]);
            	else 
	            	if(userDB.creationCheck(command[1]))
	            	{
	                	Myfile file = root.createFile(command, alloc);
	                    if(file != null)
	                    	alloc.allocateBlocks(file);
	            	}

            }
            else if (command.length == 4)
            {
            	userDB.grant(command[1], command[2], command[3], root); //grant
            }
            else
                System.out.println("Something went wrong. Try again.");

        }
        
        userDB.saveUsers(userFile);
        userDB.saveCapabilities(CapFile);
        alloc.saveVFS(myObj, root);



    }

}
