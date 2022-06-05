import java.util.HashMap;

public class User {
	private String name;
	private String password;
	HashMap<String, String> capabilities = new HashMap<String, String> ();
	
	public User(String n, String p)
	{
		this.name =n;
		this.password=p;
	}
	
	public void createCapability (String folder, String binary)
	{
		capabilities.put(folder, binary);
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPassword()
	{
		return password;
	}
}
