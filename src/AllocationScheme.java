import java.io.File;
import java.util.ArrayList;

public abstract class AllocationScheme {
	private int diskSize;
	private Directory root;
	private String spaceManager;
	//datastructure for saving files blocks eg. start, length
	
	public AllocationScheme(int diskSize, Directory root)
	{
		this.diskSize = diskSize;
		root = new Directory();
		this.root = root;
		spaceManager = "";
		for(int i=0;i<diskSize;i++)
		{
			spaceManager+="0";
		}
	}
	public void setSpaceManager(String data)
	{
		spaceManager = data;
	}
	
	public abstract void loadVFS(ArrayList<String> arr);
	public abstract void saveVFS(File file);
	public abstract void allocateBlocks(String file, int size);
	public abstract void deallocateBlocks(String file);
	public abstract void searchForSpace(int size);
	
}
