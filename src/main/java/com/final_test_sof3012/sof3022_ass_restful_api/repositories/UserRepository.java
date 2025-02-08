package com.final_test_sof3012.sof3022_ass_restful_api.repositories;

import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
