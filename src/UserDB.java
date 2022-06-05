import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserDB {
	
	private ArrayList<User> users = new ArrayList<User>();
	private User currentUser;
	private User admin;
	
	private ArrayList<String> allFolders = new ArrayList<String>();
	
	public void test()
	{
		for(User user:users)
		{
			System.out.println(user.getName()+" "+user.capabilities.keySet());
		}
	}
	
	public UserDB()
	{
		User user = new User("admin", "admin");
		admin = user;
		addUser(user);
		setCurrUser(user);
	}
	
	public void addUser(User user)
	{
		users.add(user);
	}
	
	public String getCurrUser()
	{
		return currentUser.getName();
	}
	
	public void setCurrUser(User user)
	{
		this.currentUser= user;
	}
	
	
	public User searchForUser(String name)
	{
		for(User user:users)
		{
			if(user.getName().equals(name))
				return user;
		}
		return null;
	}
	
	public boolean createUser(String name, String password)
	{
		User user = new User(name, password);
		for(int i=0; i<users.size();i++)
			if(name.equalsIgnoreCase(users.get(i).getName()))
			{
				System.out.println("user with the same name already created");
				return false;
			}
		
		if(currentUser!=admin)
		{
			System.out.println("only the admin can use such command");
			return false;
		}
		
		System.out.println("The user is successfully created");
		addUser(user);
		return true;
		
	}

	
	public boolean grant(String name, String folder, String binary, Directory root)
	{
		if(currentUser!=admin)
		{
			System.out.println("only the admin can use such command");
			return false;
		}
		
		if(root.searchForDirectory(folder, root)==null)
		{
			System.out.println("Folder is not exist");
			return false;
		}
		if(searchForUser(name)==null)
		{
			System.out.println("The user is not exist");
			return false;
		}
		
		System.out.println("The process is successfully done");
		searchForUser(name).createCapability(folder, binary);
		allFolders.add(folder);
		return true;
	}
	
	public boolean login(String name, String password)
	{
		if(searchForUser(name)==null)
		{
			System.out.println("The user is not exist");
			return false;
		}
		User user = searchForUser(name);
		
		if(!user.getPassword().equals(password))
		{
			System.out.println(user.getPassword()+"  "+password);
			System.out.println("The password is not correct");
			return false;
		}
		
		System.out.println("The process is successfully done");
		currentUser = user;
		return true;
	}
	
	public boolean creationCheck (String path)
	{
		if(currentUser==admin)
			return true;
		
		String[] temp = path.split("/");
		String[] folders = new String[temp.length-1];
		for(int i=0; i<temp.length-1;i++)
			folders[i] = temp[i];
		
		String searchedFor = folders[0];
		for(int i=0; i<folders.length;i++)
		{
			if(currentUser.capabilities.containsKey(searchedFor))
			{
				if(currentUser.capabilities.get(searchedFor).charAt(0) == '1')
					return true;
			}
			if(i<folders.length-1)
				searchedFor = searchedFor+'/'+folders[i+1];
		}
		
		System.out.println("This user can't create this");
		return false;
	}
	
	public boolean deletionCheck (String path)
	{
		if(currentUser==admin)
			return true;
		
		String[] temp = path.split("/");
		String[] folders = new String[temp.length-1];
		for(int i=0; i<temp.length-1;i++)
			folders[i] = temp[i];
		
		String searchedFor = folders[0];
		for(int i=0; i<folders.length;i++)
		{
			if(currentUser.capabilities.containsKey(searchedFor))
			{
				if(currentUser.capabilities.get(searchedFor).charAt(1) == '1')
					return true;
			}
			if(i<folders.length-1)
				searchedFor = searchedFor+'/'+folders[i+1];
		}
		
		System.out.println("This user can't delete this");
		return false;
	}
	
	public void saveUsers(File file)
	{
		try 
		{
			 FileWriter myWriter = new FileWriter(file.getAbsolutePath());
			 
			 for(User user: users)
			 {
				 myWriter.write(user.getName() +","+user.getPassword()+ System.getProperty( "line.separator" ));
			 }
			 
		     myWriter.close();
		     
		} 
		catch (IOException e) 
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public void loadUsers(File file) throws IOException
	{
    	BufferedReader bf = new BufferedReader(new FileReader(file.getAbsolutePath()));
    	
    	String data = bf.readLine(); //discard the admin 
    	data = bf.readLine();
    	
    	while (data != null) {
    		String[] temp = data.split(",");
    		User user = new User(temp[0], temp[1]);
    		this.addUser(user);
    		data = bf.readLine();
    	}
       	    	        	    	
    	bf.close();
	}
	
	public void saveCapabilities(File file)
	{
		for(String folder: allFolders)
		{
			String output = folder;
			for(User user:users)
			{
				if(user.capabilities.containsKey(folder))
					output = output+','+user.getName()+','+user.capabilities.get(folder);
			}
			try 
			{
				 FileWriter myWriter = new FileWriter(file.getAbsolutePath());
				 
				 myWriter.write(output+ System.getProperty( "line.separator" ));
				 
			     myWriter.close();
			     
			} 
			catch (IOException e) 
			{
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}
	
	public void loadCapabilities(File file) throws IOException
	{
    	BufferedReader bf = new BufferedReader(new FileReader(file.getAbsolutePath()));
    	
    	String data = bf.readLine();
    	
    	while (data != null) {
    		String[] temp = data.split(",");
    		String folderName = temp[0];
    		allFolders.add(folderName);
    		for(int i=1; i<temp.length-1; i+=2)
    		{
    			User user = searchForUser(temp[i]);
    			user.createCapability(folderName, temp[i+1]);
    		}
    		data = bf.readLine();
    	}
       	    	        	    	
    	bf.close();		
	}
}
