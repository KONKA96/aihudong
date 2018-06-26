package com.model;

import java.util.Date;

public class Message {
    private Integer id;

    private String messageName;

    private Integer messageType;

    private String messageContent;

    private String messagePic;

    private String adminId;

    private Date startTime;

    private Date endTime;

    private Integer messageState;

    private String zoneId;

    private String buildingId;

    private String roomId;
    
    private Admin admin;

    public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName == null ? null : messageName.trim();
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    public String getMessagePic() {
        return messagePic;
    }

    public void setMessagePic(String messagePic) {
        this.messagePic = messagePic == null ? null : messagePic.trim();
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getMessageState() {
        return messageState;
    }

    public void setMessageState(Integer messageState) {
        this.messageState = messageState;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId == null ? null : zoneId.trim();
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId == null ? null : buildingId.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

	@Override
	public String toString() {
		return "Message [id=" + id + ", messageName=" + messageName + ", messageType=" + messageType
				+ ", messageContent=" + messageContent + ", messagePic=" + messagePic + ", adminId=" + adminId
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", messageState=" + messageState + ", zoneId="
				+ zoneId + ", buildingId=" + buildingId + ", roomId=" + roomId + ", admin=" + admin + "]";
	}
    
}