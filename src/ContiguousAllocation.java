import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;



public class ContiguousAllocation extends AllocationScheme {
	
	
	HashMap<Myfile, Integer> filesData = new HashMap<Myfile, Integer>();
	
	public ContiguousAllocation(int diskSize, Directory root) {
		super(diskSize, root);
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean searchForSpace(Myfile file) {
		int counter = 0;
		for(int i=0; i<diskSize; i++)
		{
			if(spaceManager.charAt(i) =='0')
				counter++;
			else counter = 0;
			
			//System.out.println(counter);
			if(counter>=file.getFileSize())
			{
				allocateBlocks(file, (i-counter)+1);
				return true;
			}
		}
		return false;
		
	}
	

	@Override
	public void allocateBlocks(Myfile file, int startBlock) {
		String newstr = "";
		for(int i=0; i<spaceManager.length(); i++)
		{
			if(i>=startBlock && i<startBlock+file.getFileSize())
				newstr += '1';
			else newstr += spaceManager.charAt(i);
		}
		spaceManager = newstr;
		filesData.put(file, startBlock);
	}
	
	
	@Override
	public void deallocateBlocks(Myfile file) {
		int startBlock = filesData.get(file);
		
		String newstr = "";
		for(int i=0; i<spaceManager.length(); i++)
		{
			if(i>=startBlock && i<startBlock+file.getFileSize())
				newstr += '0';
			else newstr += spaceManager.charAt(i);
		}
		spaceManager = newstr;
		filesData.put(file, startBlock);
		
		filesData.remove(file);
	}
	


	@Override
	public void saveVFS(File file,  Directory root) {
		
	      
		try 
		{
			 FileWriter myWriter = new FileWriter(file.getAbsolutePath());
			 
		     myWriter.write(diskSize + System.getProperty( "line.separator" ));
		     
		     myWriter.write("Contiguous" + System.getProperty( "line.separator" ));
		     
		     myWriter.write(spaceManager+ System.getProperty( "line.separator" ));
		     

		     writeFilesPath(root, file, myWriter);
		     
		     myWriter.close();
		     
		} 
		catch (IOException e) 
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}


    public void writeFilesPath(Directory dir,  File file, FileWriter myWriter) {
        for (Myfile myfile : dir.files) {
        	try 
        	{
                myWriter.write(myfile.getFilePath()+" "+filesData.get(myfile)+" "+myfile.getFileSize()+ System.getProperty( "line.separator" ));
            }
            catch (IOException e) 
        	{
                System.out.println("exception occurred" + e);
            }
        
        }
        for (Directory dir2 : dir.subDirectories) {
        	try 
        	{
                myWriter.write(dir2.getDirectoryPath()+System.getProperty( "line.separator" ));
            }
            catch (IOException e) 
        	{
                System.out.println("exception occurred" + e);
            }
        	writeFilesPath(dir2, file,  myWriter);
        }
    }


	@Override
	public void loadVFS(ArrayList<String> arr, Directory root) { //load to allocation data structures and root
		for(int i=0; i<arr.size(); i++)
		{
			if(arr.get(i).contains(".txt"))
			{
				String temp = "CreateFile "+arr.get(i);
				String[] tempArray = temp.split(" ");
				String[] command = new String[tempArray.length-1];
				
				command[0]=tempArray[0];
				command[1]=tempArray[1];
				command[2]=tempArray[3];
				root.createFile(command, this);
			}
			else
				root.createFolder(arr.get(i));
		}
		
	}
	
}
