package jeeny.backend.controller;

import jeeny.backend.dto.OrderDto;
import jeeny.backend.entity.Cart;
import jeeny.backend.entity.Item;
import jeeny.backend.entity.Order;
import jeeny.backend.repository.CartRepository;
import jeeny.backend.repository.ItemRepository;
import jeeny.backend.repository.OrderRepository;
import jeeny.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

//10Jwt
@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    CartRepository cartRepository;

    @GetMapping("/api/orders")
    public ResponseEntity getOrder(
            @CookieValue(value = "token", required = false) String token
    ) {
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);//memberId지정
        List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);
//        orderRepository의 id값할당
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Transactional
//    DB와 관련된, 트랜잭션이 필요한 서비스 클래스 혹은 메서드에 @Transactional 어노테이션을 달아주면된다.
//일련의 작업들을 묶어서 하나의 단위로 처리

    @PostMapping("/api/orders")//1.사용자는 api/orders에 request하고
    public ResponseEntity pushOrder(
            @RequestBody OrderDto dto,//2.사용자가 입력한 내용을 dto에 담아서
            @CookieValue(value = "token", required = false) String token
    ){
    //@CookieValue이용하면 @Cookie객체 받을수 있다
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }//만약 token 유효하지않으면 HttpStatus.UNAUTHORIZED 401 에러 던져

    int memberId = jwtService.getId(token);
    Order newOrder = new Order();//주문정보 변수 선언

    newOrder.setMemberId(memberId);//3.새롭게 만들어진 order정보들을 만들고
    newOrder.setName(dto.getName());
    newOrder.setAddress(dto.getAddress());
    newOrder.setPayment(dto.getPayment());
    newOrder.setCardNumber(dto.getCardNumber());
    newOrder.setItems(dto.getItems());

    orderRepository.save(newOrder);//4.orderRepository이용해서 주문내역에 도출
    cartRepository.deleteByMemberId(memberId);//특정id의 cart에서 지움

    return new ResponseEntity<>(HttpStatus.OK);//새로운 목록으로 도출
    }

}


