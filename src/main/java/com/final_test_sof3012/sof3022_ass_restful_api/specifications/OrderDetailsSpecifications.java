package com.final_test_sof3012.sof3022_ass_restful_api.specifications;

import com.final_test_sof3012.sof3022_ass_restful_api.models.OrderDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsSpecifications {

    public Specification<OrderDetails> hasOrderId(Long id){
        return (Root<OrderDetails> root,CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("order").get("id"),id);
    }

}
