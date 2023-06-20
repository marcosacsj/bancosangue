package br.com.example.bancosangue.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import br.com.example.bancosangue.entity.Candidate;
import br.com.example.bancosangue.service.CandidateService;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("/api/candidates")
@Api(tags = "Candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping("/import")
    @ApiOperation(value = "Importar candidatos", notes = "Importa uma lista de candidatos a partir de um arquivo JSON.")
    public ResponseEntity<String> importCandidates(@RequestParam("file") MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            List<Candidate> candidates = objectMapper.readValue(file.getInputStream(), new TypeReference<List<Candidate>>() {
            });

            candidateService.importCandidates(candidates);
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/states")
    @ApiOperation(value = "Contagem de candidatos por estado", notes = "Retorna a contagem de candidatos agrupados por estado.")
    public Map<String, Long> getCandidatesByState() {
        return candidateService.getCandidatesByState();
    }

    @GetMapping("/imc-avg-by-age")
    @ApiOperation(value = "Média de IMC por faixa etária", notes = "Retorna a média do Índice de Massa Corporal (IMC) dos candidatos por faixa etária.")
    public Map<String, Double> getAverageIMCByAgeRange() {
        return candidateService.getAverageIMCByAgeRange();
    }

    @GetMapping("/obese-percentage-men")
    @ApiOperation(value = "Percentual de homens obesos", notes = "Retorna o percentual de homens obesos entre os candidatos.")
    public Double getPercentageOfObeseMen() {
        return candidateService.getPercentageOfObeseMen();
    }

    @GetMapping("/obese-percentage-women")
    @ApiOperation(value = "Percentual de mulheres obesas", notes = "Retorna o percentual de mulheres obesas entre os candidatos.")
    public Double getPercentageOfObeseWomen() {
        return candidateService.getPercentageOfObeseWomen();
    }

    @GetMapping("/avg-age-by-blood-type")
    @ApiOperation(value = "Média de idade por tipo sanguíneo", notes = "Retorna a média de idade dos candidatos agrupados por tipo sanguíneo.")
    public Map<String, Double> getAverageAgeByBloodType() {
        return candidateService.getAverageAgeByBloodType();
    }

    @GetMapping("/possible-donors-by-blood-type")
    @ApiOperation(value = "Contagem de possíveis doadores por tipo sanguíneo", notes = "Retorna a contagem de possíveis doadores agrupados por tipo sanguíneo.")
    public Map<String, Long> getPossibleDonorsByBloodType() {
        Map<String, Long> possibleDonorsByBloodType = candidateService.getPossibleDonorsByBloodType();
        Map<String, Long> sanitizedMap = new HashMap<>();
        for (Map.Entry<String, Long> entry : possibleDonorsByBloodType.entrySet()) {
            String key = entry.getKey() != null ? entry.getKey() : "";
            Long value = entry.getValue();
            sanitizedMap.put(key, value);
        }

        return sanitizedMap;
    }
}
