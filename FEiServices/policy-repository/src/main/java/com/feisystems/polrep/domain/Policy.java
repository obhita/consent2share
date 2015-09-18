package com.feisystems.polrep.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "policies")
@Audited
public class Policy {

	@Id
	private String id;

	private boolean valid;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] policy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public byte[] getPolicy() {
		return policy;
	}

	public void setPolicy(byte[] policy) {
		this.policy = policy;
	}
}
