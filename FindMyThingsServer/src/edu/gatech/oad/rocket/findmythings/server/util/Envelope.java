package edu.gatech.oad.rocket.findmythings.server.util;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class Envelope {
	static final Logger LOG = Logger.getLogger(Envelope.class.getName());
	
	public static final String SENDER = "FMTEmailFrom";
	
	private final String fromAddress;

    @Inject
    public Envelope(@Named(SENDER) String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void send(String toAddress, String subject, String htmlMessage) {
        LOG.info("sending message to " + toAddress);
        MailService service = MailServiceFactory.getMailService();
        MailService.Message message = new MailService.Message();
        message.setSender(fromAddress);
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setHtmlBody(htmlMessage);
        try {
            service.send(message);
            LOG.info("message has been sent to " + toAddress);
        } catch (IOException e) {
            LOG.warning("Can't send email to " + toAddress + " about " + subject + ": " + e.getMessage());
        }
    }

}
