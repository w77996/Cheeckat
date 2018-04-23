package com.award.sy.entity;

import com.award.core.annotation.ID;
import com.award.core.annotation.TableName;
import com.award.core.annotation.TempField;
import com.award.core.beans.Po;

@TableName(name="tb_group_details")
public class GroupDetails extends Po {
	@ID
	private Long   details_id;					//组明细ID
	
	@TempField
	private String detailsIdStr;				
	
	private Long member_id;
	
	private Long group_id;
	
	private Integer is_admin;
	
	private String join_time;
	
	public GroupDetails() {
		super();
	}

	public Long getDetails_id() {
		return details_id;
	}

	public void setDetails_id(Long details_id) {
		this.details_id = details_id;
	}

	public String getDetailsIdStr() {
		return detailsIdStr;
	}

	public void setDetailsIdStr(String detailsIdStr) {
		this.detailsIdStr = detailsIdStr;
	}

	public Long getMember_id() {
		return member_id;
	}

	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Integer getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(Integer is_admin) {
		this.is_admin = is_admin;
	}

	public String getJoin_time() {
		return join_time;
	}

	public void setJoin_time(String join_time) {
		this.join_time = join_time;
	}
	
	@Override
	public String toString() {
		return "GroupDetails [details_id=" + details_id + ", detailsIdStr=" + detailsIdStr + ", member_id=" + member_id
				+ ", group_id=" + group_id + ", is_admin=" + is_admin + ", join_time=" + join_time
				+ "]";
	}


}
