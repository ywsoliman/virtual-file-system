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
			spaceManager+='1';
		}
		freeBlocks = diskSize;
		System.out.println(spaceManager);
	}
	public void setSpaceManager(String data)
	{
		spaceManager = data;
	}
	
	public abstract void loadVFS(ArrayList<String> arr);
	public abstract void saveVFS(File file);
	public abstract void allocateBlocks(Myfile file, int startBlock);
	public abstract void deallocateBlocks(Myfile file);
	public abstract boolean searchForSpace(Myfile file);
	
}
