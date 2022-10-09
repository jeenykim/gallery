package jeeny.backend.controller;

import io.jsonwebtoken.Claims;
import jeeny.backend.entity.Member;
import jeeny.backend.repository.MemberRepository;
import jeeny.backend.service.JwtService;
import jeeny.backend.service.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//10Jwt
@RestController
//@Controller는 View를 반환
//@RestController Json 형태로 객체 데이터를 반환
//데이터를 응답으로 제공하는 REST API를 개발할 때 주로 사용하며 객체를 ResponseEntity로 감싸서 반환
//@Controller에 @ReponseBody를 붙인 것과 완벽히 동일

//@RequestBody 어노테이션을 이용하면 HTTP 요청 몸체를 자바 객체로 전달받을 수 있다.
//@ResponseBody 어노테이션을 이용하면 자바 객체를 HTTP 응답  body 내용매핑으로 전송할 수 있다

public class AccountController {
    @Autowired
    MemberRepository memberRepository;

    //11에서 추가
    @Autowired
    JwtService jwtService;

    @PostMapping("/api/account/login")

    public ResponseEntity login(
            @RequestBody Map<String, String> params,
            HttpServletResponse res) {
        Member member =
                memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
        if (member != null) {
            JwtService jwtService = new JwtServiceImpl();
            int id = member.getId();
            String token = jwtService.getToken("id", id);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);//자바스크립트접근금지
            cookie.setPath("/");

            res.addCookie(cookie);//상단res추가후 입력
            //res값에cookie값추가

            return new ResponseEntity<>(id, HttpStatus.OK);
        }

//        return 0;

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        ResponseStatusException 클래스를 사용하여 REST API의 에러를 쉽게 처리
    }
//10Jwt
    //이주소로 post로 데이터 요청받음
//메서드정의
//인자를 임의정의한 params로 받고
//이메일과 패스워드를 findByEmailAndPassword메서드에 인자값대체할당
//결과값을 member로 받음
//member값이 null이 아닐때
//결과값의 id값을 리턴해줌

 //logout구현
    @PostMapping("/api/account/logout")
    public ResponseEntity logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        res.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //11보완

    @GetMapping("/api/account/check")
    public ResponseEntity check(
            @CookieValue(value = "token", required = false) String token) {
        Claims claims = jwtService.getClaims(token);

        if (claims != null) {
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
//11//
    //check로 쿠키값받음 쿠키이름 설정한 token 필수는 아니야
//jwtService통해 token값 가져오고 Claims에 저장
//만약 Claims에 저장값이 null이 아니면
//id값 가져오고 toString으로 컨버팅하고
//새로운 페이지도출해
//아니면  null
}


