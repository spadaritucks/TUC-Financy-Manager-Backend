package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.futureTransactions.FutureTransactionRequestDTO;
import com.tucfinancymanager.backend.DTOs.futureTransactions.FutureTransactionResponseDTO;
import com.tucfinancymanager.backend.services.FutureTransactionService;
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
@RequestMapping("/futuretransactions")
@Tag(name = "Futuras Transações", description = "Informações das Futuras Transações")
public class FutureTransactionController {

    @Autowired
    private FutureTransactionService futureTransactionService;

    @GetMapping
    @Operation(summary = "Listagem de todas as futuras transações", description = "Essa função é responsável por listar todos as futuras transações")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de futuras transações retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = FutureTransactionResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<FutureTransactionResponseDTO>> getAllFutureTransactions(@RequestParam int page , @RequestParam int size) {
        var result = this.futureTransactionService.getAllFutureTransactions(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/by-user")
    @Operation(summary = "Listagem de todas as futuras transações de um usuario", description = "Essa função é responsável por listar todos as futuras transações feitas por um usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de futuras transações retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = FutureTransactionResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<FutureTransactionResponseDTO>> getFutureTransactionsByUserId(@RequestParam UUID userId, @RequestParam int page , @RequestParam int size) {
        var result = this.futureTransactionService.getFutureTransactionsByUserId(userId, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @PostMapping
    @Operation(summary = "Cadastro de futuras transações", description = "Essa função é responsável por cadastrar a futura transação")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = FutureTransactionResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "404", description = "O usuario não existe"),
            @ApiResponse(responseCode = "404", description = "A subcategoria não existe")
    })
    public ResponseEntity<FutureTransactionResponseDTO> createFutureTransaction(@Valid @RequestBody FutureTransactionRequestDTO futureTransactionRequestDTO) {
        var result = this.futureTransactionService.createFutureTransaction(futureTransactionRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclusão da futura transação", description = "Essa função é responsável por excluir a futura transação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = FutureTransactionResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A futura transação não existe")
    })
    public ResponseEntity<FutureTransactionResponseDTO> deleteFutureTransaction(@PathVariable UUID id) {
        var result = this.futureTransactionService.deleteFutureTransaction(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
