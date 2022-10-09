package jeeny.backend.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String getToken(String key, Object value);

    Claims getClaims(String token);
    //token인자받음

    boolean isValid(String token);
    //인자로  받은 token정보문제없는지

    int getId(String token);
    //id정보 가져옴

}
