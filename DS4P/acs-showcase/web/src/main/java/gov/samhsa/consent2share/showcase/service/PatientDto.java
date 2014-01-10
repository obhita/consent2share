/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.showcase.service;

import org.hl7.v3.types.MCCIIN000002UV01;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;
import gov.samhsa.consent2share.c32.dto.GreenCCD;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;

public class PatientDto {
	private String id;
	private String fullName;
	private String domain;
	private String hieId;
	private String hieDomain;
	private String c32Xml;
	private GreenCCD greenCcd;
	private String hl7v3Xml;
	private String pixAddMsg;
	private String pixUpdateMsg;
	private PixManagerBean pixManagerBean;
	private boolean isError;
	private MCCIIN000002UV01 xdsbRegAddMsg;
	private MCCIIN000002UV01 xdsbRegUpdateMsg;
	private RegistryResponse xdsbRepoProvideMsg;
	private String eId;

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
	}

	public String getC32Xml() {
		return c32Xml;
	}

	public String getDomain() {
		return domain;
	}

	public String getFullName() {
		return fullName;
	}

	public GreenCCD getGreenCcd() {
		return greenCcd;
	}

	public String getHieDomain() {
		return hieDomain;
	}

	public String getHieId() {
		return hieId;
	}

	public String getHl7v3Xml() {
		return hl7v3Xml;
	}

	public String getId() {
		return id;
	}

	public String getPixAddMsg() {
		return pixAddMsg;
	}

	public PixManagerBean getPixManagerBean() {
		return pixManagerBean;
	}

	public String getPixUpdateMsg() {
		return pixUpdateMsg;
	}

	public boolean isError() {
		return isError;
	}

	public void setC32Xml(String c32Xml) {
		this.c32Xml = c32Xml;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setGreenCcd(GreenCCD greenCcd) {
		this.greenCcd = greenCcd;
	}

	public void setHieDomain(String hieDomain) {
		this.hieDomain = hieDomain;
	}

	public void setHieId(String hieId) {
		this.hieId = hieId;
	}

	public void setHl7v3Xml(String hl7v3Xml) {
		this.hl7v3Xml = hl7v3Xml;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPixAddMsg(String pixAddMsg) {
		this.pixAddMsg = pixAddMsg;
	}

	public void setPixManagerBean(PixManagerBean pixManagerBean) {
		this.pixManagerBean = pixManagerBean;
	}

	public void setPixUpdateMsg(String pixUpdateMsg) {
		this.pixUpdateMsg = pixUpdateMsg;
	}

	public MCCIIN000002UV01 getXdsbRegAddMsg() {
		return xdsbRegAddMsg;
	}

	public void setXdsbRegAddMsg(MCCIIN000002UV01 xdsbRegAddMsg) {
		this.xdsbRegAddMsg = xdsbRegAddMsg;
	}

	public MCCIIN000002UV01 getXdsbRegUpdateMsg() {
		return xdsbRegUpdateMsg;
	}

	public void setXdsbRegUpdateMsg(MCCIIN000002UV01 xdsbRegUpdateMsg) {
		this.xdsbRegUpdateMsg = xdsbRegUpdateMsg;
	}

	public RegistryResponse getXdsbRepoProvideMsg() {
		return xdsbRepoProvideMsg;
	}

	public void setXdsbRepoProvideMsg(RegistryResponse xdsbRepoProvideMsg) {
		this.xdsbRepoProvideMsg = xdsbRepoProvideMsg;
	}
}
