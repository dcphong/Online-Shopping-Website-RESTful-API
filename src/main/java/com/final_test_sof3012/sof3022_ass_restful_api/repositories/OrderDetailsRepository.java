package com.final_test_sof3012.sof3022_ass_restful_api.repositories;

import com.final_test_sof3012.sof3022_ass_restful_api.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long>, JpaSpecificationExecutor<OrderDetails> {
}
