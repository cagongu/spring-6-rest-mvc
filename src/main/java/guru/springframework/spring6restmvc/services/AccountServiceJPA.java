package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.mappers.AcountMapper;
import guru.springframework.spring6restmvc.model.AccountDTO;
import guru.springframework.spring6restmvc.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class AccountServiceJPA implements AccountService {
    private final UserRepository userRepository;
    private final AcountMapper acountMapper;

    @Override
    public Optional<AccountDTO> getUserById(UUID uuid) {
        return Optional.ofNullable(acountMapper.userToUserDTO(userRepository.findById(uuid).orElse(null)));
    }

    @Override
    public List<AccountDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(acountMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO saveNewCustomer(AccountDTO accountDTO) {
        return acountMapper.userToUserDTO(userRepository.save(acountMapper.userDTOtoUser(accountDTO)));
    }

    @Override
    public Optional<AccountDTO> updateUserById(UUID id, AccountDTO accountDTO) {
        AtomicReference<Optional<AccountDTO>> atomicReference = new AtomicReference<>();

        userRepository.findById(id).ifPresentOrElse(foundUser -> {
            foundUser.setName(accountDTO.getName());
            foundUser.setYears(accountDTO.getYears());
            atomicReference.set(Optional.of(acountMapper.userToUserDTO(userRepository.save(foundUser))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteUserById(UUID id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<AccountDTO> patchUserById(UUID id, AccountDTO accountDTO) {
        AtomicReference<Optional<AccountDTO>> atomicReference = new AtomicReference<>();
        userRepository.findById(id).ifPresentOrElse(foundUser ->{
            if(StringUtils.hasText(accountDTO.getName())){
                foundUser.setName(accountDTO.getName());
            }

            if(accountDTO.getYears() != foundUser.getYears()){
                foundUser.setYears(accountDTO.getYears());
            }
            atomicReference.set(Optional.of(acountMapper.userToUserDTO(userRepository.save(foundUser))));

        }, () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }
}
