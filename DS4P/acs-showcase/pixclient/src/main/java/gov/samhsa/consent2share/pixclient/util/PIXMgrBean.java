package gov.samhsa.consent2share.pixclient.util;

import org.springframework.stereotype.Component;

@Component
public class PIXMgrBean {
	
	private String addMessage="";
	private String updateMessage="";
	private String queryMessage="";
	
	public String getAddMessage() {
		return addMessage;
	}
	public void setAddMessage(String addMessage) {
		this.addMessage = addMessage;
	}
	public String getUpdateMessage() {
		return updateMessage;
	}
	public void setUpdateMessage(String updateMessage) {
		this.updateMessage = updateMessage;
	}
	public String getQueryMessage() {
		return queryMessage;
	}
	public void setQueryMessage(String queryMessage) {
		this.queryMessage = queryMessage;
	}

	
	

}
