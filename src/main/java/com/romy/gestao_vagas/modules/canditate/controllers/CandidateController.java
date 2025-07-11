package com.romy.gestao_vagas.modules.canditate.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.romy.gestao_vagas.modules.canditate.dto.ProfileCandidateResponseDTO;
import com.romy.gestao_vagas.modules.canditate.entity.CandidateEntity;
import com.romy.gestao_vagas.modules.canditate.useCase.ApplyJobUseCase;
import com.romy.gestao_vagas.modules.canditate.useCase.CreateCandidateUseCase;
import com.romy.gestao_vagas.modules.canditate.useCase.ListAllJobsByFilterUseCase;
import com.romy.gestao_vagas.modules.canditate.useCase.ProfileCandidateUseCase;
import com.romy.gestao_vagas.modules.company.entities.JobEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Candidate Management")
public class CandidateController {
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobUseCase applyJobUseCase;
    

    @Operation(summary = "Create Candidate", description = "Create a new candidate profile")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CandidateEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "User already exists")
    })
    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity){
        try {
            var candidate = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(candidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Get Candidate Profile", description = "Retrieve the profile of the authenticated candidate")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "User not Found")
    })
    public ResponseEntity<Object> get(HttpServletRequest request) {

        var candidateId = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase
            .execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Get Jobs by Filter", description = "Retrieve jobs based on a specific filter")
    @ApiResponses(
        @ApiResponse(responseCode = "200", content = {
            @Content(
                array = @ArraySchema(
                    schema = @Schema(implementation = JobEntity.class)
                )   
            )
        })
    )
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getJobsByFilter(@RequestParam String filter) {
        try {
            var jobs = this.listAllJobsByFilterUseCase.execute(filter);
            return ResponseEntity.ok().body(jobs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Apply Job", description = "Apply a job to the authenticated candidate")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID jobId) {

        var candidateId = request.getAttribute("candidate_id");

        try {
            var job = this.applyJobUseCase.execute(UUID.fromString(candidateId.toString()), jobId);
            return ResponseEntity.ok().body(job);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

}
