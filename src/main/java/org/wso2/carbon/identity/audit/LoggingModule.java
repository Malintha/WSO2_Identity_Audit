package org.wso2.carbon.identity.audit;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisDescription;
import org.apache.axis2.description.AxisModule;
import org.apache.axis2.modules.Module;


import java.security.Policy;

/**
 * Created by malintha on 9/25/14.
 */
public class LoggingModule implements Module {

    public void init(ConfigurationContext configurationContext, AxisModule axisModule) throws AxisFault {

    }

    public void engageNotify(AxisDescription axisDescription) throws AxisFault {

    }

    public void shutdown(ConfigurationContext configurationContext) throws AxisFault {

    }

    public void applyPolicy(org.apache.neethi.Policy policy, AxisDescription axisDescription) throws AxisFault {

    }

    public boolean canSupportAssertion(org.apache.neethi.Assertion assertion) {
        return false;
    }
}
