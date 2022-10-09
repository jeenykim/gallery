package jeeny.backend.repository;

import jeeny.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer>{

        List<Cart> findByMemberId(int memberId);
        //findByMemberId에 memberId값을 인자로 받으면 member에 있는 카트정보를 List형태로 가져옴

        Cart findByMemberIdAndItemId(int memberId, int itemId);
        //findByMemberIdAndItemId에 사용자memberId, itemId의 카트정보를 가져옴

        void deleteByMemberId(int memberId);
//        결과값 도출없는 return페이지가 없는 void함수
}
