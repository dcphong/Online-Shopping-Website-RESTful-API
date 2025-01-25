package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.Repositories.ProductRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.ProductDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.mappers.ProductMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;

    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(
                productRepository.findAll().stream().map(productMapper::toProductDTO).collect(Collectors.toList())
        );
    }

    public ResponseEntity<Product> getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok) // Nếu tìm thấy, trả về 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Nếu không tìm thấy, trả về 404 Not Found
    }

    public ResponseEntity<Product> createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    public ResponseEntity<Product> updateProduct(Integer id, Product product) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    public ResponseEntity<Void> deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Trả về mã 204 No Content
    }

}
