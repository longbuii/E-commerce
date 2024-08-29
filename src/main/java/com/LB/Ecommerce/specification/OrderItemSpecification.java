package com.LB.Ecommerce.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.LB.Ecommerce.entity.OrderItem;
import com.LB.Ecommerce.enums.OrderStatus;




public class OrderItemSpecification {


    // Specification to filter order items by status/
    public static Specification<OrderItem> hasStatus(OrderStatus status){
        return ((root,query,criteriaBuilder) -> status !=null ? criteriaBuilder.equal(root.get("status"), status): null);
 
    }

    // Specification to filter order items by data range/
    public static Specification<OrderItem> createBetween(LocalDateTime startDate, LocalDateTime endDate){
        return ((root,query,criterBuilder) -> {
            if(startDate !=null && endDate != null){
                return criterBuilder.between(root.get("createdAt"), startDate, endDate);
            }else if(startDate !=null){
                return criterBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            }else if(endDate != null){
                return criterBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            }else{
                return null;
            }
        });
    }

    /** Generate specification to filter orderitems by item id */
    public static Specification<OrderItem> hasItemId(Long itemId){
        return  ((root,query,criteriaBuilder)->
            itemId != null ? criteriaBuilder.equal(root.get("id"),itemId):null);
    }

}
