
package echosign.api.clientv15.service;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

@SuppressWarnings({ "unused", "unchecked" , "rawtypes"})
public class EchoSignDocumentService15Client {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public EchoSignDocumentService15Client() {
        create0();
        Endpoint EchoSignDocumentService15PortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://api.echosign", "EchoSignDocumentService15PortTypeLocalEndpoint"), new QName("http://api.echosign", "EchoSignDocumentService15PortTypeLocalBinding"), "xfire.local://EchoSignDocumentService15");
        endpoints.put(new QName("http://api.echosign", "EchoSignDocumentService15PortTypeLocalEndpoint"), EchoSignDocumentService15PortTypeLocalEndpointEP);
        Endpoint EchoSignDocumentService15HttpPortEP = service0 .addEndpoint(new QName("http://api.echosign", "EchoSignDocumentService15HttpPort"), new QName("http://api.echosign", "EchoSignDocumentService15HttpBinding"), "https://secure.echosign.com/services/EchoSignDocumentService15");
        endpoints.put(new QName("http://api.echosign", "EchoSignDocumentService15HttpPort"), EchoSignDocumentService15HttpPortEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

	public Collection getEndpoints() {
        return endpoints.values();
    }


	private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((echosign.api.clientv15.service.EchoSignDocumentService15PortType.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://api.echosign", "EchoSignDocumentService15HttpBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://api.echosign", "EchoSignDocumentService15PortTypeLocalBinding"), "urn:xfire:transport:local");
        }
    }

    public EchoSignDocumentService15PortType getEchoSignDocumentService15PortTypeLocalEndpoint() {
        return ((EchoSignDocumentService15PortType)(this).getEndpoint(new QName("http://api.echosign", "EchoSignDocumentService15PortTypeLocalEndpoint")));
    }

    public EchoSignDocumentService15PortType getEchoSignDocumentService15PortTypeLocalEndpoint(String url) {
        EchoSignDocumentService15PortType var = getEchoSignDocumentService15PortTypeLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public EchoSignDocumentService15PortType getEchoSignDocumentService15HttpPort() {
        return ((EchoSignDocumentService15PortType)(this).getEndpoint(new QName("http://api.echosign", "EchoSignDocumentService15HttpPort")));
    }

    public EchoSignDocumentService15PortType getEchoSignDocumentService15HttpPort(String url) {
        EchoSignDocumentService15PortType var = getEchoSignDocumentService15HttpPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

}
