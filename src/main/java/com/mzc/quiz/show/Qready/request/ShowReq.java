package com.mzc.quiz.show.Qready.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowReq {

    @ApiModelProperty(name="몽고DB pk ID", example = "Email로 Show List 조회시 Show meta정보와 ID를 같이 넘겨줍니다.")
    String _id;

    @ApiModelProperty(name = "유저의 이메일", example = "유저 이멩일 입니다.")
    String email;

}
