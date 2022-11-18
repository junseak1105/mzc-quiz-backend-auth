package com.mzc.auth.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/v1/auth/test")
public class TestController {

    @GetMapping("/permitAll")
    public ResponseEntity<String> permitAll(){
        return ResponseEntity.ok("ㅜ구나 접근이 가능합니다. \n");
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> authenticated(@RequestHeader String Authorization){
        log.info(Authorization);
        return ResponseEntity.ok("로그인한 사람 누구나 가능합니다.\n");
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("User 가능합니다. \n");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("admin 가능합니다. \n");
    }
}
