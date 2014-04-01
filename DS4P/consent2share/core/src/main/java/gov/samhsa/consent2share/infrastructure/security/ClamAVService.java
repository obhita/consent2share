package gov.samhsa.consent2share.infrastructure.security;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.taldius.clamav.ClamAVScanner;
import net.taldius.clamav.ClamAVScannerFactory;

@Component
public class ClamAVService implements InitializingBean{
	
	// Host where 'clamd' process is running
	@Value("${clamdHost}")
    private String clamdHost;
   
    // Port on which 'clamd' process is listening
	@Value("${clamdPort}")
    private String clamdPort;
   
    // Connection time out to connect 'clamd' process
	@Value("${connTimeOut}")
    private String connTimeOut;
   
    private ClamAVScanner scanner;
   
    public void setClamdHost(String clamdHost){
           this.clamdHost = clamdHost;
    }
   
    public String getClamdHost(){
           return this.clamdHost;
    }
   
    public void setClamdPort(String clamdPort){
           this.clamdPort = clamdPort;
    }
   
    public String getClamdPort(){
           return this.clamdPort;
    }
   
    public void setConnTimeOut(String connTimeOut){
           this.connTimeOut = connTimeOut;
    }
   
    public String getConnTimeOut(){
           return this.connTimeOut;
    }
    
	@Override
	public void afterPropertiesSet() throws Exception {
		initScanner();
	}
   
    /**
     * Method to initialize clamAV scanner
     */
    public void initScanner(){
          
           ClamAVScannerFactory.setClamdHost(clamdHost);

           ClamAVScannerFactory.setClamdPort(Integer.parseInt(clamdPort));

           int connectionTimeOut = Integer.parseInt(connTimeOut);
          
           if (connectionTimeOut > 0) {
                  ClamAVScannerFactory.setConnectionTimeout(connectionTimeOut);
           }
           this.scanner = ClamAVScannerFactory.getScanner();
    }

    public ClamAVScanner getClamAVScanner() {
           return scanner;
    }

    /**
     * Method scans files to check whether file is virus infected
     *
     * @param destFilePath file path
     * @return
     * @throws Exception
     */
    public boolean fileScanner(String destFilePath) throws Exception  {

           return fileScanner(new FileInputStream(destFilePath));
    }

    /**
     * Method scans files to check whether file is virus infected
     *
     * @param fileInputStream
     * @return
     * @throws Exception
     */
    public boolean fileScanner(InputStream fileInputStream) throws Exception {

           boolean resScan = false;
           initScanner();

           if (fileInputStream != null) {

                  resScan = scanner.performScan(fileInputStream);

           } else {

                  throw new Exception();
           }
           return resScan;
    }

}
