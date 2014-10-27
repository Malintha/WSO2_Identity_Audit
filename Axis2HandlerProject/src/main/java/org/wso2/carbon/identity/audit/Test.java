package org.wso2.carbon.identity.audit;

import org.apache.axiom.om.ds.InputStreamDataSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPEnvelope;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by malintha on 10/7/14.
 */
public class Test {
//    public static void main(String[] args) {
//
//        String env = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\"><soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:To>local://services/AuthenticationAdmin</wsa:To><wsa:MessageID>urn:uuid:5efeaea9-4b40-458f-8f88-78d3370d8010</wsa:MessageID><wsa:Action>urn:login</wsa:Action></soapenv:Header><soapenv:Body><ns3:login xmlns:ns3=\"http://authentication.services.core.carbon.wso2.org\"><ns3:username>admin</ns3:username><ns3:password>admin</ns3:password><ns3:remoteAddress>127.0.0.1</ns3:remoteAddress></ns3:login></soapenv:Body></soapenv:Envelope>";
//        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//        documentBuilderFactory.setNamespaceAware(true);
//        DocumentBuilder docBuilder = null;
//        Document document = null;
//        try {
//            docBuilder = documentBuilderFactory.newDocumentBuilder();
//            document = docBuilder.parse(new ByteArrayInputStream(env.trim().getBytes()));
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//               
//
//    }
}
