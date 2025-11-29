package com.gestiontalentounicauca.usuariomicroservice.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleSheetsServiceImpl implements IGoogleSheetsService{

    private Sheets sheetsService;
    private Drive driveService;

    public GoogleSheetsServiceImpl() {
        // constructor vacío
    }

    @PostConstruct
    public void init() {
        try {
            ClassPathResource resource = new ClassPathResource("credentials.json");
            System.out.println("paso aqui");
            if (!resource.exists()) {
                throw new RuntimeException("credentials.json NO encontrado en classpath");
            }
            System.out.println("paso aqui");

            try (InputStream in = resource.getInputStream()) {
                // Inicialización usando la versión antigua de google-api-client
                GoogleCredentials credentials =
                        GoogleCredentials.fromStream(in)
                                .createScoped(List.of(SheetsScopes.SPREADSHEETS_READONLY, DriveScopes.DRIVE_READONLY));

                sheetsService = new Sheets.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(),
                        new HttpCredentialsAdapter(credentials)
                ).setApplicationName("Spring Google Forms Service").build();

                driveService = new Drive.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(),
                        new HttpCredentialsAdapter(credentials)
                ).setApplicationName("Spring Google Forms Service").build();
            }
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Error inicializando GoogleSheetsServiceImpl: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene el rango completo de la primera pestaña del Spreadsheet
     */
    public String getFullRange(String spreadsheetId) throws IOException {
        Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetId)
                .setIncludeGridData(false)
                .execute();

        List<Sheet> sheets = spreadsheet.getSheets();
        if (sheets == null || sheets.isEmpty()) {
            throw new RuntimeException("No se encontró ninguna pestaña en el Spreadsheet.");
        }

        String sheetName = sheets.get(0).getProperties().getTitle();

        return sheetName;
    }


    /**
     * Lee todas las respuestas del Spreadsheet directamente usando Spreadsheet ID
     */
    public List<List<Object>> getResponses(String spreadsheetId) throws IOException {
        String range = getFullRange(spreadsheetId);
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();
        if(values == null || values.isEmpty()) {
            return List.of();
        }

        List<List<Object>> filtered = new ArrayList<>();
        for(List<Object> row : values) {
            if(row.size() > 1) {
                filtered.add(row.subList(1, row.size()));
            }
        }
        filtered.remove(0);

        return filtered;
    }
}
