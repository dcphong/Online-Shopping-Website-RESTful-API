package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.OrderDetailsDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.services.OrderDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/orderDetails")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @GetMapping("")
    public ResponseEntity<?> getAllOrderDetails() {
        List<OrderDetailsDTO> list = orderDetailsService.getAllOrderDetails();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject<>("ERROR", "Not found any order details list!", null)
            );
        }
        return ResponseEntity.ok(
                new ResponseObject<>("SUCCESS", "Get all order details list successfully!", list)
        );
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getAllByOrderId(@PathVariable Long id) {
        List<OrderDetailsDTO> list = orderDetailsService.getAllByOrderId(id);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject<>("NOT_FOUND", "Not found any order details with order id: " + id, null)
            );
        }
        return ResponseEntity.ok(
                new ResponseObject<>("SUCCESS", "Get order details with order id: " + id + " successfully!", list)
        );
    }

    @PostMapping("")
    public ResponseEntity<?> createdOrderDetails(@RequestBody List<OrderDetailsDTO> orderDetailsDTO) {
        return orderDetailsService.createOrderDetails(orderDetailsDTO);
    }

    @GetMapping("product/order/{id}")
    public ResponseEntity<?> groupByOrderId(@PathVariable Long id){
        return ResponseEntity.ok(
                new ResponseObject<>("OK","Get order details with product successfully!",orderDetailsService.groupByOrderId(id))
        );
    }
}
