package gov.samhsa.consent2share.domain.valueset;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class AbstractNode {
	
	@Column(name = "code", nullable = false)
	protected String code;	
	
	@Column(name = "name", nullable = false)
	protected String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	protected Date creationTime;
    
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	protected Date modificationTime;

	@Column(name = "user_name", nullable = false)
	protected String userName;

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}	
	
	protected Date getCurrentDate() throws ParseException {
		String res = "";
		Date dt = new Date();
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			res = sdt.format(dt);
		} catch (Exception ex2) {
			ex2.printStackTrace();
		}

		return sdt.parse(res);
	}
	


	@PrePersist
    public void prePersist() {
		try {
			modificationTime = getCurrentDate();
			creationTime = getCurrentDate();
		} catch (ParseException e) {
			modificationTime = new Date();
			creationTime = new Date();
		}     		
    }

	@PreUpdate
    public void preUpdate() {
		try {
			modificationTime = getCurrentDate();
		} catch (ParseException e) {
			modificationTime = new Date();
		}    	
    }
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }	
}
