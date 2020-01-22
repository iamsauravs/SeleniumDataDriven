package com.automation.core.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.automation.core.constants.*;
import com.automation.core.constants.Constants;
import com.automation.core.utilities.Email;
import com.automation.core.utilities.FolderZiper;
import com.automation.core.utilities.ReadPropertiesFile;
import com.automation.core.utilities.SendMail;

public class SendMail {

	static Logger log = Logger.getLogger(SendMail.class);

	public static void main(String args[]) throws Exception {
		FolderZiper.zipFolder(Constants.REPORT_SOURCE_PATH, Constants.REPORT_ZIP_PATH);
		SendMail.execute(Constants.REPORT_ZIP_FILENAME);
	}

	// reportFileName = TestExecutionResultFileName
	public static void execute(String reportFileName) throws Exception

	{
		FolderZiper.zipFolder(Constants.REPORT_SOURCE_PATH, Constants.REPORT_ZIP_PATH);
		String path = Constants.REPORT_ZIP_PATH;
		String[] to = parselist( ReadPropertiesFile.getProperty(Constants.EMAIL_TO_LIST) );
		String[] cc = parselist( ReadPropertiesFile.getProperty(Constants.EMAIL_CC_LIST) );
		String[] bcc = parselist( ReadPropertiesFile.getProperty(Constants.EMAIL_BCC_LIST) );;

		Email email = new Email();
		email.setTo(to);
		email.setCc(cc);
		email.setBcc(bcc);
		email.setUserName(ReadPropertiesFile.getProperty(Constants.USER_NAME));
		email.setPassword(ReadPropertiesFile.getProperty(Constants.PASSWORD));
		email.setHost(Constants.SMTP_GMAIL_COM);
		email.setPort(Constants.PORT);
		email.setStarttls("true");
		email.setAuth("true");
		email.setDebug(false);
		email.setFallBack("false");
		email.setSubject(Constants.EMAIL_SUBJECT);
		email.setText("Test Email Text");		//Later in code this text replaced by test result report.
		email.setAttachmentPath(path);
		email.setAttachmentName(reportFileName);

		log.info("Before sending");
		if (SendMail.sendMail(email)) {
			log.info("Email successfully sent to: " + email.getTo());
		} else {
			log.info("Email sending fail to: " + email.getTo());
		}
		log.info("After sending");
	}

	public static boolean sendMail(Email email) {

		// Object Instantiation of a properties file.
		Properties props = new Properties();
		props.put("mail.smtp.user", email.getUserName());
		props.put("mail.smtp.host", email.getHost());

		if (email.getPort() != null && !email.getPort().isEmpty()) {
			props.put("mail.smtp.port", email.getPort());
		}

		if (email.getStarttls() != null && !email.getStarttls().isEmpty()) {
			props.put("mail.smtp.starttls.enable", email.getStarttls());
		}

		if (email.getAuth() != null && !email.getAuth().isEmpty()) {
			props.put("mail.smtp.auth", email.getAuth());
		}

		props.put("mail.smtp.debug", "false");
		if (email.isDebug()) {
			props.put("mail.smtp.debug", "true");
		}

		if (email.getPort() != null && !email.getPort().isEmpty()) {
			props.put("mail.smtp.socketFactory.port", email.getPort());
		}

		if (email.getSocketFactoryClass() != null && !email.getSocketFactoryClass().isEmpty()) {
			props.put("mail.smtp.socketFactory.class", email.getSocketFactoryClass());
		}

		if (email.getFallBack() != null && !email.getFallBack().isEmpty()) {
			props.put("mail.smtp.socketFactory.fallback", email.getFallBack());
		}

		try {
			Session session = Session.getDefaultInstance(props, null);

			session.setDebug(email.isDebug());

			MimeMessage message = new MimeMessage(session);

			message.setText(email.getText(),  "UTF-8", "html");

			message.setSubject(email.getSubject());

			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(email.getAttachmentPath());
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(email.getAttachmentName());
			
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(readFile(Constants.EMAIL_BODY_OVERVIEW_HTML_PAGE), "text/html");
			multipart.addBodyPart(htmlPart);
		     
		//	messageBodyPart.setText("Hello");
			multipart.addBodyPart(messageBodyPart);
			
			message.setContent(multipart);
			message.setFrom(new InternetAddress(email.getUserName()));

			for (String emailTo : email.getTo()) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			}

			for (String ccTo : email.getCc()) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccTo));
			}

			for (String bccTo : email.getBcc()) {
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccTo));
			}

			message.saveChanges();

			Transport transport = session.getTransport("smtp");
			transport.connect(email.getHost(), email.getUserName(), email.getPassword());

			transport.sendMessage(message, message.getAllRecipients());

			transport.close();

		} catch (AddressException e) {
			// log.error("Wrongly formatted address is encountered");
			e.printStackTrace();
			return false;
		} catch (NoSuchProviderException e) {
			// log.error("Session attempts to instantiate a Provider that doesn't exist");
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			// log.error("Exception in sending email");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// log.error("Error occured while sending email");
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	private static String readFile(String path) throws IOException {
		FileInputStream stream = new FileInputStream(new File(path));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}

	private static String[] parselist(String list) {
		String[] strArray = {};
		if(!list.trim().isEmpty()) {
			return  list.split(",");
		} 
		return strArray;
	}
}
