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
	public void allocateBlocks(Myfile file) {
		HashMap<Integer, Integer> groupBlocks = new HashMap<Integer, Integer>();
		
		int startBlock = 0;
		
		groupBlocks.put(diskSize, 0);
		
		int counter = 0, i;
		for(i=0; i<diskSize; i++)
		{
			if(spaceManager.charAt(i) =='0')
				counter++;
			else 
			{
				groupBlocks.put(counter, i-counter);
				counter=0;
				
			}
		}
		groupBlocks.put(counter, i-counter);
		List<Integer> sortedGp = new ArrayList<>(groupBlocks.keySet());
		Collections.sort(sortedGp);
		int l = 0;
		while(true)
		{
			if(sortedGp.get(l)>=file.getFileSize())
			{
				startBlock = groupBlocks.get(sortedGp.get(l));
				break;
			}
			l++;
		}

		String newstr = "";
		for(int f=0; f<spaceManager.length(); f++)
		{
			if(f>=startBlock && f<startBlock+file.getFileSize())
				newstr += '1';
			else newstr += spaceManager.charAt(f);
		}
		spaceManager = newstr;
		filesData.put(file, startBlock);
		freeBlocks -= file.getFileSize();
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
		freeBlocks += file.getFileSize();
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
				Myfile file = root.createFile(command, this);
				allocateFromFile(file, Integer.parseInt(tempArray[2]));
				freeBlocks -= file.getFileSize();
			}
			else
				root.createFolder(arr.get(i));
		}
		
	}
	
	public void allocateFromFile(Myfile file, int start)
	{
		filesData.put(file, start);
	}
	
}
