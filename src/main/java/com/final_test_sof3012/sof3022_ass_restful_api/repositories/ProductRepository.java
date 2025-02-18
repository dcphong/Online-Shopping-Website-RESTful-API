package com.final_test_sof3012.sof3022_ass_restful_api.repositories;

import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product> {
}
