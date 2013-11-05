package gov.samhsa.consent2share.showcase.domain;

import java.util.ArrayList;
import java.util.List;

public class PixMgrDto {
	
	private PixUserDto[] piUserDtos;
	private PixUserDto selectedPixUser;
	
	public PixMgrDto(){
		List<PixUserDto> useractionsList =  new ArrayList<PixUserDto>();
		
		PixUserDto addUserUd = new PixUserDto();
		addUserUd.setpDomainName("unKnownDomain");
		addUserUd.setpId("SC4864");
		addUserUd.setPXML("xml/PRPA_IN201301UV02_PIXADD_UVD_Req.xml");
		useractionsList.add(addUserUd);
		
		PixUserDto addUserOne = new PixUserDto();
		addUserOne.setpDomainName("D-NIST2010");
		addUserOne.setpId("JW-824-v3");
		addUserOne.setPXML("xml/PRPA_IN201301UV02_PIXADD_VD1_Req.xml");
		useractionsList.add(addUserOne);
		

		PixUserDto addUserTwo = new PixUserDto();
		addUserTwo.setpDomainName("D-NIST2010-2");
		addUserTwo.setpId("JW26957-v3");
		addUserTwo.setPXML("xml/PRPA_IN201301UV02_PIXADD_VD2_Req.xml");
		useractionsList.add(addUserTwo);
		
		PixUserDto addUserThree = new PixUserDto();
		addUserThree.setpDomainName(">D-NIST2010-3");
		addUserThree.setpId("JW00598v3");
		addUserThree.setPXML("xml/PRPA_IN201301UV02_PIXADD_VD3_Req.xml");
		useractionsList.add(addUserThree);
		
		piUserDtos = useractionsList.toArray(new PixUserDto[0]);
		
	}

}
