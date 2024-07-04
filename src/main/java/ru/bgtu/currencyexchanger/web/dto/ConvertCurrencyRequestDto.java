package ru.bgtu.currencyexchanger.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvertCurrencyRequestDto {

    private Double amount;
    private String currencyPair;
}