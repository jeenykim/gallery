package jeeny.backend.controller;

import io.jsonwebtoken.Claims;
import jeeny.backend.entity.Cart;
import jeeny.backend.entity.Item;
import jeeny.backend.entity.Member;
import jeeny.backend.repository.CartRepository;
import jeeny.backend.repository.ItemRepository;
import jeeny.backend.repository.MemberRepository;
import jeeny.backend.service.JwtService;
import jeeny.backend.service.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//10Jwt
@RestController
//@Controller는 View를 반환
//@RestController Json 형태로 객체 데이터를 반환
//데이터를 응답으로 제공하는 REST API를 개발할 때 주로 사용하며 객체를 ResponseEntity로 감싸서 반환
//@Controller에 @ReponseBody를 붙인 것과 완벽히 동일

//@RequestBody 어노테이션을 이용하면 HTTP 요청 몸체를 자바 객체로 전달받을 수 있다.
//@ResponseBody 어노테이션을 이용하면 자바 객체를 HTTP 응답  body 내용매핑으로 전송할 수 있다

public class CartController {
    @Autowired
    ItemRepository itemRepository;

    //11에서 추가
    @Autowired
    JwtService jwtService;

    @Autowired
    CartRepository cartRepository;


//  2. 특정사용자의 장바구니목록
    @GetMapping("/api/cart/items")
    public ResponseEntity getCartItems(
            @CookieValue(value = "token", required = false) String token) {
//        @CookieValue이용하면 @Cookie객체 받을수 있다
//        Cookie값만 받으면 됨

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        //만약 token 유효하지않으면 HttpStatus.UNAUTHORIZED 401 에러 던져

        int memberId = jwtService.getId(token);//jwtService의token값을 memberId에 담고
        List<Cart> carts = cartRepository.findByMemberId(memberId);
//        cartRepository의 id값을 인자로 주면 cart의 list형태로 받음
        List<Integer> itemIds = carts.stream().map(Cart::getItemId).collect(Collectors.toList());
//  Stream 을 이용하여 람다함수형식으로 간결하고 깔끔하게 요소들의 처리가 가능
// map은 요소들을 특정조건에 해당하는 값으로 변환해 줍니다.
//Cart::getItemId 특정cart안에서서만 ItemId추출
        //cart에서 리스트형태로 도출
        List<Item> items = itemRepository.findByIdIn(itemIds);
        //itemRepository의 값 선언과 연결한 Ids값을 items에 담아줌
// 특정cart에 있는 items를 가져옴
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //1.item을 장바구니에 넣음
    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity pushCartItem(
            @PathVariable("itemId") int itemId,
// Request가 들어오는 타입중에  URL 변수 (@PathVariable)일 경우
//예: http://localhost:8080/api/user/itemId

            @CookieValue(value = "token", required = false) String token
    )
    //@CookieValue이용하면 @Cookie객체 받을수 있다
{
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
//만약 token 유효하지않으면 HttpStatus.UNAUTHORIZED 401 에러 던져

        int memberId = jwtService.getId(token);
//    jwtService에서 받은 token값을 가져오면 memberId의 값 확인
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);
//        Cart에 가져온 새로운 값으로 할당

        if (cart == null) {//만약 비어있으면
            Cart newCart = new Cart();//새로운 cart만들고
            newCart.setMemberId(memberId);//그cart에  memberId 넣어주고
            newCart.setItemId(itemId);//그cart에  itemId 넣어주고
            cartRepository.save(newCart);//새로운 cart값으로 저장
        }

        return new ResponseEntity<>(HttpStatus.OK);//새로운 목록으로 도출
    }

    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity removeCartItem(
            @PathVariable("itemId") int itemId,
// Request가 들어오는 타입중에  URL 변수 (@PathVariable)일 경우
//예: http://localhost:8080/api/user/itemId

            @CookieValue(value = "token", required = false) String token
    ){

        if (!jwtService.isValid(token)) { //토큰값이 유효하지 않으면
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        //만약 token 유효하지않으면 HttpStatus.UNAUTHORIZED 401 에러 던져
        int memberId = jwtService.getId(token);//토큰값으로 memberId 가져오고
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);
//        cartRepository에서 memberId와 itemId가져와서 cart에할당
        cartRepository.delete(cart);
        return new ResponseEntity<>(HttpStatus.OK);
//        지운후 결과값 도출
    }
}


