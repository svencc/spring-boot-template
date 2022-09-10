package cc.sven.springboottemplate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
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
@Table(indexes = {
        @Index(name = "name", columnList = "name", unique = false),
        @Index(name = "created", columnList = "created", unique = false),
})
public class HelloMessage implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(insertable = true, updatable = false, nullable = false)
    private Long id;

    @Nationalized // bei maraidb gehts auch ohne fpr utf32. varchar tayp funktioniert auch - anders als bei mariadb-docker-local der extra nvarchar typ hat
    @Column(insertable = true, updatable = false, nullable = false, length = 32)
    private String name;

    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP) // scheint bei mariadb-docker-local auch ohne @Temporal Annotation zu gehen!
    private Date created;

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
        return HelloMessage.class.hashCode();
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
            HelloMessage other = (HelloMessage) obj;
            if (getId() == null) {
                return false;
            } else return getId().equals(other.getId());
        }
    }

}
