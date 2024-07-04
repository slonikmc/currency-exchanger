package ru.bgtu.currencyexchanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.bgtu.currencyexchanger.db.model.ExchangingRecord;
import ru.bgtu.currencyexchanger.db.repository.ExchangingRecordRepository;
import ru.bgtu.currencyexchanger.service.client.CurrateFeignClient;
import ru.bgtu.currencyexchanger.service.client.dto.CurrencyListDto;
import ru.bgtu.currencyexchanger.service.client.dto.CurrencyMapDto;
import ru.bgtu.currencyexchanger.web.dto.ConvertCurrencyRequestDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangingService {

    private final ExchangingRecordRepository exchangingRecordRepository;
    private final CurrateFeignClient currateFeignClient;

    @Value("${currate.authToken}")
    private String authToken;

    public CurrencyListDto getCurrencyList() {
        return currateFeignClient.getCurrencyList(authToken);
    }

    public ExchangingRecord convertCurrency(ConvertCurrencyRequestDto convertCurrencyRequestDto) {
        CurrencyMapDto currencyMapDto = currateFeignClient.getCurrencyToConvert(convertCurrencyRequestDto.getCurrencyPair(), authToken);
        ExchangingRecord exchangingRecord = buildExchangingRecord(convertCurrencyRequestDto, currencyMapDto);
        log.info("Converting currency pair: {}, amount: {}, exchange rate: {}",
                convertCurrencyRequestDto.getCurrencyPair(), convertCurrencyRequestDto.getAmount(), exchangingRecord.getExchangeRate());
        exchangingRecordRepository.save(exchangingRecord);
        return exchangingRecord;
    }

    private ExchangingRecord buildExchangingRecord(ConvertCurrencyRequestDto convertCurrencyRequestDto, CurrencyMapDto currencyMapDto) {
        return ExchangingRecord.builder()
                .amount(convertCurrencyRequestDto.getAmount())
                .exchangeCurrency(convertCurrencyRequestDto.getCurrencyPair().substring(0, 3))
                .receivingCurrency(convertCurrencyRequestDto.getCurrencyPair().substring(3))
                .convertedAmount(convertCurrencyRequestDto.getAmount() * Double.parseDouble(currencyMapDto.getData().get(convertCurrencyRequestDto.getCurrencyPair())))
                .exchangeRate(Double.parseDouble(currencyMapDto.getData().get(convertCurrencyRequestDto.getCurrencyPair())))
                .build();
    }
}