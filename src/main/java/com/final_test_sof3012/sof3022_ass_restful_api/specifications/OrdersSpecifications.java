package com.final_test_sof3012.sof3022_ass_restful_api.specifications;

import com.final_test_sof3012.sof3022_ass_restful_api.models.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class OrdersSpecifications {

    public static Specification<Order> hasUserId(Long id) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb
                    .equal(root.get("user").get("id"), id);
    }

    public static Specification<Order> hasUserIdBasicCoding(Long id){
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
               return criteriaBuilder.equal(root.get("user").get("id"),id);
            }
        };
    }

}
