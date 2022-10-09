package jeeny.backend.dto;

import lombok.Getter;

@Getter
public class OrderDto {
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;
}

//post로 던질때 받아오는것들 선언
//DTO(Data Transfer Object) 는 계층 간 데이터 교환을 하기 위해 사용하는 객체로, DTO는 로직을 가지지 않는 순수한 데이터 객체(getter & setter 만 가진 클래스)
//유저가 입력한 데이터를 DB에 넣는 과정
//유저가 자신의 브라우저에서 데이터를 입력하여 form에 있는 데이터를 DTO에 넣어서 전송
//해당 DTO를 받은 서버가 DAO를 이용하여 데이터베이스로 데이터를 주입
