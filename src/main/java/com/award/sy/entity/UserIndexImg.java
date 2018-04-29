package com.award.sy.entity;

import com.award.core.annotation.ID;
import com.award.core.annotation.TableName;
import com.award.core.annotation.TempField;
import com.award.core.beans.Po;
/**
 * 用户主页图片
 * @ClassName:       UserIndexImg
 * @Description:     TODO
 * @author:          w77996
 * @date:            2018年4月27日        上午11:56:31
 */
@TableName(name="tb_user_index_img")
public class UserIndexImg extends Po{

	@ID
	private Long   user_img_id;					//图片ID
	
	@TempField
	private String userIndexImgIdStr;		
	
	private Long user_id;
	
	private String img;			//图片
	
	private String create_time;				//生成时间

	public Long getUser_img_id() {
		return user_img_id;
	}

	public void setUser_img_id(Long user_img_id) {
		this.user_img_id = user_img_id;
	}

	public String getUserImgIdStr() {
		return userIndexImgIdStr;
	}

	public void setUserImgIdStr(String userImgIdStr) {
		this.userIndexImgIdStr = userImgIdStr;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "UserIndexImg [user_img_id=" + user_img_id + ", userImgIdStr="
				+ userIndexImgIdStr + ", user_id=" + user_id + ", img=" + img
				+ ", create_time=" + create_time + "]";
	}
	
	
	
	
	
}
