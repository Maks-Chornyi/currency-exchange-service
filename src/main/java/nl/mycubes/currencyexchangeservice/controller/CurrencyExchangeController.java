package nl.mycubes.currencyexchangeservice.controller;

import nl.mycubes.currencyexchangeservice.domain.ExchangeValue;
import nl.mycubes.currencyexchangeservice.repo.ExchangeValueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    ExchangeValueRepo exchangeValueRepo;

    private static final int DEFAULT_PORT = 8080;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

        ExchangeValue fromAndTo = exchangeValueRepo.findByFromAndTo(from, to);
        Optional<String> optional = Optional.ofNullable(environment.getProperty("local.server.port"));
        if (!optional.isPresent()) {
            fromAndTo.setPort(DEFAULT_PORT);
        } else {
            fromAndTo.setPort(Integer.parseInt(optional.get()));
        }
        return fromAndTo;
    }
}
