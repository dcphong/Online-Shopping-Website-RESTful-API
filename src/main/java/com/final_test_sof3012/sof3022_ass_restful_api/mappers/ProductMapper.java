package com.final_test_sof3012.sof3022_ass_restful_api.mappers;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.ProductDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id" ,target = "categoryId")
    @Mapping(source = "category.name" ,target = "categoryName")
    ProductDTO toProductDTO(Product product);

    Product toProduct(ProductDTO productDTO);

}
