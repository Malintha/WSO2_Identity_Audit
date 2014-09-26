package org.wso2.carbon.accesslogpublisher;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;
import org.apache.catalina.AccessLog;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.catalina.valves.ValveBase;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.wso2.carbon.context.CarbonContext;

import java.net.InetAddress;
import java.util.Date;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by malintha on 9/16/14.
 */
public class TestAccessLogsValve extends AccessLogValve {
    private static Logger log = Logger.getLogger(TestAccessLogsValve.class);
    private static ThreadLocal addToThreadLocal = new ThreadLocal();

    public void invoke(Request request, Response response) throws IOException, ServletException {

        HashedMap hm = new HashedMap();
        AccessLogElement requestURI = createAccessLogElement('U');
        AccessLogElement remoteIP = createAccessLogElement('a');
        AccessLogElement remoteHostName = createAccessLogElement('h');
        AccessLogElement requestMethod = createAccessLogElement('m');
        StringBuilder requestURIStr = new StringBuilder();
        StringBuilder remoteIPStr = new StringBuilder();
        StringBuilder remoteHostNameStr = new StringBuilder();
        StringBuilder requestMethodStr = new StringBuilder();
        Date date = new Date();
        requestURI.addElement(requestURIStr,date ,request,response,0);
        remoteIP.addElement(remoteIPStr,date ,request,response,0);
        remoteHostName.addElement(remoteHostNameStr,date ,request,response,0);
        requestMethod.addElement(requestMethodStr,date ,request,response,0);

        hm.put("requestURI",requestURIStr);
        hm.put("remoteIP",remoteIPStr);
        hm.put("remoteHostName",remoteHostNameStr);
        hm.put("requestMethod",requestMethodStr);

        addToThreadLocal.set(hm);
        log.info("Set ThreadLocal Hashmap");
        next.invoke(request,response);
 }
}
