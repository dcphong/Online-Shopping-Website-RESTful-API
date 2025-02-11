package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.OrderDetailsDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.mappers.OrderDetailsMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.OrderDetails;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.OrderDetailsRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.specifications.OrderDetailsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderDetailsMapper orderDetailsMapper;
    private final OrderDetailsSpecifications orderDetailsSpecifications;

    public List<OrderDetailsDTO> getAllOrderDetails(){
        List<OrderDetailsDTO> list = orderDetailsRepository.findAll().stream().map(orderDetailsMapper::toOrderDetailsDTO).toList();
        return list;
    }

    public List<OrderDetailsDTO> getAllByOrderId(Long id){
        Specification<OrderDetails> orderDetailsSpec = orderDetailsSpecifications.hasOrderId(id);
        List<OrderDetailsDTO> list = orderDetailsRepository
                .findAll(orderDetailsSpec)
                .stream()
                .map(orderDetailsMapper::toOrderDetailsDTO)
                .collect(Collectors.toList());
        return list;
    }

}
