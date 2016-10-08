package com.kibou.mail;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.kibou.Application;

import freemarker.template.Template;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {
	
	@Autowired
	private JavaMailSender mailSender;
	
	private static String MAIL_FROM  = "aimysaber@sina.com";
	private static String MAIL_TO  = "673504787@qq.com";
	
	@Test
	public void testSendSimpleMail() throws Exception {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(MAIL_FROM);
		message.setTo(MAIL_TO);
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");
		
		mailSender.send(message);
	}
	
	@Test //发送附件
	public void testSendAttachmentsMail() throws Exception {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		
		helper.setFrom(MAIL_FROM);
		helper.setTo(MAIL_TO);
		helper.setSubject("主题：有附件");
		helper.setText("有附件的邮件");
		
		FileSystemResource file = new FileSystemResource(new File("E:/picture.png"));
		
		helper.addAttachment("附件-1.jpg", file);
		helper.addAttachment("附件-2.jpg", file);
		
		mailSender.send(mimeMessage);
	}
	
	@Test //嵌入静态资源
	public void testSendInlineMail() throws Exception {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		
		helper.setFrom(MAIL_FROM);
		helper.setTo(MAIL_TO);
		
		helper.setSubject("主题：嵌入静态资源");
		helper.setText("<html><body><img src=\"cid:pic1\" ></body></html>", true);
		
		FileSystemResource file = new FileSystemResource(new File("E:/picture.png"));

		helper.addInline("pic1", file);
		
		mailSender.send(mimeMessage);
	}
	
	
	/*
	 * As of Spring Framework 4.3, Velocity support has been deprecated due to
	 * six years without active maintenance of the Apache Velocity project. We
	 * recommend Spring’s FreeMarker support instead, or Thymeleaf which comes
	 * with Spring support itself.
	 */
	/*@Autowired
	private VelocityEngine velocityEngine;
	
	@Test
	public void testSendVelocityTemplateMail() throws Exception {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		
		helper.setFrom(MAIL_FROM);
		helper.setTo(MAIL_TO);
		
		helper.setSubject("主题：模板邮件");
		
		Map<String, Object> model = new HashMap<>();
		model.put("username", "shijun");
		
		//deprecated spring 4.3建议使用freemarker
		String text = VelocityEngineUtils.mergeTemplateIntoString
				(velocityEngine, "template.vm", "UTF-8", model);
		
		helper.setText(text, true);
		mailSender.send(mimeMessage);
		//System.out.println(text);

	}*/
	
	/**
	 *  Configuration create by FactoryBean {@linkplain FreeMarkerConfigurationFactory}
	 * @see FreeMarkerAutoConfiguration 
	 * @see FreeMarkerNonWebConfiguration
	 * @see FreeMarkerConfigurationFactory
	 */
	@Autowired
	freemarker.template.Configuration configuration;
	
	@Test
	public void testSendFreeMarkerTemplateMail() throws Exception {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		
		helper.setFrom(MAIL_FROM);
		helper.setTo(MAIL_TO);
		
		helper.setSubject("主题：模板邮件");
		
		Map<String, Object> model = new HashMap<>();
		model.put("username", "shijun");
		
		Template template = configuration.getTemplate("template.ftl", "utf-8");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		
		helper.setText(text, true);
		mailSender.send(mimeMessage);
//		System.out.println(text);
	}
}