import java.io.File;
import java.util.ArrayList;

public class IndexedAllocation extends AllocationScheme{

	public IndexedAllocation(int diskSize, Directory root) {
		super(diskSize, root);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void saveVFS(File file,  Directory root) {
		// TODO Auto-generated method stub
		
	}



	

	@Override
	public void allocateBlocks(Myfile file, int startBlock) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deallocateBlocks(Myfile file) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean searchForSpace(Myfile file) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void loadVFS(ArrayList<String> arr, Directory root) {
		// TODO Auto-generated method stub
		
	}

}
