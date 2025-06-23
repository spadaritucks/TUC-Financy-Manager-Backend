package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionRequestDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionResponseDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionStatusDTO;
import com.tucfinancymanager.backend.services.TransactionService;
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
@RequestMapping("/transactions")
@Tag(name = "Transações", description = "Informações das Transações")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Listagem de todas as transações", description = "Essa função é responsável por listar todos as transações")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de transações retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TransactionResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions(@RequestParam int page , @RequestParam int size) {
        var result = this.transactionService.getAllTransactions(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/by-user")
    @Operation(summary = "Listagem de todas as transações de um usuario", description = "Essa função é responsável por listar todos as transações feitas por um usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de transações retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TransactionResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByUserId(@RequestParam UUID userId, @RequestParam int page , @RequestParam int size) {
        var result = this.transactionService.getTransactionsByUserId(userId, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @PostMapping
    @Operation(summary = "Cadastro de transações", description = "Essa função é responsável por cadastrar a transação")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = TransactionResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "404", description = "O usuario não existe"),
            @ApiResponse(responseCode = "404", description = "A subcategoria não existe")
    })
    public ResponseEntity<TransactionResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        var result = this.transactionService.createTransaction(transactionRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Operation(summary = "Atualização do status da transação", description = "Essa função é responsável por atualizar o status da transação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = TransactionResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A transação não existe")
    })
    public ResponseEntity<TransactionResponseDTO> updateTransactionStatus( @PathVariable UUID id, @RequestBody  TransactionStatusDTO transactionStatusDTO) {
        var result = this.transactionService.updateTransactionStatus(id, transactionStatusDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclusão da transação", description = "Essa função é responsável por excluir a transação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = TransactionResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A transação não existe")
    })
    public ResponseEntity<TransactionResponseDTO> deleteTransaction(@PathVariable UUID id) {
        var result = this.transactionService.deleteTransaction(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
