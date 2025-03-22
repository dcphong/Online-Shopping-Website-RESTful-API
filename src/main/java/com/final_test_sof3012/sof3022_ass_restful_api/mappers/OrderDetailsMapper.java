package com.final_test_sof3012.sof3022_ass_restful_api.mappers;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.OrderDetailsDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.ProductDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.response.OrderDetailsResponse;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Order;
import com.final_test_sof3012.sof3022_ass_restful_api.models.OrderDetails;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring")
public interface OrderDetailsMapper {

    OrderDetailsMapper INSTANCE = Mappers.getMapper(OrderDetailsMapper.class);

    @Mapping(target = "productId",source = "product.id")
    @Mapping(target = "orderId",source = "order.id")
    OrderDetailsDTO toOrderDetailsDTO(OrderDetails orderDetails);

    @Mapping(target = "descriptions",source = "orderDetailsDTO.descriptions")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price",source = "orderDetailsDTO.price")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "product", source = "product")
    OrderDetails toOrder(OrderDetailsDTO orderDetailsDTO, Order order, Product product);

    @Mapping(target = "id",source = "orderDetailsDTO.id")
    @Mapping(target = "product",source = "product")
    @Mapping(target = "price",source = "orderDetailsDTO.price")
    @Mapping(target = "descriptions",source = "orderDetailsDTO.descriptions")
    OrderDetailsResponse toOrderDetailsResponse(OrderDetailsDTO orderDetailsDTO, ProductDTO product);
}
