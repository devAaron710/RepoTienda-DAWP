package com.tienda.service.impl;

import com.tienda.service.CorreoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender; //DEPENDENCIA NUEVA 
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CorreoServiceImpl implements CorreoService {

    @Autowired
    private JavaMailSender mailSender;

    //METODO GENERICO / SIRVE PARA EL PROYECTO
    @Override
      public void enviarCorreoHtml(String para,String asunto,String contenidoHtml)throws MessagingException {
          
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(para);
        helper.setSubject(asunto);
        helper.setText(contenidoHtml    ,true);
        mailSender.send(message);
    }
}
