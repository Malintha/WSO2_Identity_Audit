package org.wso2.carbon.identity.audit;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by malintha on 9/25/14.
 */
public class AuditHandler extends AbstractHandler {

    private static Log audit = LogFactory.getLog(AuditHandler.class);
    @Override
    public InvocationResponse invoke(MessageContext messageContext) throws AxisFault {
        audit.info(messageContext.getEnvelope());
        SOAPEnvelope mes = messageContext.getEnvelope();

        return InvocationResponse.CONTINUE;
    }
}


/*
the axis2 handler should log,
remote ip address,
request successful/unsuccessful
username
 */

/*



AuditIn handler : get <wsa:to> value and pass the soap envelope to AuthenticationAdminLogger getInFlowMessage().
    in AuthenticationAdminLogger get the username, remoteIp, messageID 
 */