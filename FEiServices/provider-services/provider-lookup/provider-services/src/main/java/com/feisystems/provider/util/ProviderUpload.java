package com.feisystems.provider.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.provider.Provider;
import com.feisystems.provider.dtos.ProviderDto;
import com.feisystems.provider.repository.ProviderRepository;
import com.feisystems.provider.services.InvalidCSVException;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderUpload.
 */
public class ProviderUpload {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProviderUpload.class);
	
	/** The Constant PROV_NPI_CELL. */
	public final static int PROV_NPI_CELL = 0;
	
	/** The Constant PROV_ENTITY_TYPE_CELL. */
	public final static int PROV_ENTITY_TYPE_CELL = 1;
	
	/** The Constant PROV_ORGANIZATION_NAME_CELL. */
	public final static int PROV_ORGANIZATION_NAME_CELL = 2;
	
	/** The Constant PROV_LAST_NAME_CELL. */
	public final static int PROV_LAST_NAME_CELL = 3;
	
	/** The Constant PROV_FIRST_NAME_CELL. */
	public final static int PROV_FIRST_NAME_CELL = 4;
	
	/** The Constant PROV_MIDDLE_NAME_CELL. */
	public final static int PROV_MIDDLE_NAME_CELL = 5;
	
	/** The Constant PROV_ADDRESS_CELL. */
	public final static int PROV_ADDRESS_CELL = 6;
	
	/** The Constant PROV_CITY_CELL. */
	public final static int PROV_CITY_CELL = 7;
	
	/** The Constant PROV_STATE_CELL. */
	public final static int PROV_STATE_CELL = 8;
	
	/** The Constant PROV_POSTAL_CODE_CELL. */
	public final static int PROV_POSTAL_CODE_CELL = 9;

	/** The Constant NPI. */
	public final static String NPI = "NPI";
	
	/** The Constant ENTITY_TYPE. */
	public final static String ENTITY_TYPE = "Entity Type";
	
	/** The Constant ORGANIZATION_NAME. */
	public final static String ORGANIZATION_NAME = "Organization Name";
	
	/** The Constant LAST_NAME. */
	public final static String LAST_NAME = "Last Name (Legal Name)";
	
	/** The Constant FIRST_NAME. */
	public final static String FIRST_NAME = "First Name";
	
	/** The Constant MIDDLE_NAME. */
	public final static String MIDDLE_NAME = "Middle Name";
	
	/** The Constant ADDRESS. */
	public final static String ADDRESS = "Address";
	
	/** The Constant CITY. */
	public final static String CITY = "City";
	
	/** The Constant STATE. */
	public final static String STATE = "State";
	
	/** The Constant POSTAL_CODE. */
	public final static String POSTAL_CODE = "Postal Code";

	/** The Constant PG_NPI_FILE. */
	public final static String PG_NPI_FILE = "C:/java/consent2share/PG_npi.xlsx";
	
	/** The Constant CMS_NPI_FILE. */
	public final static String CMS_NPI_FILE = "C:/java/consent2share/npidata_20050523-20140413.csv";
	
	/** The provider repository. */
	private static ProviderRepository providerRepository;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException, InterruptedException {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"providerUpload/applicationContext-config.xml",
	              "providerUpload/applicationContext-dataAccess.xml"});
		providerRepository = context.getBean(ProviderRepository.class);
		
		List<Provider> listOfProviders = providerRepository.findAll();
		LOGGER.warn("Size: " + listOfProviders.size());

		// delete all providers and insert from CMS
		deleteAllProvidersAndInsertFromCMS();
		
		// insert providers from PG list
		insertProviders(readProvidersFromFile());
		
		context.close();

	}


	/**
	 * Delete all providers and insert from cms.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InterruptedException 
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	private static void deleteAllProvidersAndInsertFromCMS() throws IOException, IllegalArgumentException, IllegalAccessException, InterruptedException {
		// empty database before inserting
		deleteAllProviders(); 
		
		// insert providers from CMS
		insertProvidersFromCMS();
	}


	/**
	 * Insert providers from cms.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InterruptedException 
	 */
	private static void insertProvidersFromCMS() throws IOException, IllegalArgumentException, IllegalAccessException, InterruptedException {
//		if (true)
//			throw new RuntimeException();
		// config the path of the npidata
		FileInputStream fstream = new FileInputStream(CMS_NPI_FILE);

		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		List<Provider> providers = new ArrayList<Provider>();

		int count = 0;
		
		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			String[] providerDetails = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			if (providerDetails[31].substring(1, (providerDetails[31].length() - 1)).equals("MD")
					|| providerDetails[31].substring(1, (providerDetails[31].length() - 1)).equals("DC")) {
				count++;

				// add provider to repository
				providers.add(convertToProvider(providerDetails));
				
				if (count % 50 == 0) {
					providerRepository.save(providers);
					providerRepository.flush();
					providers.clear();
				}
			}
		}
		if (!providers.isEmpty()) {
			providerRepository.save(providers);
			providerRepository.flush();
		}

		// Close the input stream
		br.close();
	}

	/**
	 * Convert to provider.
	 *
	 * @param providerDetails the provider details
	 * @return the provider
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	private static Provider convertToProvider(String[] providerDetails) throws IllegalArgumentException, IllegalAccessException {
		Provider provider = createProvider();

		provider.setNpi(providerDetails[0].substring(1, (providerDetails[0].length() - 1)));
		if (providerDetails[1].substring(1, (providerDetails[1].length() - 1)).equals("1"))
			provider.setEntityType("Individual");
		else
			provider.setEntityType("Organization");
		provider.setProviderOrganizationName(providerDetails[4].substring(1, (providerDetails[4].length() - 1)));
		provider.setProviderLastName(providerDetails[5].substring(1, (providerDetails[5].length() - 1)));
		provider.setProviderMiddleName(providerDetails[7].substring(1, (providerDetails[7].length() - 1)));
		provider.setProviderFirstName(providerDetails[6].substring(1, (providerDetails[6].length() - 1)));
		provider.setProviderFirstLineBusinessPracticeLocationAddress(providerDetails[28].substring(1,
				(providerDetails[28].length() - 1)));
		provider.setProviderSecondLineBusinessPracticeLocationAddress(providerDetails[29].substring(1,
				(providerDetails[29].length() - 1)));
		provider.setProviderBusinessPracticeLocationAddressCityName(providerDetails[30].substring(1,
				(providerDetails[30].length() - 1)));
		provider.setProviderBusinessPracticeLocationAddressStateName(providerDetails[31].substring(1,
				(providerDetails[31].length() - 1)));
		provider.setProviderBusinessPracticeLocationAddressPostalCode(providerDetails[32].substring(1,
				(providerDetails[32].length() - 1)));
		provider.setProviderBusinessPracticeLocationAddressTelephoneNumber(providerDetails[34].substring(1,
				(providerDetails[34].length() - 1)));
		
		provider.setProviderGenderCode(providerDetails[41].substring(1,
				(providerDetails[41].length() - 1)));
		
		provider.setLastUpdateDate(providerDetails[37].substring(1, (providerDetails[37].length() - 1)));
		provider.setProviderEnumerationDate(providerDetails[36].substring(1, (providerDetails[36].length() - 1)));

		// providerRepository.save(provider);
		return provider;
	}


	
	/**
	 * Delete all providers.
	 */
	private static void deleteAllProviders() {
		providerRepository.deleteAll();
	}


	/**
	 * Read providers from file.
	 *
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static List<ProviderDto> readProvidersFromFile() throws IOException {
		FileInputStream fis = new FileInputStream(PG_NPI_FILE);
		
		List<ProviderDto> listOfProviderDtos = new ArrayList<ProviderDto>();

		// Get the workbook instance for XLS file
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		// Get first sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);

		// Iterate through each rows from first sheet
		Iterator<Row> rowIterator = sheet.iterator();

		Row row = null;

		// reading header row
		if (rowIterator.hasNext()) {
			//rowIterator.next(); // discard
			Row headerRow = rowIterator.next();

			if (headerRow != null) {
				if (headerRow.getCell(PROV_NPI_CELL, Row.RETURN_BLANK_AS_NULL) == null
						|| headerRow.getCell(PROV_ENTITY_TYPE_CELL, Row.RETURN_BLANK_AS_NULL) == null) {
					throw new InvalidCSVException(
							"Header row values in file should be in the following format: NPI, Entity Type, Organization Name, Last Name (Legal Name), First Name, Middle Name, Address, City, State, Postal Code");

				} else if (!getCellValueAsString(headerRow.getCell(PROV_NPI_CELL, Row.RETURN_BLANK_AS_NULL))
						.equalsIgnoreCase(NPI)
						|| !getCellValueAsString(headerRow.getCell(PROV_ENTITY_TYPE_CELL, Row.RETURN_BLANK_AS_NULL))
								.equalsIgnoreCase(ENTITY_TYPE)) {
					throw new InvalidCSVException(
							"Header row values in file should be in the following format: NPI, Entity Type, Organization Name, Last Name (Legal Name), First Name, Middle Name, Address, City, State, Postal Code");
				}
			}
		}

		// iterate rows with value set fields
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			ProviderDto providerDto = new ProviderDto();

			if (row != null) {

				Cell npiCell = row.getCell(PROV_NPI_CELL, Row.RETURN_NULL_AND_BLANK);
				Cell entityTypeCell = row.getCell(PROV_ENTITY_TYPE_CELL, Row.RETURN_NULL_AND_BLANK);
				Cell organizationNameCell = row.getCell(PROV_ORGANIZATION_NAME_CELL, Row.RETURN_NULL_AND_BLANK);
				Cell lastNameCell = row.getCell(PROV_LAST_NAME_CELL, Row.RETURN_NULL_AND_BLANK);
				Cell firstNameCell = row.getCell(PROV_FIRST_NAME_CELL, Row.RETURN_NULL_AND_BLANK);
				Cell middleNameCell = row.getCell(PROV_MIDDLE_NAME_CELL, Row.RETURN_NULL_AND_BLANK);
				Cell addressCell = row.getCell(PROV_ADDRESS_CELL, Row.RETURN_NULL_AND_BLANK);
				Cell cityCell = row.getCell(PROV_CITY_CELL, Row.RETURN_NULL_AND_BLANK);
				Cell stateCell = row.getCell(PROV_STATE_CELL, Row.RETURN_NULL_AND_BLANK);
				Cell postalCodeCell = row.getCell(PROV_POSTAL_CODE_CELL, Row.RETURN_NULL_AND_BLANK);
				

				// ignore empty row and throw error on missing fields
				if (npiCell == null || entityTypeCell == null) {

					if (npiCell != null || entityTypeCell != null) {
						throw new InvalidCSVException("Required field(s) empty for row: " + (row.getRowNum() + 1));
					}
				} else {
					//providerDto.setUserName(userName);
					providerDto.setNpi(getCellValueAsString(npiCell));
					providerDto.setEntityType((getCellValueAsString(entityTypeCell)));
					providerDto.setProviderOrganizationName(getCellValueAsString(organizationNameCell));
					providerDto.setProviderLastName(getCellValueAsString(lastNameCell));
					providerDto.setProviderMiddleName(getCellValueAsString(middleNameCell));
					providerDto.setProviderFirstName(getCellValueAsString(firstNameCell));
					providerDto.setProviderFirstLineBusinessPracticeLocationAddress(getCellValueAsString(addressCell));
					providerDto.setProviderBusinessPracticeLocationAddressCityName(getCellValueAsString(cityCell));
					providerDto.setProviderBusinessPracticeLocationAddressStateName(getCellValueAsString(stateCell));
					providerDto.setProviderBusinessPracticeLocationAddressPostalCode(getCellValueAsString(postalCodeCell));
					
					listOfProviderDtos.add(providerDto);

				}
			}
		}
		return listOfProviderDtos;
	}


	/**
	 * Insert providers.
	 *
	 * @param listOfProviderDtos the list of provider dtos
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	private static void insertProviders(List<ProviderDto> listOfProviderDtos) throws IllegalArgumentException, IllegalAccessException {
		
		for (int i = 0; i < listOfProviderDtos.size(); i++) {
			ProviderDto providerDto = listOfProviderDtos.get(i);
			
			Provider provider = providerRepository.findOne(providerDto.getNpi());
			if (provider == null) {
				provider = createProvider();
			}
				
			provider.setNpi(providerDto.getNpi());
			provider.setEntityType(providerDto.getEntityType());
			provider.setProviderOrganizationName(providerDto.getProviderOrganizationName());
			provider.setProviderLastName(providerDto.getProviderLastName());
			provider.setProviderMiddleName(providerDto.getProviderMiddleName());
			provider.setProviderFirstName(providerDto.getProviderFirstName());
			provider.setProviderFirstLineBusinessPracticeLocationAddress(providerDto.getProviderFirstLineBusinessPracticeLocationAddress());
			provider.setProviderBusinessPracticeLocationAddressCityName(providerDto.getProviderBusinessPracticeLocationAddressCityName());
			provider.setProviderBusinessPracticeLocationAddressStateName(providerDto.getProviderBusinessPracticeLocationAddressStateName());
			provider.setProviderBusinessPracticeLocationAddressPostalCode(providerDto.getProviderBusinessPracticeLocationAddressPostalCode());

			providerRepository.save(provider);
		}
		
	}


	/**
	 * Gets the cell value as string.
	 * 
	 * @param cell
	 *            the cell
	 * @return the cell value as string
	 */
	public static String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return null;
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return cell.toString();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			DataFormatter formatter = new DataFormatter();
			String formattedValue = formatter.formatCellValue(cell);
			return formattedValue;
		} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return "";
		}
		else {
			throw new InvalidCSVException("Value stored in cell is invalid! Valid types are Numbers or Strings.");
		}
	}

	
	/**
	 * Creates the provider.
	 *
	 * @return the provider
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	private static Provider createProvider() throws IllegalArgumentException, IllegalAccessException {
		Provider provider = new Provider();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String date = sdf.format(new Date());
		provider.setLastUpdateDate(date);
		provider.setProviderEnumerationDate(date);
		
		setEmpty(provider);
		return provider;
	}
	
	/**
	 * Sets the empty.
	 *
	 * @param object the new empty
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public static void setEmpty(Object object) throws IllegalArgumentException, IllegalAccessException {
	    Class<?> clazz = object.getClass();
	    Field[] fields = clazz.getDeclaredFields();
	    for (Field field : fields) {
	        if (String.class.equals(field.getType())) {
	            field.setAccessible(true);
	            if (field.get(object) == null) {
	                field.set(object, "");
	            }
	        }
	    }
	}

}