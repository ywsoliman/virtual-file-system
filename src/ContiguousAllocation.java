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
			if(spaceManager.charAt(i) =='1')
				counter++;
			else counter = 0;
			
			
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
		for(int i=0; i<file.getFileSize(); i++)
		{
			
			String newstr = spaceManager.replace(spaceManager.charAt(i+startBlock), '0');
			spaceManager = newstr;
		}
		
		filesData.put(file, startBlock);
	}
	
	
	@Override
	public void deallocateBlocks(Myfile file) {
		int startblock = filesData.get(file);
		
		for(int i=0; i<file.getFileSize(); i++)
		{
			
			String newstr = spaceManager.replace(spaceManager.charAt(i+startblock), '1');
			spaceManager = newstr;
		}
		
		
		filesData.remove(file);
	}
	


	@Override
	public void saveVFS(File file) {
		
	      
		try 
		{
			 FileWriter myWriter = new FileWriter(file.getAbsolutePath());
			 
		     myWriter.write(diskSize + System.getProperty( "line.separator" ));
		     
		     myWriter.write("Contigous " + System.getProperty( "line.separator" ));
		     

		     root.writeFilesPath(root, file);
		     
		     myWriter.close();
		     
		} 
		catch (IOException e) 
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}



	@Override
	public void loadVFS(ArrayList<String> arr) { //load to allocation data structures and root
		// TODO Auto-generated method stub
		
	}




	
}
