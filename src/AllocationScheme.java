import java.io.File;
import java.util.ArrayList;

public abstract class AllocationScheme {
	protected int diskSize;
	protected Directory root;
	protected String spaceManager;
	protected int freeBlocks;
	//data structure for saving files blocks eg. start, length
	
	public AllocationScheme(int diskSize, Directory root)
	{
		this.diskSize = diskSize;
		root = new Directory();
		this.root = root;
		spaceManager = "";
		for(int i=0;i<diskSize;i++)
		{
			spaceManager+='0';
		}
		freeBlocks = diskSize;
		
	}
	
	public void setSpaceManager(String data)
	{
		spaceManager = data;
	}
	
	public abstract void loadVFS(ArrayList<String> arr, Directory root);
	public abstract void saveVFS(File file, Directory root);
	public abstract void allocateBlocks(Myfile file);
	public abstract void deallocateBlocks(Myfile file);
	
	public void DisplayDiskStatus()
	{
    	System.out.println("Empty space: "+this.freeBlocks);
    	System.out.println("Empty Allocated space: "+ (this.diskSize-this.freeBlocks));
    	System.out.print("Empty Blocks in the Disk: ");
    	for(int i=0; i<spaceManager.length(); i++)
    	{
    		if(spaceManager.charAt(i)=='0')
    			System.out.print(i+" ");
    	}
    	System.out.println();
    	System.out.print("Allocated Blocks in the Disk: ");
    	for(int i=0; i<spaceManager.length(); i++)
    	{
    		if(spaceManager.charAt(i)=='1')
    			System.out.print(i+" ");
    	}
    	System.out.println();
	}
	
	public boolean searchForSpace(Myfile file)
	{
		return freeBlocks>=file.getFileSize();
	}
	
}
