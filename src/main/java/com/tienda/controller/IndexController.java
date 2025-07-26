package com.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tienda.service.CorreoService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;

@Controller /*@ ANOTACIONES O DECORADORES*/
public class IndexController {
    
    @Autowired
    CorreoService correoService;
    
    @RequestMapping("/")//url al que responde
    public String index(Model model) {
        String HtmlContent = """
            <!doctype html>
            <html xmlns="http://www.w3.org/1999/xhtml"
                  xmlns:v="urn:schemas-microsoft-com:vml"
                  xmlns:o="urn:schemas-microsoft-com:office:office">
            <head>
                <title></title>
                <!--[if !mso]><!-->
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <!--<![endif]-->
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <style type="text/css">
                    #outlook a { padding: 0; }
                    body { margin: 0; padding: 0; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }
                    table, td { border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; }
                    img { border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; }
                    p { display: block; margin: 13px 0; }
                </style>
                <!--[if mso]>
                <noscript><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml></noscript>
                <![endif]-->
                <link href="https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700" rel="stylesheet" type="text/css">
                <link href="https://fonts.googleapis.com/css?family=Cabin:400,700" rel="stylesheet" type="text/css">
                <style type="text/css">
                    @media only screen and (min-width:480px) {
                        .mj-column-per-100 { width: 100%!important; max-width: 100%; }
                    }
                </style>
            </head>
            <body style="word-spacing:normal;background-color:#FFFFFF;">
                <div style="background-color:#FFFFFF;">
                    <div style="margin:0px auto;max-width:600px;">
                        <table align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="width:100%;">
                            <tr>
                                <td style="direction:ltr;font-size:0px;padding:9px 0px;text-align:center;">
                                    <div class="mj-column-per-100 mj-outlook-group-fix" style="font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;">
                                        <table border="0" cellpadding="0" cellspacing="0" role="presentation" style="vertical-align:top;" width="100%">
                                            <tr>
                                                <td align="left" style="font-size:0px;padding:15px;word-break:break-word;">
                                                    <div style="font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:13px;line-height:1.5;text-align:left;color:#000000;">
                                                        <p style="font-family: Helvetica, Arial, sans-serif; font-size: 11px; text-align: center;">
                                                            <span style="font-size: 15pt;">Esto es una prueba&nbsp;</span>
                                                        </p>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" style="font-size:0px;padding:10px;word-break:break-word;">
                                                    <p style="font-family: Helvetica, Arial, sans-serif; border-top: solid 1px #000000; font-size: 1px; margin: 0px auto; width: 100%;"></p>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left" style="font-size:0px;padding:15px;word-break:break-word;">
                                                    <div style="font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:13px;line-height:1.5;text-align:left;color:#000000;">
                                                        <p style="font-family: Helvetica, Arial, sans-serif; font-size: 11px; text-align: left;">
                                                            <span style="font-size: 14px;">
                                                                <strong>
                                                                    <span style="color: rgb(132, 63, 161);">
                                                                        Este es tu nuevo bloque de texto con el primer párrafo.
                                                                    </span>
                                                                </strong>
                                                            </span>
                                                        </p>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div style="color: #ccc; font-size: 12px; width: 600px; margin: 15px auto; text-align: center;">
                    <a href="https://wordtohtml.net/email/designer">Creado con WordToHTML.net Email Designer</a>
                </div>
            </body>
            </html>
            """;
            
        try {
            correoService.enviarCorreoHtml("adiscord2803@gmail.com", "Prueba de correo", HtmlContent);
        } catch (Exception ex) {
            System.out.println("exception");
        }
                
        return "index";//nombre de la vista en templates a mostrar
    }
    
//    @RequestMapping("/informacion")
//    public String info() {
//        return "info";
//    }
    
}
