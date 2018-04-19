package org.mindtrails.persistence;

import org.mindtrails.domain.questionnaire.QuestionnaireData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface QuestionnaireRepository<T extends QuestionnaireData>
        extends JpaRepository<T, Long> {
    public List<T> findByIdGreaterThan(long id);

    @Query("SELECT COUNT (DISTINCT participant) FROM #{#entityName}")
    Integer countDistinctByParticipant();

}