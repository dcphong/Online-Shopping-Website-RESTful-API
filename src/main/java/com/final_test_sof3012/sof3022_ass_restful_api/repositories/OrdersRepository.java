package com.final_test_sof3012.sof3022_ass_restful_api.repositories;

import com.final_test_sof3012.sof3022_ass_restful_api.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {

    @Query(value = "EXEC ordered_procedure :salerId, :page, :pageSize", nativeQuery = true)
    List<Object[]> getOrdersBySalerId(@Param("salerId") Long salerId,
                                      @Param("page") int page,
                                      @Param("pageSize") int pageSize);
}
