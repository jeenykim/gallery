package jeeny.backend.repository;

import jeeny.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>{
    List<Order> findByMemberIdOrderByIdDesc(int memberId);
    //인자로 int memberId넣어줌
}

