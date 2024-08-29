package com.LB.Ecommerce.service.interf;

import com.LB.Ecommerce.dto.AddressDto;
import com.LB.Ecommerce.dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);
}
