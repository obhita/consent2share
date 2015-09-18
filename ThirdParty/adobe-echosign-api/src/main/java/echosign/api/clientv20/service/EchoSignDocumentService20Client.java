
package echosign.api.clientv20.service;

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

public class EchoSignDocumentService20Client {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public EchoSignDocumentService20Client() {
        create0();
        Endpoint EchoSignDocumentService20PortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://api.echosign", "EchoSignDocumentService20PortTypeLocalEndpoint"), new QName("http://api.echosign", "EchoSignDocumentService20PortTypeLocalBinding"), "xfire.local://EchoSignDocumentService20");
        endpoints.put(new QName("http://api.echosign", "EchoSignDocumentService20PortTypeLocalEndpoint"), EchoSignDocumentService20PortTypeLocalEndpointEP);
        Endpoint EchoSignDocumentService20HttpPortEP = service0 .addEndpoint(new QName("http://api.echosign", "EchoSignDocumentService20HttpPort"), new QName("http://api.echosign", "EchoSignDocumentService20HttpBinding"), "https://secure.echosign.com/services/EchoSignDocumentService20");
        endpoints.put(new QName("http://api.echosign", "EchoSignDocumentService20HttpPort"), EchoSignDocumentService20HttpPortEP);
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
        service0 = asf.create((echosign.api.clientv20.service.EchoSignDocumentService20PortType.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://api.echosign", "EchoSignDocumentService20HttpBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://api.echosign", "EchoSignDocumentService20PortTypeLocalBinding"), "urn:xfire:transport:local");
        }
    }

    public EchoSignDocumentService20PortType getEchoSignDocumentService20PortTypeLocalEndpoint() {
        return ((EchoSignDocumentService20PortType)(this).getEndpoint(new QName("http://api.echosign", "EchoSignDocumentService20PortTypeLocalEndpoint")));
    }

    public EchoSignDocumentService20PortType getEchoSignDocumentService20PortTypeLocalEndpoint(String url) {
        EchoSignDocumentService20PortType var = getEchoSignDocumentService20PortTypeLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public EchoSignDocumentService20PortType getEchoSignDocumentService20HttpPort() {
        return ((EchoSignDocumentService20PortType)(this).getEndpoint(new QName("http://api.echosign", "EchoSignDocumentService20HttpPort")));
    }

    public EchoSignDocumentService20PortType getEchoSignDocumentService20HttpPort(String url) {
        EchoSignDocumentService20PortType var = getEchoSignDocumentService20HttpPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

}
