package guru.springframework.spring6restmvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private int years;
}
