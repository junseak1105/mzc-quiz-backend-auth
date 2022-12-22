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

//        String msg = "<div align=\"center\" style=\"text-align: center;\"><span style=\"font-size: 24pt; font-family: arial, sans-serif;\">"
//                + "<b>회원가입을 축하드립니다.</b></span></div><div align=\"center\" style=\"text-align: center;\">"
//                + "<p align=\"center\" style=\"font-family: 돋움, Dotum, Helvetica, Apple SD Gothic Neo, sans-serif; font-size: 12px;\"><span style=\"font-size: 10pt; font-family: arial, sans-serif;\">.</span></p>"
//                + "<p align=\"center\" style=\"font-family: 돋움, Dotum, Helvetica, Apple SD Gothic Neo, sans-serif; font-size: 12px;\"><span style=\"font-size: 10pt; font-family: arial, sans-serif;\">!</span></p>"
//                + "<p align=\"center\" style=\"font-family: 돋움, Dotum, Helvetica, Apple SD Gothic Neo, sans-serif; font-size: 12px;\"><span style=\"font-size: 12pt; font-family: arial, sans-serif;\">"
//                + "코드 번호 : <span style=\"background-color: rgb(255, 255, 255); font-size: 12pt; color: rgb(0, 0, 0); font-family: arial, sans-serif;\"><u>WELCOME123</u></span></span></p>"
//                + "<p align=\"center\" style=\"font-family: 돋움, Dotum, Helvetica, Apple SD Gothic Neo, sans-serif; font-size: 12px;\">"
//                + "<span style=\"font-size: 12pt; font-family: arial, sans-serif;\"><span style=\"background-color: rgb(255, 255, 255); font-size: 12pt; color: rgb(0, 0, 0); font-family: arial, sans-serif;\"><br></span>"
//                + "</span></p></div><div align=\"center\" style=\"text-align: center;\"><img src=\"https://image.flaticon.com/icons/png/512/1169/1169905.png\" alt=\"Coupons free icon\" style=\"width=\" height=\"240\"><br></div>"
//                + "<div align=\"center\" style=\"text-align: center;\"><br></div><div align=\"center\" style=\"text-align: center;\">"
//                + "<form action=\"http://localhost:8080/myweb2/main.sh\" method=\"get\"><input type=\"submit\" value=\"쿠폰 등록하러가기\" style=\"border:1px solid #f4f4f4; padding: 7px 15px; border-radius:8px; background-color: rgb(81, 143, 187);color:white; cursor: pointer;\"></form></div>"; // 내용

//        String content = "<p align=\"center\" style=\"text-align: center; \">&nbsp;<span style=\"font-size: 10pt;\">&nbsp;</span></p><p align=\"center\" style=\"text-align: center; \">"
//                + "<img src=\"https://postfiles.pstatic.net/MjAyMTA4MDRfMTA3/MDAxNjI4MDU3ODE4MDM3.dQGrbYYH-OB9Pi7lo8CDH97BbHOhbvmWRLB66QJkbcAg.0Xx2qYIHIMjO6hGspDI-Z5M7kCNLnBL7EgK54rpZl3og.JPEG.honamory/logo_proto_1.jpg?type=w966\" loading=\"lazy\"></p>"
//                + "<p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p>"
//                + "<p align=\"center\" style=\"text-align: center; \"><span style=\"font-family: dotum, sans-serif;\">안녕하세요 회원님,</span></p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p>"
//                + "<p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \"><span style=\"font-family: dotum, sans-serif;\">회원님의 임시 비밀번호를&nbsp;</span></p><p align=\"center\" style=\"text-align: center; \">"
//                + "<span style=\"font-family: dotum, sans-serif;\">아래와 같이 전달해 드립니다.</span></p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p>"
//                + "<p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">"
//                + "<b><span style=\"font-size: 9pt; font-family: dotum, sans-serif;\">임시 비밀번호:&nbsp;</span>"
//                + "</b><span style=\"font-family: dotum, sans-serif; font-size: 9pt;\">["+tempPass+"]</span>" //임시비밀번호!!
//                + "</p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">"
//                + "&nbsp;</p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">"
//                + "<span style=\"font-family: dotum, sans-serif;\">이 비밀번호로 로그인 하셔서</span></p><p align=\"center\" style=\"text-align: center; \">"
//                + "<span style=\"font-family: dotum, sans-serif;\">새로운 비밀번호로&nbsp;</span></p>"
//                + "<p align=\"center\" style=\"text-align: center; \"><span style=\"font-family: dotum, sans-serif;\">변경하여 주시기 바랍니다.</span>"
//                + "</p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p>"
//                + "<p align=\"center\" style=\"text-align: center; \"><span style=\"font-family: dotum, sans-serif;\">"
//                + "<b><a href=\"http://localhost:8080/myweb2/login.sh\" target=\"_blank\" style=\"cursor: pointer; white-space: pre;\" rel=\"noreferrer noopener\">"
//                + "로그인 하러 가기</a><span></span></b></span></p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p>"
//                + "<p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">"
//                + "<span style=\"font-family: dotum, sans-serif;\">※비밀번호 찾기 신청을&nbsp;</span><span style=\"font-size: 10pt; font-family: dotum, sans-serif;\">하시지 않았다면</span></p>"
//                + "<p align=\"center\" style=\"text-align: center; \"><span style=\"font-family: dotum, sans-serif;\"><span></span><a href=\"http://localhost:8080/myweb2/notice.sh\" target=\"_blank\" style=\"cursor: pointer; white-space: pre;\" rel=\"noreferrer noopener\">"
//                + "고객센터</a><span></span>로 연락해 주세요.</span></p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">&nbsp;</p><p align=\"center\" style=\"text-align: center; \">"
//                + "<span style=\"font-size: 10pt;\">&nbsp;</span>&nbsp;</p>\r\n"
//                + "\r\n"
//                + "";

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
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.FAIL_EMAIL_SEND_AUTH_NUM), HttpStatus.OK);// 메일로 보냈던 인증 코드를 서버로 리턴

        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.EMAIL_SEND_AUTH_NUM,ePw), HttpStatus.OK); // 메일로 보냈던 인증 코드를 서버로 리턴
    }

    public ResponseEntity verifyEmail(String key){
        String emailAuth = redisUtil.getData(key);
        if (emailAuth == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.AUTH_NUM_CHECK_INVALID_EMAIL_SEND_AUTH_NUM), HttpStatus.OK); // 유효 하지 않은 이메일 인증 번호
    }
    redisUtil.deleteData(key); // 인증 완료 된 인증 번호 삭제
    return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.AUTH_NUM_CHECK_SUCCESS,emailAuth), HttpStatus.OK); // 메일로 보냈던 인증 코드를 서버로 리턴
    }
}
