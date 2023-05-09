package com.ecommerce.library.model;

import lombok.*;

import javax.persistence.*;

import org.springframework.stereotype.Component;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity

@Table(name = "shopping_cart")
@Component
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id")
    private Long id;
    @Column(name = "total_items")
    private int totalItems;
    @Column(name = "total_prices")
    private double totalPrices;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartItem> cartItem;

    
    


	
	
    
    
}
