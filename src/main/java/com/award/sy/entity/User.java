package com.award.sy.entity;

import com.award.core.annotation.ID;
import com.award.core.annotation.TableName;
import com.award.core.annotation.TempField;
import com.award.core.beans.Po;

/**
 * @描述：用户信息实体
 * @作者：bin
 * @版本：V1.0
 * @创建时间：：2018-04-10
 */
@TableName(name="tb_user")
public class User extends Po {
	
	@ID
	private Long   user_id;					//用户ID
	
	@TempField
	private String userIdStr;				
	
	private String head_img;			//头像
	
	private String birth;				//生日
	
	private String user_name;          //用户名
	
	private String password;          //密码
	
	private Integer sex;                 //性别
	
	private String country;
	
	private String create_time;
	
    private Integer invisible;           //是否隐身
    
    private String im_user_name; //环信username
    
    private Integer height;
    
    private String phone;
    
    private String open_id;
	
	public User() {
		super();
	}
	

	public Long getUser_id() {
		return user_id;
	}


	public void setUser_id(Long user_id) {
		this.userIdStr = user_id.toString();
		this.user_id = user_id;
	}
    

	public String getUserIdStr() {
		return userIdStr;
	}


	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}


	public String getHead_img() {
		return head_img;
	}


	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}


	public String getBirth() {
		return birth;
	}


	public void setBirth(String birth) {
		this.birth = birth;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getSex() {
		return sex;
	}


	public void setSex(Integer sex) {
		this.sex = sex;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getCreate_time() {
		return create_time;
	}


	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}


	public Integer getInvisible() {
		return invisible;
	}


	public void setInvisible(Integer invisible) {
		this.invisible = invisible;
	}
	

	public Integer getHeight() {
		return height;
	}


	public void setHeight(Integer height) {
		this.height = height;
	}


	public String getIm_user_name() {
		return im_user_name;
	}


	public void setIm_user_name(String im_user_name) {
		this.im_user_name = im_user_name;
	}

	
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getOpen_id() {
		return open_id;
	}


	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}


	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", userIdStr=" + userIdStr + ", head_img=" + head_img + ", birth=" + birth
				+ ", user_name=" + user_name + ", password=" + password + ", sex=" + sex + ", country=" + country
				+ ", create_time=" + create_time + ", height=" + height + ", invisible=" + ", im_user_name=" + im_user_name + invisible + "]";
	}


	
}