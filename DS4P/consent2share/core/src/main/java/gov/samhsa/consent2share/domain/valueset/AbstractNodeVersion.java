package gov.samhsa.consent2share.domain.valueset;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class AbstractNodeVersion {
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(nullable = false)
	protected Date uploadEffectiveDate;
	
	@Column(name = "user_name", nullable = false)
	protected String userName;
	
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
	

	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }



	public Date getUploadEffectiveDate() {
		return uploadEffectiveDate;
	}



	public void setUploadEffectiveDate(Date uploadEffectiveDate) {
		this.uploadEffectiveDate = uploadEffectiveDate;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}	
    
    
}
