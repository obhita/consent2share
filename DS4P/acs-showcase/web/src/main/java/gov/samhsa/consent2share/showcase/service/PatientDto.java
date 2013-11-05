package gov.samhsa.consent2share.showcase.service;

import gov.samhsa.consent2share.c32.dto.GreenCCD;

public class PatientDto {
	private String id;
	private String fullName;
	private String domain;
	private String hieId;
	private String hieDomain;
	private String c32Xml;
	private GreenCCD greenCcd;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getHieId() {
		return hieId;
	}

	public void setHieId(String hieId) {
		this.hieId = hieId;
	}

	public String getHieDomain() {
		return hieDomain;
	}

	public void setHieDomain(String hieDomain) {
		this.hieDomain = hieDomain;
	}

	public String getC32Xml() {
		return c32Xml;
	}

	public void setC32Xml(String c32Xml) {
		this.c32Xml = c32Xml;
	}

	public GreenCCD getGreenCcd() {
		return greenCcd;
	}

	public void setGreenCcd(GreenCCD greenCcd) {
		this.greenCcd = greenCcd;
	}
}
