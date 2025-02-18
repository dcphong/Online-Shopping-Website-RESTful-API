package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.OrderDetailsDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.response.OrderDetailsResponse;
import com.final_test_sof3012.sof3022_ass_restful_api.mappers.OrderDetailsMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Order;
import com.final_test_sof3012.sof3022_ass_restful_api.models.OrderDetails;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.OrderDetailsRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.OrdersRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.ProductRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.specifications.OrderDetailsSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderDetailsMapper orderDetailsMapper;
    private final OrderDetailsSpecifications orderDetailsSpecifications;
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;

    @Transactional
    public List<OrderDetailsDTO> getAllOrderDetails() {
        List<OrderDetailsDTO> list = orderDetailsRepository.findAll().stream().map(orderDetailsMapper::toOrderDetailsDTO).toList();
        return list;
    }

    @Transactional
    public List<OrderDetailsDTO> getAllByOrderId(Long id){
        Specification<OrderDetails> orderDetailsSpec = orderDetailsSpecifications.hasOrderId(id);
        List<OrderDetailsDTO> list = orderDetailsRepository
                .findAll(orderDetailsSpec)
                .stream()
                .map(orderDetailsMapper::toOrderDetailsDTO)
                .collect(Collectors.toList());
        return list;
    }

    @Transactional
    public ResponseEntity<?> createOrderDetails(List<OrderDetailsDTO> orderDetailsDTO) {
        if (orderDetailsDTO.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        Set<Long> orderId = orderDetailsDTO.stream().map(OrderDetailsDTO::getOrderId).collect(Collectors.toSet());
        Set<Long> productId = orderDetailsDTO.stream().map(OrderDetailsDTO::getProductId).collect(Collectors.toSet());

        Map<Long, Order> orders = ordersRepository.findAllById(orderId).stream().collect(Collectors.toMap(Order::getId, o -> o));
        Map<Long, Product> products = productRepository.findAllById(productId).stream().collect(Collectors.toMap(Product::getId, p -> p));

        List<OrderDetails> orderDetailsList = orderDetailsDTO.stream()
                .map(od -> {
                    Order order = Optional.ofNullable(orders.get(od.getOrderId()))
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_WITH_ID:"+od.getOrderId()));
                    Product product = Optional.ofNullable(products.get(od.getProductId()))
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_PRODUCT_WITH_ID:"+od.getProductId()));
                    return orderDetailsMapper.toOrder(od,order,product);
                }).toList();

        List<OrderDetails> listAfterSave =  orderDetailsRepository.saveAll(orderDetailsList);

        return ResponseEntity.ok(
                new ResponseObject<>("OK","Save list orderDetails successfully!!",listAfterSave)
        );
    }
}
