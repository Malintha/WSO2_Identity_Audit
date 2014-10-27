package org.wso2.carbon.identity.audit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.swing.BakedArrayList;

import javax.xml.namespace.QName;

/**
 * Created by malintha on 9/25/14.
 */
public class AuditHandler extends AbstractHandler {

	String wsaMessageId = null;
	static String messageIDStr;
	private static Log audit = LogFactory.getLog(AuditHandler.class);
	static String logMessage;
	@Override
	public InvocationResponse invoke(MessageContext messageContext)
			throws AxisFault {

		SOAPEnvelope mes = messageContext.getEnvelope();
		SOAPHeader mesh = mes.getHeader();
		SOAPBody mesb = mes.getBody();
		OMElement bodyChild = mesb.getFirstElement();

		HashMap<String, String> datahm = new HashMap<String, String>();
		messageIDStr = null;

		ArrayList<SOAPHeaderBlock> al = mesh
				.getHeaderBlocksWithNSURI("http://www.w3.org/2005/08/addressing");

		for (SOAPHeaderBlock shb : al) {
			if (shb.getLocalName().contains("To")
					&& shb.getText().contains("AuthenticationAdmin")
					&& bodyChild.getLocalName().toLowerCase().contains("login")) {
				
				if (bodyChild != null) {
					OMElement username = bodyChild
							.getFirstChildWithName(new QName(
									"http://authentication.services.core.carbon.wso2.org",
									"username"));
					OMElement remoteAddress = bodyChild
							.getFirstChildWithName(new QName(
									"http://authentication.services.core.carbon.wso2.org",
									"remoteAddress"));
					OMElement messageId = mesh.getFirstChildWithName(new QName("http://www.w3.org/2005/08/addressing","MessageID"));
					if (username != null) {
						datahm.put("username", username.getText());
                        logMessage = username.getText();
						}
					if (remoteAddress != null) {
						datahm.put("remoteAddress", remoteAddress.getText());
                        logMessage+="#"+remoteAddress.getText();
					}
					if(messageId != null) {
						messageIDStr = messageId.getText();
						datahm.put("mesageId", messageIDStr);
//						audit.info("AuthenticationAdmin:"+datahm);
					}		
				}
			}
			
			if(shb.getLocalName().contains("Action") && shb.getText().toLowerCase().contains("loginresponse")) {
				OMElement relatesTo = mesh.getFirstChildWithName(new QName("http://www.w3.org/2005/08/addressing","RelatesTo"));
					if(relatesTo != null ) {
						datahm.put("resMessageId", relatesTo.getText());
                        logMessage += "#"+relatesTo.getText();
						if(bodyChild != null) {
							OMElement returnVal = bodyChild.getFirstChildWithName(new QName("http://authentication.services.core.carbon.wso2.org","return"));
							datahm.put("response", returnVal.getText());
							logMessage += "#"+returnVal.getText();
                            audit.info("AuthenticationAdmin#"+logMessage);
//                            audit.info("AuthenticationAdmin"+datahm);
							datahm.clear();
						}
				}
			}
		}

		return InvocationResponse.CONTINUE;
	}
}

/*
 * the axis2 handler should log, remote ip address, request
 * successful/unsuccessful username
 */

/*
 * 
 * AuditIn handler : get <wsa:to> value and pass the soap envelope to
 * AuthenticationAdminLogger getInFlowMessage(). in AuthenticationAdminLogger
 * get the username, remoteIp, messageID
 */
