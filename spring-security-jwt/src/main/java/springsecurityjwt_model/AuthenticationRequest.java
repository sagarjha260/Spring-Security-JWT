package springsecurityjwt_model;

public class AuthenticationRequest 
{
	private String usernmae;
	private String password;
	
	public AuthenticationRequest() {
	
	}
	
	public AuthenticationRequest(String usernmae, String password) {
		super();
		this.usernmae = usernmae;
		this.password = password;
	}
	
	public String getUsernmae() {
		return usernmae;
	}
	public void setUsernmae(String usernmae) {
		this.usernmae = usernmae;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	 

}
