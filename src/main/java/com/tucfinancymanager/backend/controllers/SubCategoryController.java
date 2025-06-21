package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryRequestDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.user.UserResponseDTO;
import com.tucfinancymanager.backend.services.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Operation(summary = "Listagem de todas as subcategorias", description = "Essa função é responsável por listar todos as subcategorias")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de subcategorias retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = SubCategoryResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<SubCategoryResponseDTO>> getAllSubCategories(@RequestParam int page, @RequestParam int size) {
        var result = this.subCategoryService.getAllSubCategories(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Cadastro da subcategoria", description = "Essa função é responsável por cadastrar a subcategoria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "409", description = "A subcategoria já existe no sistema"),
            @ApiResponse(responseCode = "404", description = "A categoria já existe no sistema")
    })
    public ResponseEntity<SubCategoryResponseDTO> createSubCategory(@RequestBody SubCategoryRequestDTO subCategoryRequestDTO) {
        var result = this.subCategoryService.createSubCategory(subCategoryRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Operation(summary = "Atualização da subcategoria", description = "Essa função é responsável por atualizar a subcategoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A subcategoria não existe")
    })
    public ResponseEntity<SubCategoryResponseDTO>  updateSubCategory(@PathVariable UUID id, @RequestBody SubCategoryRequestUpdateDTO subCategoryRequestUpdateDTO) {
        var result = this.subCategoryService.updateSubCategory(id,subCategoryRequestUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclusão da subcategoria", description = "Essa função é responsável por excluir a subcategoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = SubCategoryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "A subcategoria não existe")
    })
    public ResponseEntity<SubCategoryResponseDTO>  deleteSubCategory(@PathVariable UUID id) {
        var result = this.subCategoryService.deleteSubCategory(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
