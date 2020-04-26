//package kovteba.onlineshopapi.util;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.stereotype.Component;
//
//import java.util.Properties;
//
//@Configuration
//public class EmailSender {
//
//    @Value("${spring.mail.host}")
//    private String host;
//
//    @Value("${spring.mail.port}")
//    private String port;
//
//    @Value("${spring.mail.username}")
//    private String userName;
//
//    @Value("${spring.mail.password}")
//    private String password;
//
//    @Value("${spring.mail.protocol}")
//    private String protocol;
//
//    @Value("${spring.mail.properties.mail.smtp.auth}")
//    private String auth;
//
//    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
//    private String starttlsEnable;
//
//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
////        mailSender.setHost("smtp.gmail.com");
////        mailSender.setPort(587);
////        mailSender.setUsername("kovteba@gmail.com");
////        mailSender.setPassword("qfgnvbaghwjfyzhq");
////
////        Properties props = mailSender.getJavaMailProperties();
////        props.put("mail.transport.protocol", "smtp");
////        props.put("mail.smtp.auth", "true");
////        props.put("mail.smtp.starttls.enable", "true");
////        props.put("mail.debug", "true");
//
//        mailSender.setHost(host);
//        mailSender.setPort(Integer.parseInt(port));
//        mailSender.setUsername(userName);
//        mailSender.setPassword(password);
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", protocol);
//        props.put("mail.smtp.auth", auth);
//        props.put("mail.smtp.starttls.enable", starttlsEnable);
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
//
//}
