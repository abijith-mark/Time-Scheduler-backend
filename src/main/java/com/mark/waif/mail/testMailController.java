package com.mark.waif.mail;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mark.waif.mail.MailService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v1/mail")
public class testMailController {

	@Autowired
	private MailService mailService;
	
	@PostMapping("/sendmail")
	public void sendMail() throws UnsupportedEncodingException, MessagingException  {
//		mailService.sendEmail(null);
	}
	
}
