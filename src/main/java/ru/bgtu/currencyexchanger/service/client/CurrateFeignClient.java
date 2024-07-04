package ru.bgtu.currencyexchanger.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.bgtu.currencyexchanger.service.client.dto.CurrencyListDto;
import ru.bgtu.currencyexchanger.service.client.dto.CurrencyMapDto;

@FeignClient(name = "currateFeignClient", url = "${currate.url}")
public interface CurrateFeignClient {

    @GetMapping("/?get=currency_list&key={authToken}")
    CurrencyListDto getCurrencyList(@PathVariable("authToken") String authToken);

    @GetMapping("/?get=rates&pairs={pairs}&key={authToken}")
    CurrencyMapDto getCurrencyToConvert(@PathVariable("pairs") String pairs, @PathVariable("authToken") String authToken);
}