package jeeny.backend.repository;

import jeeny.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

//extends:상속의 대표적인 형태이다.
//모든 선언/정의를 부모가 하며, 자식은 오버라이딩 할 필요 없이 부모의 메소드/변수를 그대로 사용할 수 있다.
//부모 클래스가 두 개 이상 존재할 수 없다(다중상속을 지원하지 않는다.)는 것이다.
//클래스 한 개만 상속 받을 수 있으며, 자식 클래스는 부모 클래스의 기능을 사용할 수 있다.

//implements
//부모는 선언만 하며, 반드시 자식이 정의를 오버라이딩해서 사용한다.
//여러 개의 interfaces를 상속 받을 수 있으며, 자식 클래스는 부모의 기능을 다시 정의해서 사용해야한다.

    List<Item> findByIdIn(List<Integer> ids);
    //ids:item목록도출인 CartController와 연결
}
