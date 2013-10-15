package gov.samhsa.consent2share.accesscontrolservice.common.tool;

import javax.xml.bind.JAXBException;

/**
 * The Interface SimpleMarshaller.
 */
public interface SimpleMarshaller {

	/**
	 * Unmarshall from xml string to generic type object.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param xml
	 *            the xml
	 * @return the t
	 * @throws JAXBException
	 *             the jAXB exception
	 */
	public abstract <T> T unmarshallFromXml(Class<T> clazz, String xml)
			throws JAXBException;

	/**
	 * Marshall from object to xml string.
	 * 
	 * @param obj
	 *            the obj
	 * @return the string
	 * @throws Throwable
	 *             the throwable
	 */
	public abstract String marshall(Object obj) throws Throwable;

}