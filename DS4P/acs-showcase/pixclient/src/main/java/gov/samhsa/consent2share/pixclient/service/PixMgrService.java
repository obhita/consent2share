package gov.samhsa.consent2share.pixclient.service;

import gov.samhsa.consent2share.pixclient.ws.MCCIIN000002UV01;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201304UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201310UV02;

public interface PixMgrService {

	public abstract MCCIIN000002UV01 pixManagerPRPAIN201301UV02(
			PRPAIN201301UV02 body);

	public abstract MCCIIN000002UV01 pixManagerPRPAIN201302UV02(
			PRPAIN201302UV02 body);

	public abstract MCCIIN000002UV01 pixManagerPRPAIN201304UV02(
			PRPAIN201304UV02 body);

	public abstract PRPAIN201310UV02 pixManagerPRPAIN201309UV02(
			PRPAIN201309UV02 body);
	
}