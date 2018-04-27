package com.award.sy.entity;

import com.award.core.annotation.ID;
import com.award.core.annotation.TableName;
import com.award.core.annotation.TempField;
import com.award.core.beans.Po;

@TableName(name="tb_group")
public class Group extends Po {
	
	@ID
	private Long   group_id;					//组ID
	
	@TempField
	private String groupIdStr;				
	
	private String create_time;
	
	private String group_name;
	
	private String im_group_id;
	
	private Long admin_id;
	
	public Group() {
		super();
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.groupIdStr = group_id.toString();
		this.group_id = group_id;
	}

	public String getGroupIdStr() {
		return groupIdStr;
	}

	public void setGroupIdStr(String groupIdStr) {
		this.groupIdStr = groupIdStr;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}   	
	
	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}	
	

	public String getIm_group_id() {
		return im_group_id;
	}

	public void setIm_group_id(String im_group_id) {
		this.im_group_id = im_group_id;
	}	
	

	public Long getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(Long admin_id) {
		this.admin_id = admin_id;
	}

	@Override
	public String toString() {
		return "Group [group_id=" + group_id + ", groupIdStr=" + groupIdStr + ", create_time=" + create_time + ", group_name=" + group_name
				+ ", im_group_id=" + im_group_id + ", admin_id=" + admin_id
				+ "]";
	}

}
