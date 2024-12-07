package com.llm.backend.controller;

import com.llm.backend.dto.CommonResponse;
import com.llm.backend.dto.SqlDeckRequest;
import com.llm.backend.service.SqlDeckService;
import java.util.List;
import java.util.Map;
import javax.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SqlDeckController {

    private final SqlDeckService sqlDeckService;

    @PostMapping("/sqldeck/execute")
    public ResponseEntity<CommonResponse> executeSqlDeck(@RequestBody SqlDeckRequest sqlQuery) {
        try {
            List<Map<String, Object>> result = sqlDeckService.executeNativeQuery(sqlQuery.getSqlQuery());
            return ResponseEntity.ok(CommonResponse.ok(result));
        } catch (PersistenceException e) {
            return ResponseEntity.badRequest().body(CommonResponse.ok("Error processing the SQL query: " + sqlQuery.getSqlQuery()));
        }
    }

}
