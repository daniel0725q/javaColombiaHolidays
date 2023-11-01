package com.quinterodaniel.holidays.controllers;

import com.quinterodaniel.holidays.services.HolidaysService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festivos")
public class HolidaysController {
    private final HolidaysService holidaysService;

    public HolidaysController(HolidaysService holidaysService) {
        this.holidaysService = holidaysService;
    }

    @GetMapping("/verificar/{year}/{month}/{day}")
    public ResponseEntity<String> verify(@PathVariable Integer year,
                                 @PathVariable Integer month,
                                 @PathVariable Integer day) {
        if (holidaysService.isValid(year + "-" + month + "-" + day)) {
            return ResponseEntity.ok(holidaysService.isHoliday(year, month - 1, day) ?
                    "Es Festivo" : "No es festivo");
        }
        return ResponseEntity.ok("Fecha No valida");
    }
}
