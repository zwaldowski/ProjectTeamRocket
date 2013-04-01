package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

import edu.gatech.oad.rocket.findmythings.server.util.HTTP;
import edu.gatech.oad.rocket.findmythings.server.util.MimeTypes;
import edu.gatech.oad.rocket.findmythings.server.util.Parameters;
import edu.gatech.oad.rocket.findmythings.server.web.PageGenerator;

@Singleton
public class TemplateServlet extends BaseServlet {

	private static final long serialVersionUID = 8526927539799303725L;
	
    static final Logger LOGGER = Logger.getLogger(TemplateServlet.class.getName());
    
    protected static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        }
        int extensionPos = filename.lastIndexOf(".");
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        return (Math.max(lastUnixPos, lastWindowsPos) > extensionPos ? -1 : extensionPos);
    }

    protected static String removeExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = indexOfExtension(filename);
        if (index == -1) {
            return filename;
        } else {
            return filename.substring(0, index);
        }
    }
    
    protected static String replaceExtensionWith(String filename, String extension) {
	return removeExtension(filename) + ".ftl";
    }

	public TemplateServlet() {
		// TODO Auto-generated constructor stub
	}

    protected void writeDocument(HttpServletResponse response, String templateName, Object... args) throws IOException {
    	writeDocument(response, templateName, PageGenerator.map(args));
    }
    
    protected void writeDocument(HttpServletResponse response, String templateName, Map<String,Object> args) throws IOException {
    	String html = createDocument(templateName, args);
    	write(MimeTypes.HTML, HTTP.STATUS_OK, html, response);
    }
    
    protected String createDocument(String templateName, Object... args) throws IOException {
    	return createDocument(templateName, PageGenerator.map(args));
    }
    
    protected String createDocument(String templateName, Map<String,Object> userArgs) throws IOException {
    	return generator.createPage(templateName, userArgs);
    }

    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uri = replaceExtensionWith(request.getRequestURI(), "ftl");
		writeDocument(response, uri, getParameterMap(request));
	}

    // you really should override this in the subclass! :)
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// we only get here if the login has failed.
		Map<String, Object> extraInfo = new HashMap<>();
		Object failureReason = request.getAttribute(Parameters.FAILURE_REASON);
		if (failureReason != null) {
			extraInfo.put(Parameters.FAILURE_REASON, failureReason);
		}
		writeDocument(response, "login.ftl", extraInfo);
	}

}
