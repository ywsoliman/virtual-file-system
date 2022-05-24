import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LinkedAllocation extends AllocationScheme{

	public LinkedAllocation(int diskSize, Directory root) {
		super(diskSize, root);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void allocateBlocks(Myfile file) {
        int linkedBlocks = 0;
        
        while (linkedBlocks != file.getFileSize()) {
           int randomNum = getRandomNumber(0, diskSize);
           if(spaceManager.charAt(randomNum) != '1' && !file.linkedBlocksList.contains(randomNum)) {
        	   String newstr = "";
               linkedBlocks++;
               file.linkedBlocksList.add(randomNum);
	       		for(int i=0; i<spaceManager.length(); i++)
	    		{
	    			if(i==randomNum)
	    				newstr += '1';
	    			else newstr += spaceManager.charAt(i);
	    		}
	       		spaceManager = newstr;
           }
        }
        file.startBlock = file.linkedBlocksList.get(0);
        file.endBlock = +file.linkedBlocksList.get(file.linkedBlocksList.size()-1);

		freeBlocks -= file.getFileSize();
		
	}
	

	@Override
	public void deallocateBlocks(Myfile file) {
        String newstr;
        for(int k=0;k<file.linkedBlocksList.size();k++)
        {
			newstr = "";
	        for(int i=0; i<spaceManager.length(); i++)
			{
				if(i==file.linkedBlocksList.get(k))
					newstr += '0';
				else newstr += spaceManager.charAt(i);
				
			}
			spaceManager = newstr;

        }
		freeBlocks += file.getFileSize();
		
	}
	
	
	@Override
	public void saveVFS(File file,  Directory root) {
		
	      
		try 
		{
			 FileWriter myWriter = new FileWriter(file.getAbsolutePath());
			 
		     myWriter.write(diskSize + System.getProperty( "line.separator" ));
		     
		     myWriter.write("Linked" + System.getProperty( "line.separator" ));
		     
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
                myWriter.write(myfile.getFilePath()+" "+myfile.startBlock+" "+myfile.endBlock+System.getProperty( "line.separator" ));
                for(int i=0;i<myfile.linkedBlocksList.size();i++)
                {
                	myWriter.write(myfile.linkedBlocksList.get(i)+"->");
                }
                myWriter.write("nil");
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

	
	public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

	@Override
	public void loadVFS(ArrayList<String> arr, Directory root) { //load to allocation data structures and root
		for(int i=0; i<arr.size(); i++)
		{
			if(arr.get(i).contains(".txt"))
			{
				String temp = "CreateFile "+arr.get(i);
				String[] tempArray = temp.split(" ");
				String[] command = new String[3];
				
				String[] blocksNumber = arr.get(i+1).split("->");
				
				command[0]=tempArray[0];
				command[1]=tempArray[1];
				command[2]= Integer.toString(blocksNumber.length-1);
				
				//ArrayList<String> fileOfBlocks = new ArrayList<String>();
				Myfile file = root.createFile(command, this);
				file.startBlock = Integer.parseInt(blocksNumber[0]);
				file.endBlock = Integer.parseInt(blocksNumber[blocksNumber.length-2]);
				
				allocateFromFile(file, blocksNumber);
				freeBlocks -= file.getFileSize();
				
				i++;
			}
			else
				root.createFolder(arr.get(i));
		}
		
	}
	
	public void allocateFromFile(Myfile file, String[] arr)
	{
		for(int i=0; i<arr.length-1; i++)
		{
			file.linkedBlocksList.add(Integer.parseInt(arr[i]));
		}
	}

}
