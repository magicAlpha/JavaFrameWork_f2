package com.magic.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmailUtil {
	
	public static void SendEmailNow(String hostName, String portID, String senderEmailID, String senderPassword, String [] receiverEmailArray, String [] ccEmailArray,long pageLoadTime_Minutes, String reportPath) {
		
		// SMTP info
		String host = hostName;
        String port = portID;

        String mailFrom = senderEmailID;
        String password = senderPassword;
        
        
        // message info
        
        String subject = "QA AUTOMATED TESTING STATUS";
        StringBuffer body = new StringBuffer("Hi All, <br> <br>  <b> NOTE: THIS IS AN AUTOMATED EMAIL</b><br> <br>Please find attached file <b>MAGIC_QA_AUTOMATION_REPORT.html</b> for the complete status of Automation testing. You need to Download the file to view it.<br> <br>");
        body.append("Screenshots of failed test cases are attached in the email(if any)");
        body.append("<br><br> Thanks<br>Magic Automation Team");
        body.append("</html>");
 
        // inline images
        Map<String, String> inlineImages = new HashMap<String, String>();
        inlineImages.put("report", reportPath);
        
        // Browser console file
        //inlineImages.put("Browser console file",browserConsloe);
        
        ArrayList listOfFiles = SeleniumUtil.getfileNamesFromFolder( System.getProperty("user.dir")+"\\FailedTestsScreenshots");
		System.out.println(listOfFiles);
		
		
		
		for(int i=0;i<listOfFiles.size();i++) {
			
			if(!String.valueOf(listOfFiles.get(i)).equals("extentScreenshot.png")) {
				String screenShotPath = System.getProperty("user.dir")+"/FailedTestsScreenshots/"+listOfFiles.get(i);
				
				inlineImages.put("FailedScreenshot"+i, screenShotPath);
			}
			
			
		}
 
        try {
            send(host, port, mailFrom, password, receiverEmailArray,ccEmailArray,subject, body.toString(), inlineImages);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
	
	
	public static void send(String host, String port,final String userName, final String password, String[] toAddressArray,String[] ccEmailArray,
            String subject, String htmlBody,
            Map<String, String> mapInlineImages)
                throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        for(int i=0;i<toAddressArray.length;i++) {
	    	String toAddress = toAddressArray[i];
	    	System.out.println("toAddress: "+toAddress);
	    	InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
	    	msg.addRecipients(Message.RecipientType.TO, toAddresses);
	    }
        for(int i=0;i<ccEmailArray.length;i++) {
	    	String ccAddress = ccEmailArray[i];
	    	System.out.println("ccAddress: "+ccAddress);
	    	InternetAddress[] ccAddresses = { new InternetAddress(ccAddress) };
	    	msg.addRecipients(Message.RecipientType.CC, ccAddresses);
	    }
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlBody, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds inline image attachments
        if (mapInlineImages != null && mapInlineImages.size() > 0) {
            Set<String> setImageID = mapInlineImages.keySet();
             
            for (String contentId : setImageID) {
            	
            	System.out.println(contentId);
            	
                MimeBodyPart imagePart = new MimeBodyPart();
                
                if(contentId.contains("image")) {
                	imagePart.setHeader("Content-ID", "<" + contentId + ">");
                    imagePart.setDisposition(MimeBodyPart.INLINE);
                }
               
                 
                String imageFilePath = mapInlineImages.get(contentId);
                try {
                    imagePart.attachFile(imageFilePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(imagePart);
            }
        }
 
        msg.setContent(multipart);
 
        Transport.send(msg);
    }

}
