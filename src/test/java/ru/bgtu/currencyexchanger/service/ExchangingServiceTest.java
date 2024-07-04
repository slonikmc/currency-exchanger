package ru.bgtu.currencyexchanger.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bgtu.currencyexchanger.db.model.ExchangingRecord;
import ru.bgtu.currencyexchanger.db.repository.ExchangingRecordRepository;
import ru.bgtu.currencyexchanger.service.client.CurrateFeignClient;
import ru.bgtu.currencyexchanger.service.client.dto.CurrencyListDto;
import ru.bgtu.currencyexchanger.service.client.dto.CurrencyMapDto;
import ru.bgtu.currencyexchanger.web.dto.ConvertCurrencyRequestDto;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangingServiceTest {

    @Mock
    private ExchangingRecordRepository exchangingRecordRepository;
    @Mock
    private CurrateFeignClient currateFeignClient;
    @InjectMocks
    private ExchangingService exchangingService;


    @BeforeEach
    @SneakyThrows
    void init() {
        Field authTokenField = exchangingService.getClass().getDeclaredField("authToken");
        authTokenField.setAccessible(true);
        authTokenField.set(exchangingService, "fsdsad1321asfasf6455");
    }

    @Test
    void getCurrencyList() {
        when(currateFeignClient.getCurrencyList(anyString())).thenReturn(new CurrencyListDto());

        CurrencyListDto currencyList = exchangingService.getCurrencyList();

        verify(currateFeignClient).getCurrencyList(anyString());
        assertNotNull(currencyList);
    }

    @Test
    void convertCurrency() {
        ConvertCurrencyRequestDto convertCurrencyRequestDto = getConvertCurrencyRequestDto();

        when(currateFeignClient.getCurrencyToConvert(anyString(), anyString())).thenReturn(getCurrencyMapDto());
        when(exchangingRecordRepository.save(any())).thenReturn(new ExchangingRecord());

        ExchangingRecord exchangingRecord = exchangingService.convertCurrency(convertCurrencyRequestDto);

        verify(currateFeignClient).getCurrencyToConvert(anyString(), anyString());
        verify(exchangingRecordRepository).save(any());
        assertEquals(convertCurrencyRequestDto.getAmount(), exchangingRecord.getAmount());
        assertEquals(convertCurrencyRequestDto.getCurrencyPair(), exchangingRecord.getExchangeCurrency() + exchangingRecord.getReceivingCurrency());
    }

    private ConvertCurrencyRequestDto getConvertCurrencyRequestDto() {
        ConvertCurrencyRequestDto convertCurrencyRequestDto = new ConvertCurrencyRequestDto();
        convertCurrencyRequestDto.setCurrencyPair("RUBUSD");
        convertCurrencyRequestDto.setAmount(312312.123);
        return convertCurrencyRequestDto;
    }

    private CurrencyMapDto getCurrencyMapDto() {
        CurrencyMapDto currencyMapDto = new CurrencyMapDto();
        Map<String, String> data = new HashMap<>();
        data.put("RUBUSD", "0.12");
        currencyMapDto.setData(data);
        return currencyMapDto;
    }
}