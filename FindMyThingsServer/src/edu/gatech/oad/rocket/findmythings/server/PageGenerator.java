package edu.gatech.oad.rocket.findmythings.server;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import edu.gatech.oad.rocket.findmythings.server.util.tags.PageAuthTags;
import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class PageGenerator {

	public static final String TEMPLATES = "FMTTemplateDirectory";
	public static final String LOCALE = "FMTTemplateLocale";
	public static final String CHARSET = "FMTTemplateCharset";

	private static final Logger LOGGER = Logger.getLogger(PageGenerator.class.getName());
	private static final String EMPTY_STRING = "";

	/**
	 * The root location where the templates are stored, either a local file,
	 * or a directory served from a remote web server.
	 */
	private final URL templateBase;
	private final Locale locale;
	private final Charset charset;
	private Configuration config;

	PageGenerator(URL templateBase, Locale locale, Charset charset) throws IOException {
		Preconditions.checkNotNull(templateBase);
		Preconditions.checkNotNull(locale);
		Preconditions.checkNotNull(charset);

		this.templateBase = templateBase;
		this.locale = locale;
		this.charset = charset;
	}

	Template getTemplate(String templateName) throws IOException {
		String[] names = getFileNamesForSearch(locale, templateName);
		IOException lastException = null;
		for (String nm : names) {
			try {
				Template template = getConfig().getTemplate(nm);
				template.setOutputEncoding(charset.name());
				return template;
			} catch (IOException e) {
				lastException = e;
			}
		}
		if (lastException != null) {
			LOGGER.fine("problem getting template \"" + templateName + "\" for " + locale + " in dir " + templateBase);
					throw lastException;
		}
		return null;
	}

	public String createPage(String templateName, Map<String, ?> args) throws IOException {
		Template template = getTemplate(templateName);
		if (template == null) {
			return EMPTY_STRING;
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try (Writer outLocal = new OutputStreamWriter(os, charset)) {
			template.process(args, outLocal);
			outLocal.close();
			return new String(os.toByteArray(), charset);
		} catch (TemplateException e) {
			LOGGER.severe("Error processing template " + e.getMessage());
			return EMPTY_STRING;
		}
	}

	public String createPage(String templateName, Object... args) throws IOException {
		return createPage(templateName, map(args));
	}

	public void writePage(String templateName, Writer out, Map<String, ?> args) throws IOException {
		String append = createPage(templateName, args);
		if (append.length() != 0) {
			out.write(append);
		}
	}

	public void writePage(String templateName, Writer out, Object... args) throws IOException {
		writePage(templateName, out, map(args));
	}

	// helper for variable argument (Object...) methods
	public static Map<String, Object> map(Object[] list) {
		Preconditions.checkNotNull(list);
		Preconditions.checkArgument(list.length % 2 == 0, "Your list has to have an even length, not " + list.length);

		Map<String,Object> out = Maps.newHashMap();
		for (int i = 0; i < list.length; i += 2) {
			out.put((String)list[i], list[i+1]);
		}
		return out;
	}

	private static String[] getFileNamesForSearch(Locale locale, String templateName) {
		String language = locale.getLanguage().toLowerCase();
		String country = locale.getCountry().toLowerCase();
		return new String[]{
				language + "_" + country + "_" + templateName,
				language + "_" + templateName,
				templateName
		};
	}

	synchronized Configuration getConfig() {
		if (config == null) {
			config = new Configuration();
			config.setObjectWrapper(new DefaultObjectWrapper());
			try {
				config.setSetting(Configuration.CACHE_STORAGE_KEY, "strong:20, soft:250");
			} catch (TemplateException e) {
				LOGGER.warning("Can't set freemarker cache (not fatal) " + e.getMessage());
			}

			String charsetName = charset.name();
			config.setDefaultEncoding(charsetName);
			config.setEncoding(locale, charsetName);
			config.setLocale(locale);
			config.setSharedVariable("shiro", new PageAuthTags());

			config.setTemplateLoader(new URLTemplateLoader(){
				@Override
				public URL getURL(String templateName) {
					Preconditions.checkNotNull(templateName);
					try {
						URL url =  new URL(templateBase, templateName);
						URLConnection connect = url.openConnection();
						try {
							connect.connect();
							return url;
						} catch (IOException e) {
							return null;
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}

			});
		}
		return config;
	}

}
