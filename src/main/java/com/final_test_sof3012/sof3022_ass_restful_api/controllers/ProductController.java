package com.final_test_sof3012.sof3022_ass_restful_api.controllers;


import com.final_test_sof3012.sof3022_ass_restful_api.dto.ProductDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.ProductRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @GetMapping("products")
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("products/page/{p}")
    public ResponseEntity<?> getAllProductsPage(@PathVariable int p) {
        return productService.listWithPaging(p);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id).getBody();
        if( product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject<>("ERROR","Not found products with id "+id,null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject<>("OK","Search for product successfully",product)
        );
    }

    @GetMapping("user/products/{id}")
    public ResponseEntity<?> getProductsByUser(@PathVariable Long id){
        List<ProductDTO> list = productService.getProductsByUser(id);
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject<>("EMPTY","NOT FOUND ANY PRODUCTS CREATED BY USER:"+id,null)
            );
        }
        return ResponseEntity.ok(
                new ResponseObject<>("OK","GET PRODUCTS LIST BY USER ID: "+id+" SUCCESSFULLY!",list)
        );
    }


    @PostMapping("user/products")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request){
        Optional<Product> product = productService.create(request);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject<>("ERROR","Can't not create product!",null)
            );
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject<>("OK","Create product successfully",product)
        );
    }

}
