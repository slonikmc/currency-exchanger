package ru.bgtu.currencyexchanger.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bgtu.currencyexchanger.db.model.ExchangingRecord;
import ru.bgtu.currencyexchanger.service.ExchangingService;
import ru.bgtu.currencyexchanger.service.client.dto.CurrencyListDto;
import ru.bgtu.currencyexchanger.web.dto.ConvertCurrencyRequestDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/currency-exchanger", produces = "application/json")
public class CurrencyController {

    private final ExchangingService exchangingService;

    @GetMapping(value = "/currency-list")
    public CurrencyListDto getCurrencyList() {
        log.info("Getting currency list");
        return exchangingService.getCurrencyList();
    }

    @PostMapping("/convert")
    public ExchangingRecord convertCurrency(@RequestBody ConvertCurrencyRequestDto convertCurrencyRequestDto) {
        return exchangingService.convertCurrency(convertCurrencyRequestDto);
    }
}