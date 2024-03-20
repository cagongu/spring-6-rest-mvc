package guru.springframework.spring6restmvc.mappers;

import guru.springframework.spring6restmvc.entities.Account;
import guru.springframework.spring6restmvc.model.AccountDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-20T16:11:52+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class AcountMapperImpl implements AcountMapper {

    @Override
    public AccountDTO userToUserDTO(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO.AccountDTOBuilder accountDTO = AccountDTO.builder();

        accountDTO.id( account.getId() );
        accountDTO.name( account.getName() );
        accountDTO.years( account.getYears() );

        return accountDTO.build();
    }

    @Override
    public Account userDTOtoUser(AccountDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        Account.AccountBuilder account = Account.builder();

        account.id( accountDTO.getId() );
        account.name( accountDTO.getName() );
        account.years( accountDTO.getYears() );

        return account.build();
    }
}
