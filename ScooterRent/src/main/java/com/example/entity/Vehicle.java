package com.example.entity;

import com.example.enums.VehiclesStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

    @Column(nullable = false)
    private Long serialNumber;

    @Column(nullable = false)
    private Integer batteryLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehiclesStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "rental_point_id", referencedColumnName = "id")
    private RentalPoint rentalPoint;

    @Column(nullable = false)
    private Integer mileage;
}
