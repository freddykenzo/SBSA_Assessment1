package za.co.standardbank.assessment1.method;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import za.co.standardbank.assessment1.domain.model.RegisterUserRequest;
import za.co.standardbank.assessment1.service.UserService;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessUserCsvFileRead {

    private final UserService userService;

    private final ResourceLoader resourceLoader;

    @Value("${app.pathToUserCsvFile}")
    private String pathToCsvFile;

    public void execute() {
        // Only process CSV files
        if (pathToCsvFile.toLowerCase().endsWith(".csv")) {
            try {
                final InputStream inputStream;
                if (pathToCsvFile.contains("classpath:")) {
                    final Resource resource = resourceLoader.getResource(pathToCsvFile);
                    inputStream = resource.getInputStream();
                } else {
                   inputStream = new FileInputStream(pathToCsvFile);
                }
                readUserFromCsvAndSaveOrUpdateUser(inputStream);
                inputStream.close();
            } catch (final IOException e) {
                log.warn("Missing or invalid file {}", pathToCsvFile);
                throw new RuntimeException("Can not read or find the file with the path");
            }
        }

    }

    private void readUserFromCsvAndSaveOrUpdateUser(final InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                final String action = csvRecord.get("Action");
                if ("CREATE".equalsIgnoreCase(action)) {
                    registerUser(csvRecord);
                } else {
                    // TODO: update user
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse CSV file: " + e.getMessage());
        }
    }

    private void registerUser(final CSVRecord csvRecord) {
        try {
            final RegisterUserRequest request = RegisterUserRequest.builder()
                    .firstname(csvRecord.get("Firstname"))
                    .surname(csvRecord.get("Surname"))
                    .email(csvRecord.get("Email"))
                    .mobileNumber(csvRecord.get("Mobile Number"))
                    .build();

            log.debug("Saving user {}", request);

            userService.registerUser(request);
        } catch (final Exception exception) {
            // TODO: find a way to notify or mark the users that failed to be registered
        }
    }
}
