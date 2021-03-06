package admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;
import org.apache.jasper.runtime.*;
import java.beans.*;
import org.apache.jasper.JasperException;
import org.apache.soap.*;
import org.apache.soap.server.*;


public class _0002fadmin_0002fundeploy_0002ejspundeploy_jsp_0 extends HttpJspBase {


    static {
    }
    public _0002fadmin_0002fundeploy_0002ejspundeploy_jsp_0( ) {
    }

    private static boolean _jspx_inited = false;

    public final void _jspx_init() throws JasperException {
    }

    public void _jspService(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {

        JspFactory _jspxFactory = null;
        PageContext pageContext = null;
        HttpSession session = null;
        ServletContext application = null;
        ServletConfig config = null;
        JspWriter out = null;
        Object page = this;
        String  _value = null;
        try {

            if (_jspx_inited == false) {
                _jspx_init();
                _jspx_inited = true;
            }
            _jspxFactory = JspFactory.getDefaultFactory();
            response.setContentType("text/html;charset=ISO-8859-1");
            pageContext = _jspxFactory.getPageContext(this, request, response,
			"", true, 8192, true);

            application = pageContext.getServletContext();
            config = pageContext.getServletConfig();
            session = pageContext.getSession();
            out = pageContext.getOut();

            // HTML // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(0,38);to=(1,0)]
                out.write("\r\n");
            // end
            // HTML // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(1,45);to=(5,0)]
                out.write("\r\n\r\n<h1>Un-Deploy a Service</h1>\r\n\r\n");
            // end
            // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(5,2);to=(16,4)]
                 
                ServiceManager serviceManager =
                  org.apache.soap.server.http.ServerHTTPUtils.getServiceManagerFromContext(application);
                
                String id = request.getParameter ("id");
                if (id == null) {
                  String[] serviceNames = serviceManager.list ();
                  if (serviceNames.length == 0) {
                    out.println ("<p>Sorry, there are no services currently deployed.</p>");
                  } else {
                    out.println ("<p>Select the service to be undeployed:</p>");
                    
            // end
            // HTML // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(16,6);to=(18,4)]
                out.write("\r\n    <ul>\r\n    ");
            // end
            // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(18,6);to=(21,4)]
                
                    for (int i = 0; i < serviceNames.length; i++) {
                      id = serviceNames[i];
                    
            // end
            // HTML // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(21,6);to=(22,35)]
                out.write("\r\n      <li><a href=\"undeploy.jsp?id=");
            // end
            // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(22,38);to=(22,40)]
                out.print(id);
            // end
            // HTML // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(22,42);to=(22,44)]
                out.write("\">");
            // end
            // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(22,47);to=(22,50)]
                out.print( id);
            // end
            // HTML // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(22,52);to=(23,4)]
                out.write("</li>\r\n    ");
            // end
            // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(23,6);to=(25,4)]
                
                    }
                    
            // end
            // HTML // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(25,6);to=(27,4)]
                out.write("\r\n    </ul>\r\n    ");
            // end
            // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(27,6);to=(38,0)]
                
                  }
                } else {
                  try {
                    DeploymentDescriptor dd = serviceManager.undeploy (id);
                    out.println ("OK, service named '" + id + "' undeployed successfully!");
                  } catch (SOAPException e) {
                    out.println ("Ouch, coudn't undeploy service '" + id + "' because: ");
                    e.getMessage ();
                  }
                }
            // end
            // HTML // begin [file="C:\\SOAP\\jakarta-tomcat-3.2.4\\webapps\\soap\\admin\\undeploy.jsp";from=(38,2);to=(39,0)]
                out.write("\r\n");
            // end

        } catch (Exception ex) {
            if (out != null && out.getBufferSize() != 0)
                out.clearBuffer();
            if (pageContext != null) pageContext.handlePageException(ex);
        } finally {
            if (out != null) out.flush();
            if (_jspxFactory != null) _jspxFactory.releasePageContext(pageContext);
        }
    }
}
