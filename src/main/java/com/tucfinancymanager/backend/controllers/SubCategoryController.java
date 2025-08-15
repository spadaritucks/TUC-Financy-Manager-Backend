package com.tucfinancymanager.backend.controllers;



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



    @GetMapping
    @Operation(summary = "Listagem de todas as subcategorias do usuario", description = "Essa função é responsável por listar todas as subcategorias do usuário autenticado sem paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de subcategorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = SubCategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<SubCategoryResponseDTO>> getUserAllSubCategories(@RequestParam UUID userId) {
        var result = this.subCategoryService.getAllSubCategoriesByUserId(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Cadastro da minha subcategoria", description = "Essa função é responsável por cadastrar a subcategoria para o usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "409", description = "A subcategoria já existe para este usuário"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada para este usuário")
    })
    public ResponseEntity<SubCategoryResponseDTO> createUserSubCategory(
            @RequestParam UUID userId,
            @Valid @RequestBody SubCategoryRequestDTO subCategoryRequestDTO) {
        // Sobrescrever o userId com o usuário autenticado
       
        var result = this.subCategoryService.createSubCategory(subCategoryRequestDTO, userId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Operation(summary = "Atualização da minha subcategoria", description = "Essa função é responsável por atualizar a subcategoria do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A subcategoria não existe para este usuário")
    })
    public ResponseEntity<SubCategoryResponseDTO> updateUserSubCategory(
            @PathVariable UUID id,
            @RequestParam UUID userId,
            @RequestBody SubCategoryRequestUpdateDTO subCategoryRequestUpdateDTO) {
        var result = this.subCategoryService.updateUserSubCategory(id, userId, subCategoryRequestUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclusão da minha subcategoria", description = "Essa função é responsável por excluir a subcategoria do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A subcategoria não existe para este usuário")
    })
    public ResponseEntity<SubCategoryResponseDTO> deleteUserSubCategory(
            @PathVariable UUID id, 
            @RequestParam UUID userId) {
        var result = this.subCategoryService.deleteUserSubCategory(id, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
