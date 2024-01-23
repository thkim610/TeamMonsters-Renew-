package hello.monsters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // 트랜잭션 어노테이션이 테스트케이스에 있으면 테스트가 끝난 후, 바로 롤백이 되어 db에 저장되지 않는다.
    @Rollback(false) //롤백 기능 비활성화
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");
        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId()); //만든 객체 ID와 DB에 저장된 ID가 같은지 검증
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // ==비교 진행 => (같은 영속성 컨텍스트 안에서는 id값이 같으면 같은 엔티티로 인식한다.)
        //JPA 엔티티 동일성 보장
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member:  " + (findMember==member));
    }

}