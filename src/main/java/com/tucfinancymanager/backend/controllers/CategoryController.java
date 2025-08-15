package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.category.CategoryRequestDTO;
import com.tucfinancymanager.backend.DTOs.category.CategoryRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.services.CategoryService;
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
@RequestMapping("/categories")
@Tag(name = "Categorias", description = "Informações das Categorias")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @GetMapping
    @Operation(summary = "Listagem de todas as categorias do usuario", description = "Essa função é responsável por listar todas as categorias do usuário autenticado sem paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<CategoryResponseDTO>> getUserAllCategories(@RequestParam UUID userId) {
        var result = this.categoryService.getUserAllCategories(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Cadastro da categoria do usuario", description = "Essa função é responsável por cadastrar a categoria para o usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "409", description = "A categoria já existe para este usuário")
    })
    public ResponseEntity<CategoryResponseDTO> createUserCategory (
            @RequestParam UUID userId,
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        
        categoryRequestDTO.setUserId(userId);
        var result = this.categoryService.createUserCategory(categoryRequestDTO, userId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Operation(summary = "Atualização da minha categoria", description = "Essa função é responsável por atualizar a categoria do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A categoria não existe para este usuário")
    })
    public ResponseEntity<CategoryResponseDTO> updateUserCategory (
            @PathVariable UUID id, 
            @RequestParam UUID userId,
            @RequestBody CategoryRequestUpdateDTO categoryRequestUpdateDTO) {
        var result = this.categoryService.updateUserCategory(id, userId, categoryRequestUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclusão da minha categoria", description = "Essa função é responsável por excluir a categoria do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A categoria não existe para este usuário")
    })
    public ResponseEntity<CategoryResponseDTO> deleteMyCategory (
            @PathVariable UUID id, 
            @RequestParam UUID userId) {
        var result = this.categoryService.deleteCategory(id, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
