/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package com.feisystems.polrep.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

public class JAXBUtils {

	public static String marshal(Object obj) throws JAXBException {
		return marshal(obj, obj.getClass());
	}

	private static <T> String marshal(Object obj, Class<T> contextClass)
			throws JAXBException {
		final JAXBContext context = JAXBContext.newInstance(contextClass);
		final Marshaller marshaller = context.createMarshaller();
		final StringWriter stringWriter = new StringWriter();
		marshaller.marshal(obj, stringWriter);
		final String output = stringWriter.toString();
		return output;
	}

	@SuppressWarnings("unchecked")
	public static <T> String marshalWithoutRootElement(T obj)
			throws JAXBException {
		final JAXBElement<T> wrappedJaxbElement = new JAXBElement<T>(new QName(
				obj.getClass().getSimpleName()), (Class<T>) obj.getClass(), obj);
		return marshal(wrappedJaxbElement, obj.getClass());
	}

	public static <T> T unmarshalFromXml(Class<T> clazz, String xml)
			throws JAXBException, UnsupportedEncodingException {
		final JAXBContext context = JAXBContext.newInstance(clazz);
		return unmarshalFromXml(context, clazz, xml);
	}

	@SuppressWarnings("unchecked")
	private static <T> T unmarshalFromXml(JAXBContext context, Class<T> clazz,
			String xml) throws JAXBException, UnsupportedEncodingException {
		final Unmarshaller um = context.createUnmarshaller();
		final ByteArrayInputStream input = new ByteArrayInputStream(
				xml.getBytes("UTF-8"));
		return (T) um.unmarshal(input);
	}
}
