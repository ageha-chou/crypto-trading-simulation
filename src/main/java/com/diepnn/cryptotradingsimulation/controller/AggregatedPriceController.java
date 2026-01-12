package com.diepnn.cryptotradingsimulation.controller;

import com.diepnn.cryptotradingsimulation.dto.response.AggregatedPriceDTO;
import com.diepnn.cryptotradingsimulation.service.AggregatedPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/aggregated-prices")
@RequiredArgsConstructor
public class AggregatedPriceController {
    private final AggregatedPriceService aggregatedPriceService;

    @GetMapping
    public ResponseEntity<List<AggregatedPriceDTO>> getAggregatedPrices() {
        List<AggregatedPriceDTO> data = aggregatedPriceService.getAll();
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(data);
    }
}
