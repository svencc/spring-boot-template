package cc.sven.springboottemplate.repository;

import cc.sven.springboottemplate.entity.CustomMessage;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomMessageRepository extends JpaRepository<CustomMessage, Long> {

    @NonNull
    List<CustomMessage> findAllByRecipient(@NonNull String recipient);

    @NonNull
    @Query("SELECT cm FROM CustomMessage cm WHERE cm.recipient = :recipient")
    List<CustomMessage> findAllByRecipientWithQuery(@NonNull String recipient);

    @NonNull
    @Query(value =
            "SELECT * FROM custom_message AS cm WHERE cm.recipient = :recipient",
            nativeQuery = true
    )
    List<CustomMessage> findAllByRecipientWithNativeQuery(@NonNull String recipient);

}
