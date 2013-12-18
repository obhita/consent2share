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
package gov.samhsa.acs.brms.tool;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import com.googlecode.sardine.util.SardineException;


/**
 * The Class FactModelGuvnorUploader uploads the fact model jar to given Guvnor factmodel URI.
 */
public class FactModelGuvnorUploader {
	
	/** The Constant FACTMODEL_JAR_PATH. */
	private final static String FACTMODEL_JAR_PATH = "../factmodel/target/brms-factmodel.jar";
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FactModelGuvnorUploader.class);
	
	/**
	 * The main method.
	 *
	 * @param args need 3 the arguments (username, password and factmodel-put-url)
	 */
	public static void main(String[] args)
	{		
		if(args.length!=3)
		{
			LOGGER.error("ERROR: FactModelGuvnorUploader needs 3 arguments: username, password, factmodel-put-url");
			System.exit(1);
		}
	
		Sardine sardine = null;
		try {
			sardine = SardineFactory.begin(args[0], args[1]);
		} catch (SardineException e) {
			LOGGER.error("ERROR: Connection couldn't be opened.");
			LOGGER.error(e.getMessage(),e);			
		}
		byte[] data = null;
		try {
			data = IOUtils.toByteArray(new FileInputStream(FACTMODEL_JAR_PATH));
		} catch (FileNotFoundException e) {
			LOGGER.error("ERROR: File not found");
			LOGGER.error(e.getMessage(),e);
		} catch (IOException e) {
			LOGGER.error("ERROR: IOException");
			LOGGER.error(e.getMessage(),e);
		}
		try {
			sardine.put(args[2], data);
		} catch (SardineException e) {
			LOGGER.error("ERROR: File couldn't be put.");
			LOGGER.error(e.getMessage(),e);
		}
	
	}
}
