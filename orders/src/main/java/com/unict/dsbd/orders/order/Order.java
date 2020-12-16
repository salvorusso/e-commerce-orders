package com.unict.dsbd.orders.order;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


public class Order {
        @Id
        public int id;
        public String name;
        public int quantity;

    public Order(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Order() {
    }

    public int getId()  {
        return id;
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Order setName(String name) {
        this.name = name;
        return this;
    }

    public int getnOrder() {
        return quantity;
    }

    public Order setnOrder(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nOrder=" + quantity +
                '}';
    }
}
