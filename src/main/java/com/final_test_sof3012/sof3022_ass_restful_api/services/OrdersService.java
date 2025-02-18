package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.OrderDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.OrderRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.mappers.OrderMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Order;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.OrderDetailsRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.OrdersRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.UserRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.specifications.OrdersSpecifications;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OrdersService {

    final OrdersRepository orderRepository;
    final OrderMapper orderMapper;
    final OrderDetailsRepository orderDetailsRepository;
    final UserRepository userRepository;

    @Transactional
    public List<OrderDTO> getAllOrders(){
        List<OrderDTO> list = orderRepository.findAll().stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
        return list;
    }

    @Transactional
    public List<OrderDTO> getAllOrdersById(Long id){
        List<OrderDTO> list = orderRepository.findById(id).stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
        return list;
    }

    @Transactional
    public List<OrderDTO> getAllOrdersByUserId(Long id){
        Specification<Order> orderSpec = OrdersSpecifications.hasUserId(id);
        List<OrderDTO> list = orderRepository.findAll(orderSpec).stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
        return list;
    }

    @Transactional
    public ResponseEntity<?> createOrder(OrderDTO orderDTO){
        User user = userRepository.findById(orderDTO.getUserId()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject<>("ERROR", "User not found!", null)
            );
        }
        Order order = orderMapper.toOrder(orderDTO,user);

        orderRepository.save(order);

        return ResponseEntity.ok(
                new ResponseObject<>("OK", "Created Orders successfully!", order)
        );
    }

}
