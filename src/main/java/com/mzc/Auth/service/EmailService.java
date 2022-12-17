package com.mzc.Auth.service;

import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@PropertySource("classpath:application.properties")
@Log4j2
@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    private final RedisUtil redisUtil;

    //인증번호 생성
    private final String ePw = createKey();

    @Value("${spring.mail.username}")
    private String id;

    public MimeMessage createMessage(String to)throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상 : "+ to);
        log.info("인증 번호 : " + ePw);
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
        message.setSubject("MQuiz 회원가입 인증 코드"); //메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
//        String msg="";
//        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
//        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
//        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
//        msg += ePw;
//        msg += "</td></tr></tbody></table></div>";
//
//        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
//        message.setFrom(new InternetAddress(id,"MQuiz_Admin")); //보내는 사람의 메일 주소, 보내는 사람 이름

        String msg = "";
        msg += "<div style='margin:100px;'>";
        msg += "<h1> 안녕하세요</h1>";
        msg += "<h1> 엠퀴즈 입니다.</h1>";
        msg += "<br>";
        msg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msg += "<br>";
        msg += "<p>엠퀴즈를 찾아주셔서 감사합니다!<p>";
        msg += "<br>";
        msg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msg += "<div style='font-size:130%'>";
        msg += "CODE : <strong>";
        msg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msg += "</div>";
        message.setText(msg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress(id, "MQuiz_Admin"));// 보내는 사람
        return message;
    }

    // 인증코드 만들기
//    public static String createKey() {
//        StringBuffer key = new StringBuffer();
//        Random rnd = new Random();
//
//        for (int i = 0; i < 6; i++) { // 인증코드 6자리
//            key.append((rnd.nextInt(10)));
//        }
//        return key.toString();
//    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    /*
        메일 발송
        sendSimpleMessage의 매개변수로 들어온 to는 인증번호를 받을 메일주소
        MimeMessage 객체 안에 내가 전송할 메일의 내용을 담아준다.
        bean으로 등록해둔 javaMailSender 객체를 사용하여 이메일 send
     */
//    public String sendSimpleMessage(String to)throws Exception {
//        MimeMessage message = createMessage(to);
//        try{
//            javaMailSender.send(message); // 메일 발송
//        }catch(MailException es){
//            es.printStackTrace();
//            throw new IllegalArgumentException();
//        }
//        return ePw; // 메일로 보냈던 인증 코드를 서버로 리턴
//    }
    public ResponseEntity sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try{
            redisUtil.setDataExpire(ePw,to,60*1L);
            javaMailSender.send(message); // 메일 발송
        }catch(MailException es){
            es.printStackTrace();
            //throw new IllegalArgumentException();
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.FAIL_EMAIL_SEND_AUTH_NUM), HttpStatus.BAD_REQUEST);// 메일로 보냈던 인증 코드를 서버로 리턴

        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.EMAIL_SEND_AUTH_NUM,ePw), HttpStatus.OK); // 메일로 보냈던 인증 코드를 서버로 리턴
    }

    public ResponseEntity verifyEmail(String key){
        String emailAuth = redisUtil.getData(key);
        if (emailAuth == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.AUTH_NUM_CHECK_INVALID_EMAIL_SEND_AUTH_NUM), HttpStatus.BAD_REQUEST); // 유효 하지 않은 이메일 인증 번호
    }
    redisUtil.deleteData(key); // 인증 완료 된 인증 번호 삭제
    return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.AUTH_NUM_CHECK_SUCCESS,emailAuth), HttpStatus.OK); // 메일로 보냈던 인증 코드를 서버로 리턴
    }
}
