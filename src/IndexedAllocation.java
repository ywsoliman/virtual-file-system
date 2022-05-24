import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class IndexedAllocation extends AllocationScheme{

	public IndexedAllocation(int diskSize, Directory root) {
		super(diskSize, root);
		// TODO Auto-generated constructor stub
	}

	HashMap<Myfile, Integer> filesData = new HashMap<Myfile, Integer>(); //each file has an index block
	
	ArrayList<ArrayList<Integer>> Allocatedblocks = new ArrayList<ArrayList<Integer>>();
	
	
	@Override
	public void allocateBlocks(Myfile file) { 
		
		ArrayList<Integer> fileOfBlocks = new ArrayList<Integer>();
		String newstr = "";
		int counter = file.getFileSize();
		for(int i=0; i<spaceManager.length(); i++)
		{
			if(spaceManager.charAt(i)=='0' &&counter>0)
			{
				newstr += '1';
				counter--;
				fileOfBlocks.add(i);
			}
			else newstr += spaceManager.charAt(i);
		}
		
		spaceManager = newstr;
		Allocatedblocks.add(fileOfBlocks);
		filesData.put(file, Allocatedblocks.indexOf(fileOfBlocks));
		freeBlocks -= file.getFileSize();
		
		
	}


	@Override
	public void deallocateBlocks(Myfile file) {
		ArrayList<Integer> fileOfBlocks = new ArrayList<Integer>(Allocatedblocks.get(filesData.get(file)));
		int counter=0;
		String newstr = "";
		for(int i=0; i<spaceManager.length(); i++)
		{
			if(counter<fileOfBlocks.size())
			{
				if(i==fileOfBlocks.get(counter) )
				{
					newstr += '0';
					counter++;
				}
				else newstr += spaceManager.charAt(i);
			}

			else newstr += spaceManager.charAt(i);
			
		}
		spaceManager = newstr;
		filesData.remove(file);
		freeBlocks += file.getFileSize();
		
	}


	@Override
	public void saveVFS(File file,  Directory root) {
		
	      
		try 
		{
			 FileWriter myWriter = new FileWriter(file.getAbsolutePath());
			 
		     myWriter.write(diskSize + System.getProperty( "line.separator" ));
		     
		     myWriter.write("Indexed" + System.getProperty( "line.separator" ));
		     
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
                myWriter.write(myfile.getFilePath()+" "+filesData.get(myfile) +System.getProperty( "line.separator" ));
                ArrayList<Integer> fileOfBlocks = new ArrayList<Integer>(Allocatedblocks.get(filesData.get(myfile)));
                for(int i=0;i<fileOfBlocks.size();i++)
                {
                	myWriter.write(fileOfBlocks.get(i)+" ");
                }
                myWriter.write(System.getProperty( "line.separator" ));
                
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
				String[] command = new String[tempArray.length];
				String[] fileOfBlocks = arr.get(i+1).split(" ");
				
				command[0]=tempArray[0];
				command[1]=tempArray[1];
				command[2]= Integer.toString(fileOfBlocks.length);
				
				//ArrayList<String> fileOfBlocks = new ArrayList<String>();
				Myfile file = root.createFile(command, this);
				
				allocateFromFile(file, fileOfBlocks, Integer.parseInt(tempArray[2]));
				freeBlocks -= file.getFileSize();
				
				i++;
			}
			else
				root.createFolder(arr.get(i));
		}
		
	}
	
	public void allocateFromFile(Myfile file, String[] arr, int index)
	{
		filesData.put(file, index);
		ArrayList<Integer> arrayOfBlocks = new ArrayList<Integer> ();
		for(int i=0; i<arr.length; i++)
		{
			arrayOfBlocks.add(Integer.parseInt(arr[i]));
		}
		Allocatedblocks.add(index, arrayOfBlocks);
	}

}
