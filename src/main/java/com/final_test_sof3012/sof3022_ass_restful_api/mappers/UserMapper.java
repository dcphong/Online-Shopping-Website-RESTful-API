package com.final_test_sof3012.sof3022_ass_restful_api.mappers;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Order;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles",source = "roles")
    @Mapping(target = "orderListIds", expression = "java(mapOrderListIds(user))")
    @Mapping(target = "productCreatingListIds", expression = "java(mapProductCreatingListIds(user))")
    UserDTO toUserDTO(User user);
    User toUser (UserDTO userDTO);

    default List<Long> mapOrderListIds(User user){
        if(user.getOrderList() == null)return null;
        return user.getOrderList().stream().map(Order::getId).collect(Collectors.toList());
    }

    default List<Long> mapProductCreatingListIds(User user){
        if(user.getProductsList() == null)return null;
        return user.getProductsList().stream().map(Product::getId).collect(Collectors.toList());
    }
}
