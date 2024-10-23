package contact;

public class UserInfoDTO {
	private String user_id;
	private String pw;
	private String name;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "UserInfoDTO [user_id=" + user_id + ", pw=" + pw + ", name=" + name + "]";
	}
	
}
