package com.gestiontalentounicauca.usuariomicroservice.service;


import java.io.IOException;
import java.util.List;

public interface IGoogleSheetsService {
    List<List<Object>> getResponses(String spreadsheetId) throws IOException;
}
