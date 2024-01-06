package guru.springframework.spring6restmvc.mappers;

import guru.springframework.spring6restmvc.entities.Account;
import guru.springframework.spring6restmvc.model.AccountDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AcountMapper {
    AccountDTO userToUserDTO(Account account);
    Account userDTOtoUser(AccountDTO accountDTO);
}
