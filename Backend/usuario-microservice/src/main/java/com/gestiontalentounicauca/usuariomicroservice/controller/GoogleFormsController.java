package com.gestiontalentounicauca.usuariomicroservice.controller;

import com.gestiontalentounicauca.usuariomicroservice.service.IGoogleSheetsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/forms")
@Tag(
        name = "Google Forms",
        description = "Consulta de respuestas almacenadas en Google Forms / Google Sheets"
)
public class GoogleFormsController {

    @Autowired
    private IGoogleSheetsService googleSheetsService;

    // ------------------ OBTENER RESPUESTAS ------------------
    @Operation(
            summary = "Obtener respuestas de Google Forms",
            description = "Devuelve todas las respuestas almacenadas en un Spreadsheet asociado a Google Forms"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Respuestas obtenidas correctamente"),
            @ApiResponse(responseCode = "400", description = "ID del Spreadsheet inválido"),
            @ApiResponse(responseCode = "500", description = "Error al consultar Google Sheets")
    })
    @GetMapping("/respuestas")
    public List<List<Object>> getResponses(
            @Parameter(
                    description = "ID del Spreadsheet de Google Forms",
                    example = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms",
                    required = true
            )
            @RequestParam String spreadsheetId
    ) throws IOException {

        return googleSheetsService.getResponses(spreadsheetId);
    }
}
