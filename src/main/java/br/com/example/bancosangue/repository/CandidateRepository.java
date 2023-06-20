package br.com.example.bancosangue.repository;

import br.com.example.bancosangue.entity.Candidate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query(value = "SELECT c.estado, COUNT(c) "
            + "FROM Candidate c "
            + "WHERE EXTRACT(year FROM AGE(CURRENT_DATE, c.data_nasc)) BETWEEN 16 AND 69 "
            + "AND (c.peso ) > 50 "
            + "GROUP BY c.estado", nativeQuery = true)
    List<Object[]> countCandidatesByState();

    @Query(value = "SELECT "
            + "CASE "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 0 AND 10 THEN '0-10' "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 11 AND 20 THEN '11-20' "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 21 AND 30 THEN '21-30' "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 31 AND 40 THEN '31-40' "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 41 AND 50 THEN '41-50' "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 51 AND 60 THEN '51-60' "
            + "   ELSE '61+' "
            + "END AS ageRange, "
            + "AVG(c.peso / (c.altura * c.altura)) "
            + "FROM Candidate c "
            + "GROUP BY "
            + "CASE "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 0 AND 10 THEN '0-10' "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 11 AND 20 THEN '11-20' "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 21 AND 30 THEN '21-30' "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 31 AND 40 THEN '31-40' "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 41 AND 50 THEN '41-50' "
            + "   WHEN FLOOR(EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM c.data_nasc)) BETWEEN 51 AND 60 THEN '51-60' "
            + "   ELSE '61+' "
            + "END "
            + "ORDER BY ageRange", nativeQuery = true)
    List<Object[]> calculateAverageIMCByAgeRange();

    Long countBySexo(String sexo);
    
    @Query(value=  "SELECT COUNT(*) FROM CANDIDATE c WHERE c.SEXO= ?1 AND (c.peso / (c.altura * c.altura)) > ?2",nativeQuery = true)
    Long countBySexoAndIMCGreaterThan(String sexo, Double IMC);

    @Query(value = "SELECT tipo_sanguineo, AVG(EXTRACT(YEAR FROM AGE(CAST(data_nasc AS DATE)))) "
            + "FROM candidate "
            + "WHERE EXTRACT(year FROM AGE(CURRENT_DATE, CAST(data_nasc AS DATE))) BETWEEN 16 AND 69 "
            + "AND (peso ) > 50 "
            + "GROUP BY tipo_sanguineo",
            nativeQuery = true)
    List<Object[]> calculateAverageAgeByBloodType();

    @Query(value = "SELECT c.tipo_sanguineo, COUNT(c) "
            + "FROM Candidate c "
            + "WHERE EXTRACT(year FROM AGE(CURRENT_DATE, c.data_nasc)) BETWEEN 16 AND 69 "
            + "AND (c.peso ) > 50 "
            + "GROUP BY c.tipo_sanguineo", nativeQuery = true)
    List<Object[]> countPossibleDonorsByBloodType();

}
