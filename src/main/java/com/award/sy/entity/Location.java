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
@TableName(name="tb_location")
public class Location extends Po {
	
	@ID
	private Long   loc_id;					//地址ID
	
	@TempField
	private String locIdStr;				
	
	private Double lat;			//维度
	
	private Double lng;				//经度
	
	private String last_time;      //最后一次位置的时间
	
    private Long user_id;          //用户id 
    
    @TempField
    private User user;
	
	public Location() {
		super();
	}
	

	public Long getLoc_id() {
		return loc_id;
	}


	public void setLoc_id(Long loc_id) {
		this.locIdStr = loc_id.toString();
		this.loc_id = loc_id;
	}
    

	public String getLocIdStr() {
		return locIdStr;
	}


	public void setLocIdStr(String locIdStr) {
		this.locIdStr = locIdStr;
	}
	

	public Double getLat() {
		return lat;
	}


	public void setLat(Double lat) {
		this.lat = lat;
	}


	public Double getLng() {
		return lng;
	}


	public void setLng(Double lng) {
		this.lng = lng;
	}


	public String getLast_time() {
		return last_time;
	}


	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}


	public Long getUser_id() {
		return user_id;
	}


	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "Location [loc_id=" + loc_id + ", locIdStr=" + locIdStr
				+ ", lat=" + lat + ", lng=" + lng
				+ ", last_time=" + last_time + ", user_id=" + user_id
				+ ", birth=" + user.getBirth() + ", head_img=" + user.getHead_img()
				+ "]";
	}
	
}