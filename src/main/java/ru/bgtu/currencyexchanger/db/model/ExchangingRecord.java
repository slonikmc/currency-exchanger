package ru.bgtu.currencyexchanger.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "exchanging-history")
@AllArgsConstructor
@NoArgsConstructor
public class ExchangingRecord {

    @Id
    @GeneratedValue
    private UUID id;
    private Double amount;
    private String exchangeCurrency;
    private String receivingCurrency;
    private Double convertedAmount;
    private Double exchangeRate;
    @CreationTimestamp
    private LocalDateTime operationDateTime;
}