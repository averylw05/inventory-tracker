package com.example.inventory_backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "parts")
public class PartEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;

protected PartEntity(){

}
public PartEntity(String name, int quantity){
    this.name = name;
    this.quantity = quantity;
}

public Long getId(){
    return id;
}

public String getName(){
    return name;
}

public int getQuantity(){
    return quantity;
}
}