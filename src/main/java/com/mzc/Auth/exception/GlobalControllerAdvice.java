//package com.mzc.Auth.exception;
//
//import com.mzc.Auth.response.Response;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import static com.mzc.Auth.exception.ErrorCode.DATABASE_ERROR;
//
//@RestControllerAdvice
//@Log4j2
//public class GlobalControllerAdvice {
//
//    @ExceptionHandler(ApplicationException.class)
//    public ResponseEntity<?> applicationHandler(ApplicationException e) {
//        log.error("Error occurs {}", e.toString());
//        return ResponseEntity.status(e.getErrorCode().getStatus())
//                .body(Response.error(e.getErrorCode().name()));
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<?> applicationHandler(RuntimeException e){
//        log.error("Error occurs {}", e.toString());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(Response.error(ErrorCode.INTERNAL_SERVER_ERROR.name()));
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<?> databaseErrorHandler(IllegalArgumentException e) {
//        log.error("Error occurs {}", e.toString());
//        return ResponseEntity.status(DATABASE_ERROR.getStatus())
//                .body(Response.error(DATABASE_ERROR.name()));
//    }
//}
