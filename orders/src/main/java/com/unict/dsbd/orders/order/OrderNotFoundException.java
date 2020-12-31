package com.unict.dsbd.orders.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Order Not Found")
public class OrderNotFoundException extends Exception {

}
