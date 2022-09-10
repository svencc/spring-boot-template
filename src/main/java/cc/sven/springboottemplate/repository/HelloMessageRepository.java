package cc.sven.springboottemplate.repository;

import cc.sven.springboottemplate.entity.HelloMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloMessageRepository extends JpaRepository<HelloMessage, Long> {

}
