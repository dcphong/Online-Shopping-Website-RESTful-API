package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.services.OrdersService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrdersController {

    OrdersService ordersService;

    @GetMapping("/user/orders")
    public ResponseEntity<ResponseObject> getAllOrders(){
        return ordersService.getAllOrders();
    }
    @GetMapping("/user/orders/{id}")
    public ResponseEntity<ResponseObject> getAllOrders(@PathVariable Long id){
        return ordersService.getAllOrdersByUserId(id);
    }
}
