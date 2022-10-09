package jeeny.backend.repository;

import jeeny.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByEmailAndPassword(String email, String password);
}

//MemberRepository라는 인터페이스에 JpaRepository를 상속
//Member는 Entity 클래스 명이고 Integer는 해당값을 객체화한다.

//Reposity
//Entity에 의해 생성된 DB에 접근하는 메서드 들을 사용하기 위한 인터페이스
//@Entity라는 어노테이션으로 데이터베이스 구조를 만들었다면 여기에 CRUD를 해야한다이것을 어떻게 할 것인지 정의해주는 계층이라고 생각하면 된다

//jpa는 인터페이스이다. jpa는 미리 검색 메소드를 정의 해 두는 것으로, 메소드를 호출하는 것만으로 스마트한 데이터 검색을 할 수 있게 되는 것이다.
// Etity에 있는 데이터를 조회하거나 저장과 변경 그리고 삭제를 할때 Spring JPA에서 제공하는 Repository라는 인터페이스를 정의해 해당 Entity의 데이터를 사용 할 수 있다.
//Repository는 내부적으로 EntityManager가 직접 대상 Entity의 데이터를 관리하고 있기 때문에 굳이 Repository 인터페이스를 정의하지 않고도 직접 EntityManger를 사용해 Persistance Layer를 구현 할 수 있지만 Spring JPA에서 Repository의 내부 구현체를 자동으로 생성시켜 주기 때문에 별도의 구현체를 따로 생성하지 않아도 된다.

//int(변수)기본형을 표현해야 하는 경우
//1매개변수로 객체를 필요로 할 때
//2기본형 값이 아닌 객체로 저장해야할 때
//3객체 간 비교가 필요할 때

//Integer는 int의 레퍼클레스 라고 할 수 있다.
//기본형을 객체로 다루기 위해 사용하는 클래스들을 래퍼 클래스(wrapper class)

