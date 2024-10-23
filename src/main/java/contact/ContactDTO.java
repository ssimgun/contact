package contact;

public class ContactDTO {
	private String id;
	private String name;
	private String phone_num;
	private String address;
	private String gubun_name;
	private String memo;
	private String profile_val;
	private String user_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGubun_name() {
		return gubun_name;
	}
	public void setGubun_name(String gubun_name) {
		this.gubun_name = gubun_name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getProfile_val() {
		return profile_val;
	}
	public void setProfile_val(String profile_val) {
		this.profile_val = profile_val;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String toString() {
		return "ContactDTO [id=" + id + ", name=" + name + ", phone_num=" + phone_num + ", address=" + address
				+ ", gubun_name=" + gubun_name + ", memo=" + memo + ", profile_val=" + profile_val + ", user_id="
				+ user_id + "]";
	}
	
	
	
}
