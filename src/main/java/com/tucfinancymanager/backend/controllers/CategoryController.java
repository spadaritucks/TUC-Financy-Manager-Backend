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

    // Endpoints que usam o usuário autenticado automaticamente
    @GetMapping("/my")
    @Operation(summary = "Listagem das minhas categorias", description = "Essa função é responsável por listar todas as categorias do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<CategoryResponseDTO>> getMyCategories(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "20") int size) {
        var result = this.categoryService.getAllCategoryByUserId(userId, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/my/all")
    @Operation(summary = "Listagem de todas as minhas categorias sem paginação", description = "Essa função é responsável por listar todas as categorias do usuário autenticado sem paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<CategoryResponseDTO>> getMyCategoriesWithoutPagination(@RequestParam UUID userId) {
        var result = this.categoryService.getAllCategoryByUserId(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/my")
    @Operation(summary = "Cadastro da minha categoria", description = "Essa função é responsável por cadastrar a categoria para o usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "409", description = "A categoria já existe para este usuário")
    })
    public ResponseEntity<CategoryResponseDTO> createMyCategory (
            @RequestParam UUID userId,
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        // Sobrescrever o userId com o usuário autenticado
        categoryRequestDTO.setUserId(userId);
        var result = this.categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/my/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Operation(summary = "Atualização da minha categoria", description = "Essa função é responsável por atualizar a categoria do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A categoria não existe para este usuário")
    })
    public ResponseEntity<CategoryResponseDTO> updateMyCategory (
            @PathVariable UUID id, 
            @RequestParam UUID userId,
            @RequestBody CategoryRequestUpdateDTO categoryRequestUpdateDTO) {
        var result = this.categoryService.updateCategory(id, userId, categoryRequestUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/my/{id}")
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

    // Endpoints que permitem especificar o userId (para administradores ou casos especiais)
    @GetMapping("/user/{userId}")
    @Operation(summary = "Listagem de categorias por usuário", description = "Essa função é responsável por listar todas as categorias de um usuário específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategoriesByUserId(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "20") int size) {
        var result = this.categoryService.getAllCategoryByUserId(userId, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/all")
    @Operation(summary = "Listagem de todas as categorias por usuário sem paginação", description = "Essa função é responsável por listar todas as categorias de um usuário específico sem paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategoriesByUserIdWithoutPagination(@PathVariable UUID userId) {
        var result = this.categoryService.getAllCategoryByUserId(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Cadastro da categoria", description = "Essa função é responsável por cadastrar a categoria para um usuário específico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "409", description = "A categoria já existe para este usuário"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<CategoryResponseDTO> createCategory (@RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        var result = this.categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}/user/{userId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Operation(summary = "Atualização da categoria", description = "Essa função é responsável por atualizar a categoria de um usuário específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A categoria não existe para este usuário")
    })
    public ResponseEntity<CategoryResponseDTO> updateCategory (
            @PathVariable UUID id, 
            @PathVariable UUID userId,
            @RequestBody CategoryRequestUpdateDTO categoryRequestUpdateDTO) {
        var result = this.categoryService.updateCategory(id, userId, categoryRequestUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/user/{userId}")
    @Operation(summary = "Exclusão da categoria", description = "Essa função é responsável por excluir a categoria de um usuário específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A categoria não existe para este usuário")
    })
    public ResponseEntity<CategoryResponseDTO> deleteCategory (
            @PathVariable UUID id, 
            @PathVariable UUID userId) {
        var result = this.categoryService.deleteCategory(id, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
