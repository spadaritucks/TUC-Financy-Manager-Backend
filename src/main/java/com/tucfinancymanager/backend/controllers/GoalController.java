package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.goal.GoalCountDTO;
import com.tucfinancymanager.backend.DTOs.goal.GoalRequestDTO;
import com.tucfinancymanager.backend.DTOs.goal.GoalRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.goal.GoalResponseDTO;
import com.tucfinancymanager.backend.DTOs.pagination.PageResponseDTO;
import com.tucfinancymanager.backend.ENUMs.GoalStatus;
import com.tucfinancymanager.backend.services.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/goals")
@Tag(name = "Metas", description = "Informações das Metas")
public class GoalController {

        @Autowired
        private GoalService goalService;

        @GetMapping("/by-user")
        @Operation(summary = "Listagem de todas as metas de um usuario", description = "Essa função é responsável por listar todos as metas feitas por um usuario")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de metas retornada com sucesso", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GoalResponseDTO.class)))
                        })
        })
        public ResponseEntity<PageResponseDTO<GoalResponseDTO>> getGoalsByUserId(
                        @RequestParam UUID userId,
                        @RequestParam(required = false) String subcategory,
                        @RequestParam(required = false) String goalName,
                        @RequestParam(required = false) String goalStatus,
                        @RequestParam int page,
                        @RequestParam int size) {
                var result = this.goalService.getGoalsByUserId(userId,subcategory,goalName,goalStatus, page, size);
                return new ResponseEntity<>(result, HttpStatus.OK);
        }

        @GetMapping("/count-goals")
        @Operation(summary = "Contagem das Metas do usuario de acordo com o status", description = "Essa função é responsável por retornar a quantia de metas do usuario de acordo com o status")
        public ResponseEntity<GoalCountDTO> getGoalsCountByStatus(@RequestParam UUID userId) {
                var result = this.goalService.getGoalsCountByStatus(userId);
                return new ResponseEntity<>(result, HttpStatus.OK);
        }

        @PostMapping
        @Operation(summary = "Cadastro de metas", description = "Essa função é responsável por cadastrar a meta")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", content = {
                                        @Content(schema = @Schema(implementation = GoalResponseDTO.class))
                        }),
                        @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
                        @ApiResponse(responseCode = "404", description = "O usuario não existe"),
                        @ApiResponse(responseCode = "404", description = "A subcategoria não existe")
        })
        public ResponseEntity<GoalResponseDTO> createGoal(@Valid @RequestBody GoalRequestDTO goalRequestDTO) {
                var result = this.goalService.createGoal(goalRequestDTO);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
        }

        @RequestMapping(value = "/{id}", method = { RequestMethod.PUT, RequestMethod.PATCH })
        @Operation(summary = "Atualização do status da meta", description = "Essa função é responsável por atualizar o status da meta")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = GoalResponseDTO.class))
                        }),
                        @ApiResponse(responseCode = "404", description = "A meta não existe")
        })
        public ResponseEntity<GoalResponseDTO> updateGoalStatus(@PathVariable UUID id,
                        @RequestBody GoalRequestUpdateDTO goalRequestUpdateDTO) {
                var result = this.goalService.updateGoalStatus(id, goalRequestUpdateDTO);
                return new ResponseEntity<>(result, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Exclusão da meta", description = "Essa função é responsável por excluir a meta")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = GoalResponseDTO.class))
                        }),
                        @ApiResponse(responseCode = "404", description = "A meta não existe")
        })
        public ResponseEntity<GoalResponseDTO> deleteGoal(@PathVariable UUID id) {
                var result = this.goalService.deleteGoal(id);
                return new ResponseEntity<>(result, HttpStatus.OK);
        }

}
