package com.final_test_sof3012.sof3022_ass_restful_api.repositories;

import com.final_test_sof3012.sof3022_ass_restful_api.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
}
