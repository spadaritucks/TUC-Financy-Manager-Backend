package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.category.CategoryRequestDTO;
import com.tucfinancymanager.backend.DTOs.category.CategoryRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.user.UserResponseDTO;
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
    @Operation(summary = "Listagem de todas as categorias", description = "Essa função é responsável por listar todos as categorias")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(@RequestParam int page, @RequestParam int size) {
        var result = this.categoryService.getAllCategory(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Cadastro da categoria", description = "Essa função é responsável por cadastrar a categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "409", description = "A categoria já existe no sistema")
    })
    public ResponseEntity<CategoryResponseDTO> createCategory (@RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        var result = this.categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Operation(summary = "Atualização da categoria", description = "Essa função é responsável por atualizar a categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A categoria não existe")
    })
    public ResponseEntity<CategoryResponseDTO> updateCategory (@PathVariable UUID id, @RequestBody CategoryRequestUpdateDTO categoryRequestUpdateDTO) {
        var result = this.categoryService.updateCategory(id, categoryRequestUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclusão da categoria", description = "Essa função é responsável por excluir a categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A categoria não existe")
    })
    public ResponseEntity<CategoryResponseDTO> deleteCategory (@PathVariable UUID id) {
        var result = this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
