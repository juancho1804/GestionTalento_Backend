package com.gestiontalentounicauca.usuariomicroservice.controller;

import com.gestiontalentounicauca.usuariomicroservice.service.IGoogleSheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/forms")
public class GoogleFormsController {

    @Autowired
    private IGoogleSheetsService googleSheetsService;


    /**
     * Devuelve todas las respuestas de un Spreadsheet de Google Forms
     * @param spreadsheetId El ID del Spreadsheet donde se guardan las respuestas
     */
    @GetMapping("/respuestas")
    public List<List<Object>> getResponses(@RequestParam String spreadsheetId) throws IOException {
        return googleSheetsService.getResponses(spreadsheetId);
    }
}
