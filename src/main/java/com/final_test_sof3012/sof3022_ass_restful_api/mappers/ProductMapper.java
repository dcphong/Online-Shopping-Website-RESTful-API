package com.final_test_sof3012.sof3022_ass_restful_api.mappers;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.ProductDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.models.OrderDetails;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id" ,target = "categoryId")
    @Mapping(source = "category.name" ,target = "categoryName")
    @Mapping(source = "user.id", target="createdBy")
    @Mapping(target = "orderDetailsIds", expression = "java(mapOrderDetailsListIds(product))")
    @Mapping(target = "descriptions",source = "descriptions")
    ProductDTO toProductDTO(Product product);

    Product toProduct(ProductDTO productDTO);

    default List<Long> mapOrderDetailsListIds(Product product){
        if(product.getOrderDetailsList() == null) return null;
        return product.getOrderDetailsList().stream().map(OrderDetails::getId).collect(Collectors.toList());
    }

}
