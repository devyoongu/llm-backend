package com.llm.backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqlDeckService {

    private final EntityManager entityManager;

    public List<Map<String, Object>> executeNativeQuery(String sql) {
        // SQL 쿼리의 타입을 확인하고, 조회 쿼리일 때만 실행
        if (isSelectQuery(sql)) {
            try {
                Query nativeQuery = entityManager.createNativeQuery(sql);

                nativeQuery.unwrap(org.hibernate.query.NativeQuery.class)
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

                List<Map<String, Object>> resultMaps = nativeQuery.getResultList();
                return resultMaps;

            } catch (PersistenceException e) {
                log.error("Error processing the SQL query is = {}", sql);
                throw new PersistenceException(e);
            }
        } else {
            log.info("executeNativeQuery is not select sql {}", sql);
            return Collections.emptyList();
        }
    }

    private boolean isSelectQuery(String sql) {
        return sql.trim().toUpperCase().startsWith("SELECT");
    }

}
