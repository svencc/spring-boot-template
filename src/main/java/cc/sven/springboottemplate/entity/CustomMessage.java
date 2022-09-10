package cc.sven.springboottemplate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        indexes = {
                @Index(name = "recipient", columnList = "recipient", unique = false),
                @Index(name = "created", columnList = "created", unique = false),
        })
public class CustomMessage implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(insertable = true, updatable = false, nullable = false)
    private Long id;

    @Column(insertable = true, updatable = false, nullable = false, length = 32)
    private String recipient;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false, length = 65)
    private String messageText;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return getId() != null;
    }

    @Override
    public int hashCode() {
        return CustomMessage.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            CustomMessage other = (CustomMessage) obj;
            if (getId() == null) {
                return false;
            } else return getId().equals(other.getId());
        }
    }

}
