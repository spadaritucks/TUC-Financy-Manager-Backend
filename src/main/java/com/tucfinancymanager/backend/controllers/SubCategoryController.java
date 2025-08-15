package com.tucfinancymanager.backend.controllers;


import com.tucfinancymanager.backend.DTOs.pagination.PageResponseDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryRequestDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryResponseDTO;
import com.tucfinancymanager.backend.services.SubCategoryService;
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
@RequestMapping("/subcategories")
@Tag(name = "Subcategorias", description = "Informações das Subcategorias")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    // Endpoints que usam o usuário autenticado automaticamente
    @GetMapping("/my")
    @Operation(summary = "Listagem das minhas subcategorias", description = "Essa função é responsável por listar todas as subcategorias do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de subcategorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = SubCategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<PageResponseDTO<SubCategoryResponseDTO>> getMySubCategories(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "20") int size) {
        var result = this.subCategoryService.getAllSubCategoriesByUserId(userId, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/my/all")
    @Operation(summary = "Listagem de todas as minhas subcategorias sem paginação", description = "Essa função é responsável por listar todas as subcategorias do usuário autenticado sem paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de subcategorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = SubCategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<SubCategoryResponseDTO>> getMySubCategoriesWithoutPagination(@RequestParam UUID userId) {
        var result = this.subCategoryService.getAllSubCategoriesByUserId(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/my")
    @Operation(summary = "Cadastro da minha subcategoria", description = "Essa função é responsável por cadastrar a subcategoria para o usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "409", description = "A subcategoria já existe para este usuário"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada para este usuário")
    })
    public ResponseEntity<SubCategoryResponseDTO> createMySubCategory(
            @RequestParam UUID userId,
            @Valid @RequestBody SubCategoryRequestDTO subCategoryRequestDTO) {
        // Sobrescrever o userId com o usuário autenticado
        subCategoryRequestDTO.setUserId(userId);
        var result = this.subCategoryService.createSubCategory(subCategoryRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/my/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Operation(summary = "Atualização da minha subcategoria", description = "Essa função é responsável por atualizar a subcategoria do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A subcategoria não existe para este usuário")
    })
    public ResponseEntity<SubCategoryResponseDTO> updateMySubCategory(
            @PathVariable UUID id,
            @RequestParam UUID userId,
            @RequestBody SubCategoryRequestUpdateDTO subCategoryRequestUpdateDTO) {
        var result = this.subCategoryService.updateSubCategory(id, userId, subCategoryRequestUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/my/{id}")
    @Operation(summary = "Exclusão da minha subcategoria", description = "Essa função é responsável por excluir a subcategoria do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A subcategoria não existe para este usuário")
    })
    public ResponseEntity<SubCategoryResponseDTO> deleteMySubCategory(
            @PathVariable UUID id, 
            @RequestParam UUID userId) {
        var result = this.subCategoryService.deleteSubCategory(id, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Endpoints que permitem especificar o userId (para administradores ou casos especiais)
    @GetMapping("/user/{userId}")
    @Operation(summary = "Listagem de subcategorias por usuário", description = "Essa função é responsável por listar todas as subcategorias de um usuário específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de subcategorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = SubCategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<PageResponseDTO<SubCategoryResponseDTO>> getAllSubCategoriesByUserId(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "20") int size) {
        var result = this.subCategoryService.getAllSubCategoriesByUserId(userId, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/all")
    @Operation(summary = "Listagem de todas as subcategorias por usuário sem paginação", description = "Essa função é responsável por listar todas as subcategorias de um usuário específico sem paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de subcategorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = SubCategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<SubCategoryResponseDTO>> getAllSubCategoriesByUserIdWithoutPagination(@PathVariable UUID userId) {
        var result = this.subCategoryService.getAllSubCategoriesByUserId(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Cadastro da subcategoria", description = "Essa função é responsável por cadastrar a subcategoria para um usuário específico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "409", description = "A subcategoria já existe para este usuário"),
            @ApiResponse(responseCode = "404", description = "Usuário ou categoria não encontrada para este usuário")
    })
    public ResponseEntity<SubCategoryResponseDTO> createSubCategory(@Valid @RequestBody SubCategoryRequestDTO subCategoryRequestDTO) {
        var result = this.subCategoryService.createSubCategory(subCategoryRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}/user/{userId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Operation(summary = "Atualização da subcategoria", description = "Essa função é responsável por atualizar a subcategoria de um usuário específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A subcategoria não existe para este usuário")
    })
    public ResponseEntity<SubCategoryResponseDTO>  updateSubCategory(
            @PathVariable UUID id,
            @PathVariable UUID userId,
            @RequestBody SubCategoryRequestUpdateDTO subCategoryRequestUpdateDTO) {
        var result = this.subCategoryService.updateSubCategory(id, userId, subCategoryRequestUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/user/{userId}")
    @Operation(summary = "Exclusão da subcategoria", description = "Essa função é responsável por excluir a subcategoria de um usuário específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A subcategoria não existe para este usuário")
    })
    public ResponseEntity<SubCategoryResponseDTO>  deleteSubCategory(
            @PathVariable UUID id, 
            @PathVariable UUID userId) {
        var result = this.subCategoryService.deleteSubCategory(id, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
