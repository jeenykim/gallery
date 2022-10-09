package jeeny.backend.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.Signature;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//10jwt로그인
//https://jwt.io/ hs256
@Service("jwtService")
public class JwtServiceImpl implements JwtService {

    private String secretKey = "abbci2ioadij@@@ai17a662###8139!!!18ausudahd178316738687687@@ad6g";

    //보안키생성
    @Override
    public String getToken(String key, Object value) {

        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30);
        //로그인시간30분지나면 초기화 시간조정가능
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }
//secretKey생성
//11//////////////////////////////////////////////////////

    //12체크보완////////////////////////////////////////////////////
    //Token 유효성 확인
    @Override
    public Claims getClaims(String token) {
        if (token != null && !"".equals(token)) {
            try {
                byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
                Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
                return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e) {
                // 만료됨
            } catch (JwtException e) {
                // 유효하지 않음
            }
        }

        return null;
    }
    //문제가 없는 경우 true가 반환된다.
//만료 / 변조된 경우 false가 반환된다.

    ////////////////12.cart 장바구니추가/////////////////////

    @Override
    public boolean isValid(String token) {
        return this.getClaims(token) != null;
    }
    //.getClaims(token)값이 null아니면 문제없음
    @Override
    public int getId(String token) {
        Claims claims = this.getClaims(token);

        if (claims != null) {
            return Integer.parseInt(claims.get("id").toString());
        }
       // claims 값이 null아니면
        //id값을 String화하고
//.getClaims(id)값을 parseInt(문자열을 정수로 바꾸는 함수)로 도출
        return 0;
    }
}
