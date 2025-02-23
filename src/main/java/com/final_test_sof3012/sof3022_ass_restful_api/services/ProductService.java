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

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductRepository productRepository;
    UserService userService;
    CategoryService categoryService;
    UserMapper userMapper;
    ProductMapper productMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(
                productRepository.findAll().stream().map(productMapper::toProductDTO).collect(Collectors.toList())
        );
    }

    @Transactional
    public ResponseEntity<?> listWithPaging(int p) {
        Page<Product> listPage = productRepository.findAll(PageRequest.of(1, p));
        List<ProductDTO> list = listPage.stream().map(productMapper::toProductDTO).toList();
        return ResponseEntity.ok(
                new ResponseObject<>("OK", "list", list)
        );
    }

    @Transactional
    public Optional<ProductDTO> getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND ANY PRODUCT WITH ID:" + id));
        return Optional.of(productMapper.toProductDTO(product));
    }

    @Transactional
    protected Product findProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND ANY PRODUCT WITH ID:" + id));
        product.getOrderDetailsList().size();
        return product;
    }

    @Transactional
    public List<ProductDTO> getProductsByUser(Long id) {
        Specification<Product> productSpec = ProductSpecifications.hasUserId(id);
        return productRepository.findAll(productSpec).stream().map(productMapper::toProductDTO).toList();
    }

    @Transactional
    public Optional<Product> create(ProductRequest request) {
        UserDTO user = userService.getById(request.getCreatedBy()).orElseThrow(() -> new RuntimeException("NOT FOUND ANY USER WITH ID: " + request.getCreatedBy()));
        Category category = categoryService.getCategoryById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("NOT FOUND CATEGORY WITH ID: " + request.getCategoryId()));

        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        product.setUser(userMapper.toUser(user));
        product.setCreatedDate(LocalDateTime.now());
        product.setCategory(category);

        return Optional.of(productRepository.save(product));
    }

    @Transactional
    public Optional<Product> updateProduct(Long id, ProductRequest request) {
        if (!productRepository.existsById(id)) {
            return Optional.empty();
        }
        Product product = findProduct(id);
        var productImage = product.getImage();
        BeanUtils.copyProperties(request, product);
        if (request.getImage() == null || request.getImage().equals("")) {
            product.setImage(productImage);
        }
        product.setUser(userRepository.findById(request.getCreatedBy()).orElseThrow(() -> new RuntimeException("NOT FOUND ANY USER WITH ID: " + request.getCreatedBy())));
        product.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("NOT FOUND ANY CATEGORY WITH ID: " + request.getCategoryId())));
        Product updatedProduct = productRepository.save(product);
        return Optional.of(updatedProduct);
    }

    @Transactional
    public ResponseEntity<?> deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok(
                new ResponseObject<>("DELETE", "Delete product with id: " + id + " successfully!", productRepository.findById(id).orElse(null))
        );
    }

    @Transactional
    public List<ProductDTO> searchProduct(String key) {
        Specification<Product> spec = ProductSpecifications.hasKeyword(key);
        List<Product> list = productRepository.findAll(spec);
        return list.stream().map(productMapper::toProductDTO).toList();
    }

}
