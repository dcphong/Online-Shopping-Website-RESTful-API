package com.final_test_sof3012.sof3022_ass_restful_api.specifications;

import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> hasUserId(Long id) {
        return (Root<Product> root,CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("user").get("id"),id);
    }

    public static Specification<Product> hasKeyword(String key){
        return (Root<Product> root,CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.like(cb.lower(root.get("name")),"%"+key.toLowerCase()+"%");
    }

    public static Specification<Product> hasCategory(String category){
        return (root,query,cb) -> {
            return cb.equal(root.get("category").get("name"),category);
        };
    }

    public static Specification<Product> isAvailable(String category){
        return null;
    }

}
