package com.feisystems.polrep.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "JAXBTestElement")
@XmlAccessorType(XmlAccessType.FIELD)
public class JAXBTestElement {
	private String field1;
	private String field2;

	public String getField1() {
		return field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

}
