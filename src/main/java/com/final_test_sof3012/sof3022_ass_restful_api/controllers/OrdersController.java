package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.OrderDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.OrderRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Order;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.OrdersRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.services.OrdersService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrdersController {

     final OrdersService ordersService;
     final OrdersRepository ordersRepository;

    @GetMapping("/user/orders")
    public ResponseEntity<?> getAllOrders(){
        List<OrderDTO> list = ordersService.getAllOrders();
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject<>("ERROR","Not found any order",null)
            );
        }
        return ResponseEntity.ok(
                new ResponseObject<>("SUCCESS","Get all order successfully!",list)
        );
    }
    @GetMapping("/user/orders/{id}")
    public ResponseEntity<?> getAllOrdersById(@PathVariable Long id){
        List<OrderDTO> list = ordersService.getAllOrdersById(id);
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject<>("ERROR","Not found any order with id: "+id,null)
            );
        }
        return ResponseEntity.ok(
                new ResponseObject<>("SUCCESS","Get all order with id:"+id+" successfully!",list)
        );
    }
    @GetMapping("/user/orders/userId/{id}")
    public ResponseEntity<?> getAllOrdersByUserId(@PathVariable Long id){
        List<OrderDTO> list = ordersService.getAllOrdersByUserId(id);
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject<>("ERROR","Not found any order with user id: "+id,null)
            );
        }return ResponseEntity.ok(
                new ResponseObject<>("SUCCESS","Get all order with user id: "+id+" successfully!",list)
        );
    }

    @PostMapping("/user/orders")
    public ResponseEntity<?> order(@RequestBody OrderDTO orderDTO) {
        return ordersService.createOrder(orderDTO);
    }



}
