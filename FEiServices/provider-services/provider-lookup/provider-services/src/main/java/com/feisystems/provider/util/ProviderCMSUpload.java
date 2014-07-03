package com.feisystems.provider.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import au.com.bytecode.opencsv.CSVReader;

import com.feisystems.provider.Provider;
import com.feisystems.provider.repository.ProviderRepository;

public class ProviderCMSUpload {
	
	private static ProviderRepository providerRepository;
	
	public static void main(String[] args) throws IOException {
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"providerUpload/applicationContext-config.xml",
        "providerUpload/applicationContext-dataAccess.xml"});
		providerRepository = context.getBean(ProviderRepository.class);
		
		//config the path of the npidata
		FileInputStream fstream = new FileInputStream("C:/split npi database/npidata_20050523-20140413.csv");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
	    List<Provider> providers=new ArrayList();
	    
		
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
			String[] providerDetails = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			if(providerDetails[31].substring(1,(providerDetails[31].length()-1)).equals("MD")||providerDetails[31].substring(1,(providerDetails[31].length()-1 )).equals("DC")){
		  // add provider to repository
				providers.add(convertToProvider(providerDetails));
				//
			}
		}

		//Close the input stream
		br.close();
		
		/*String csvFilename = "C:\\java\\consent2share\\npidata_20050523-20140413.csv";
		//String csvFilename = "C:\\java\\consent2share\\npidata_20050523-20140413-50_ROWS.csv";
		
		ProviderCMSUpload.readCSV(csvFilename);*/
	}
	
	public static Provider convertToProvider(String[] providerDetails){
		Provider provider=new Provider();
		
		
		provider.setNpi(providerDetails[0].substring(1,(providerDetails[0].length()-1)));
		if(providerDetails[1].substring(1,(providerDetails[1].length()-1)).equals("1"))
		provider.setEntityType("Individual");
		else provider.setEntityType("Organization");
		provider.setProviderOrganizationName(providerDetails[4].substring(1,(providerDetails[4].length()-1)));
		provider.setProviderLastName(providerDetails[5].substring(1,(providerDetails[5].length()-1)));
		provider.setProviderMiddleName(providerDetails[7].substring(1,(providerDetails[7].length()-1)));
		provider.setProviderFirstName(providerDetails[6].substring(1,(providerDetails[6].length()-1)));
		provider.setProviderFirstLineBusinessPracticeLocationAddress(providerDetails[28].substring(1,(providerDetails[28].length()-1)));
		provider.setProviderSecondLineBusinessPracticeLocationAddress(providerDetails[29].substring(1,(providerDetails[29].length()-1)));
		provider.setProviderBusinessPracticeLocationAddressCityName(providerDetails[30].substring(1,(providerDetails[30].length()-1)));
		provider.setProviderBusinessPracticeLocationAddressStateName(providerDetails[31].substring(1,(providerDetails[31].length()-1)));
		provider.setProviderBusinessPracticeLocationAddressPostalCode(providerDetails[32].substring(1,(providerDetails[32].length()-1)));
		provider.setProviderBusinessPracticeLocationAddressTelephoneNumber(providerDetails[34].substring(1,(providerDetails[34].length()-1)));
		
		
		provider.setLastUpdateDate(providerDetails[37].substring(1,(providerDetails[37].length()-1)));
		provider.setProviderEnumerationDate(providerDetails[36].substring(1,(providerDetails[36].length()-1)));
		
		providerRepository.save(provider);
		return provider;
	}
	
	public static void readCSV(String csvFilename) throws IOException {

		CSVReader csvReader = null;
		CSVReader reader = null;
		try {
			reader=new CSVReader(
				    new InputStreamReader(new FileInputStream(csvFilename)));
			csvReader = new CSVReader(new FileReader(csvFilename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] row = null;
		int numRows = 0;
		while((reader.readNext()) != null) {
			numRows++;
			if (numRows % 5000 == 0)
				System.out.println("Number of rows: " + numRows);
//		    System.out.println(row[0]
//		              + " # " + row[1]
//		              + " #  " + row[2]);
		}
		System.out.println("Number of rows total: " + numRows);
		//...
		csvReader.close();
	}

}