package br.com.example.bancosangue.service;

import br.com.example.bancosangue.entity.Candidate;
import br.com.example.bancosangue.repository.CandidateRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public void importCandidates(List<Candidate> candidates) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Candidate candidate : candidates) {
            String formattedDate = candidate.getData_nasc().format(outputFormatter);
            candidate.setData_nasc(LocalDate.parse(formattedDate, outputFormatter));
        }

        candidateRepository.saveAll(candidates);
    }

    public Map<String, Long> getCandidatesByState() {
        List<Object[]> result = candidateRepository.countCandidatesByState();
        Map<String, Long> candidatesByState = new HashMap<>();

        for (Object[] row : result) {
            String state = (String) row[0];
            Long count = ((Number) row[1]).longValue();
            candidatesByState.put(state, count);
        }

        return candidatesByState;
    }

    public Map<String, Double> getAverageIMCByAgeRange() {
        List<Object[]> result = candidateRepository.calculateAverageIMCByAgeRange();
        Map<String, Double> averageIMCByAgeRange = new HashMap<>();

        if (result != null && !result.isEmpty()) {
            for (Object[] row : result) {
                String ageRange = (String) row[0];
                Double averageIMC = (Double) row[1];
                averageIMCByAgeRange.put(ageRange, averageIMC);
            }
        }

        return averageIMCByAgeRange;
    }

    public Map<String, Double> getAverageAgeByBloodType() {
        List<Object[]> result = candidateRepository.calculateAverageAgeByBloodType();
        Map<String, Double> averageAgeByBloodType = new HashMap<>();

        for (Object[] row : result) {
            String bloodType = (String) row[0];
            Double averageAge = ((Number) row[1]).doubleValue();
            averageAgeByBloodType.put(bloodType, averageAge);
        }

        return averageAgeByBloodType;
    }

    public Map<String, Long> getPossibleDonorsByBloodType() {
        List<Object[]> result = candidateRepository.countPossibleDonorsByBloodType();
        Map<String, Long> possibleDonorsByBloodType = new HashMap<>();

        for (Object[] row : result) {
            String bloodType = (String) row[0];
            Long count = ((Number) row[1]).longValue();
            possibleDonorsByBloodType.put(bloodType, count);
        }

        return possibleDonorsByBloodType;
    }
    
    public Double getPercentageOfObeseMen() {
        Long totalMen = candidateRepository.countBySexo("Masculino");
        Long totalObeseMen = candidateRepository.countBySexoAndIMCGreaterThan("Masculino", 30.0);

        if (totalMen == 0) {
            return 0.0;
        }

        return (double) (totalObeseMen * 100) / totalMen;
    }
    
    public Double getPercentageOfObeseWomen() {
        Long totalWomen = candidateRepository.countBySexo("Feminino");
        Long totalObeseWomen = candidateRepository.countBySexoAndIMCGreaterThan("Feminino", 30.0);

        if (totalWomen == 0) {
            return 0.0;
        }

        return (double) (totalObeseWomen * 100) / totalWomen;
    }
}
