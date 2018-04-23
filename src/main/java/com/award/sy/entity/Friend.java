package com.award.sy.entity;

import com.award.core.annotation.ID;
import com.award.core.annotation.TableName;
import com.award.core.annotation.TempField;
import com.award.core.beans.Po;

@TableName(name="tb_friend")
public class Friend extends Po {

	
	@ID
	private Long   friend_id;					//好友ID
	
	@TempField
	private String friendIdStr;				
	
	private Long user_id_fr1;			//用户1
	
	private Long user_id_fr2;				//用户2
	
	private String create_time;      //创建时间
	
    private Integer status;          //状态

	
	public Friend() {
		super();
	}	


	public Long getFriend_id() {
		return friend_id;
	}


	public void setFriend_id(Long friend_id) {
		this.friendIdStr = friend_id.toString();
		this.friend_id = friend_id;
	}


	public String getFriendIdStr() {
		return friendIdStr;
	}


	public void setFriendIdStr(String friendIdStr) {
		this.friendIdStr = friendIdStr;
	}


	public Long getUser_id_fr1() {
		return user_id_fr1;
	}


	public void setUser_id_fr1(Long user_id_fr1) {
		this.user_id_fr1 = user_id_fr1;
	}


	public Long getUser_id_fr2() {
		return user_id_fr2;
	}


	public void setUser_id_fr2(Long user_id_fr2) {
		this.user_id_fr2 = user_id_fr2;
	}


	public String getCreate_time() {
		return create_time;
	}


	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Friend [friend_id=" + friend_id + ", friendIdStr=" + friendIdStr
				+ ", user_id_fr1=" + user_id_fr1 + ", user_id_fr2=" + user_id_fr2
				+ ", create_time=" + create_time + ", status=" + status
				+ "]";
	}
	


}
