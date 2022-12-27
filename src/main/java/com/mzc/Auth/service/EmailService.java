package com.mzc.Auth.service;

import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.util.RedisPrefix;
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
import java.util.concurrent.TimeUnit;

@PropertySource("classpath:application.properties")
@Log4j2
@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    private final RedisUtil redisUtil;

    //인증번호 생성
    private String ePw;

    @Value("${spring.mail.username}")
    private String id;

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        ePw = createKey();
        log.info("보내는 대상 : " + to);
        log.info("인증 번호 : " + ePw);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
        message.setSubject("MQuiz 회원가입 인증 코드"); //메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능

        String msg = "";
        //msg += "<div align='center' style='background-image: url(https://img.freepik.com/free-vector/alien-planet-landscape-cosmic-background-deserted-coastline-with-mountains-view-glowing-cleft-stars-shining-spheres-space-extraterrestrial-pc-game-backdrop-cartoon-vector-illustration_107791-8012.jpg?w=826&t=st=1671701382~exp=1671701982~hmac=090b066d0594c2aecc9fccb11ac00994dd00309314e2ff267efe3a55703108f5); background-repeat: no-repeat; background-size: cover; background-position: center'>";
        msg += "<div align='center' style='background-image: url(https://img.freepik.com/free-vector/cartoon-podium-background-design-illustration_52683-70695.jpg?w=1380&t=st=1671761892~exp=1671762492~hmac=d223f7ff1c36a9fa5b37c0d48b2fd8a5c080f0ac67639e1c6e27d579a6ded1a7); background-repeat: no-repeat; background-size: cover; background-position: center'>";
        msg += "<img src=\"https://img.freepik.com/free-vector/astronaut-sitting-planet-waving-hand-cartoon-vector-icon-illustration-science-technology-icon-concept-isolated-premium-vector-flat-cartoon-style_138676-3503.jpg?w=826&t=st=1671701009~exp=1671701609~hmac=a596f3a69793e4184312988f6ff1335541e29251111d808afd40dbf972ad5fe0\" alt=\"Mquiz Icon\" style=\"width=\" height=\"240\">";
        msg += "<br>";
        msg += "<h1> 안녕하세요</h1>";
        msg += "<h1> 엠퀴즈 입니다.</h1>";
        msg += "<br>";
        msg += "<h3>아래 코드를 회원가입 창으로 돌아가 입력해주세요</h3>";
        //msg += "<br>";
        msg += "<h3>엠퀴즈를 찾아주셔서 감사합니다!</h3>";
        //msg += "<br>";
        //msg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msg += "<div align='center' style='solid black; font-family:verdana';/>";
        msg += "<h3>회원가입 인증 코드입니다.</h3>";
        msg += "<div style='font-size:150%; color:#FFD700'>";
        msg += "CODE : <strong>";
        msg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msg += "</div>";
        message.setText(msg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress(id, "MQuiz_Admin"));// 보내는 사람
        return message;
    }

    // 인증코드 만들기
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
    public ResponseEntity sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try {
            //KEY -> 이메일:인증번호 VALUE -> 인증번호
            String authNum = redisUtil.genKey(to,ePw);
            //log.info("authNum :" + authNum);
            redisUtil.setDataExpire(authNum,ePw,60*3L);
            //redisUtil.expire(authNum, 3, TimeUnit.MINUTES);

            //redisUtil.setDataExpire(ePw,to,60*1L);
            javaMailSender.send(message); // 메일 발송
        } catch (MailException es) {
            es.printStackTrace();
            //throw new IllegalArgumentException();
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.FAIL_EMAIL_SEND_AUTH_NUM), HttpStatus.OK);// 메일로 보냈던 인증 코드를 서버로 리턴
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.EMAIL_SEND_AUTH_NUM, ePw), HttpStatus.OK); // 메일로 보냈던 인증 코드를 서버로 리턴
    }

    public ResponseEntity verifyEmail(String key) {
        log.info("key : " + key);
        String emailAuth = redisUtil.getData(key);
        System.out.println("emailAuth!! :" + emailAuth);
        if (emailAuth == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.AUTH_NUM_CHECK_INVALID_EMAIL_SEND_AUTH_NUM), HttpStatus.OK); // 유효 하지 않은 이메일 인증 번호
        }
        redisUtil.deleteData(key); // 인증 완료 된 인증 번호 삭제
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.AUTH_NUM_CHECK_SUCCESS, emailAuth), HttpStatus.OK); // 메일로 보냈던 인증 코드를 서버로 리턴
    }
}
