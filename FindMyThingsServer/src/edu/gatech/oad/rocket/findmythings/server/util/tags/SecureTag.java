package edu.gatech.oad.rocket.findmythings.server.util.tags;

import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.Map;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.SecureTag}</p>
 */
@SuppressWarnings("rawtypes")
abstract class SecureTag implements TemplateDirectiveModel {
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		verifyParameters(params);
		render(env, params, body);
	}

	abstract void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException;

	String getParam(Map params, String name) {
		Object value = params.get(name);

		if (value instanceof SimpleScalar) {
			return ((SimpleScalar)value).getAsString();
		}

		return null;
	}

	Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	void verifyParameters(Map params) throws TemplateModelException {}

	void renderBody(Environment env, TemplateDirectiveBody body) throws IOException, TemplateException {
		if (body != null) {
			body.render(env.getOut());
		}
	}
}
