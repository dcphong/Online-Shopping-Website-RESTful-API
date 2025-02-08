package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.repositories.ProductRepository;
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

    public ResponseEntity<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toProductDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Product> createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    public ResponseEntity<Product> updateProduct(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    public ResponseEntity<Void> deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Trả về mã 204 No Content
    }

}
