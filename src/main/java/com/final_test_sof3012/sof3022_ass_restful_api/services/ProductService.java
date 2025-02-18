package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.ProductRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.mappers.UserMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Category;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.CategoryRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.ProductRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.ProductDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.mappers.ProductMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.UserRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.specifications.ProductSpecifications;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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
    UserService userService;
    CategoryService categoryService;
    UserMapper userMapper;
    ProductMapper productMapper;

    @Transactional
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(
                productRepository.findAll().stream().map(productMapper::toProductDTO).collect(Collectors.toList())
        );
    }

    @Transactional
    public ResponseEntity<?> listWithPaging(int p){
        Page<Product> listPage = productRepository.findAll(PageRequest.of(1,p));
        List<ProductDTO> list = listPage.stream().map(productMapper::toProductDTO).toList();
        return ResponseEntity.ok(
                new ResponseObject<>("OK","list",list)
        );
    }

    @Transactional
    public ResponseEntity<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toProductDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    public List<ProductDTO> getProductsByUser(Long id){
        Specification<Product> productSpec = ProductSpecifications.hasUserId(id);
        List<ProductDTO> list = productRepository.findAll(productSpec).stream().map(productMapper::toProductDTO).toList();
        return list;
    }

    @Transactional
    public Optional<Product> create(ProductRequest request){
        UserDTO user = userService.getById(request.getCreatedBy()).orElseThrow(() -> new RuntimeException("NOT FOUND ANY USER WITH ID: "+request.getCreatedBy()));
        Category category = categoryService.getCategoryById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("NOT FOUND CATEGORY WITH ID: "+request.getCategoryId()));

        Product product = new Product();
        BeanUtils.copyProperties(request,product);
        product.setUser(userMapper.toUser(user));
        product.setCategory(category);

        return Optional.of(productRepository.save(product));
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
