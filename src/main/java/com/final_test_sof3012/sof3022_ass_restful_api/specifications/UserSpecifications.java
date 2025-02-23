package com.final_test_sof3012.sof3022_ass_restful_api.specifications;

import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> hasUsername(String username){
        return (Root<User> root,CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.equal(root.get("username"),username);
    }

    public static Specification<User> hasPhoneNumber(String phone){
        return (root,query,cb) -> {
            return cb.equal(root.get("phone"),phone);
        };
    }

    public static Specification<User> hasEmail(String email){
        return (root,query,cb) -> {
            return cb.equal(root.get("email"),email);
        };
    }

}
