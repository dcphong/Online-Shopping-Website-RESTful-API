package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.Repositories.AccountRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Account;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;

    public List<Account> getAllUsers(){
        return accountRepository.findAll();
    }

    public Optional<Account> getUserById(Optional<Account> user){
        return accountRepository.findById(user.get().getUsername());
    }

}
