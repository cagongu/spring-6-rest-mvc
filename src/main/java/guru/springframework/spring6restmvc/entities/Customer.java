package guru.springframework.spring6restmvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private String name;
    private UUID id;
    @Version
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
