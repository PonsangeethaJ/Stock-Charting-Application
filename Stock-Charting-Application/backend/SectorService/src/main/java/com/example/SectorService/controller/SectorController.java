package com.example.SectorService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SectorService.dto.CompanyDto;
import com.example.SectorService.dto.SectorDto;
import com.example.SectorService.exception.SectorNotFoundException;
import com.example.SectorService.service.SectorService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/sectors")
public class SectorController
{
    @Autowired
    private SectorService sectorService;

    @GetMapping(path = "")
    public ResponseEntity<List<SectorDto>> findAll() {
        return ResponseEntity.ok(sectorService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SectorDto> findById(@PathVariable String id)
            throws SectorNotFoundException
    {
        SectorDto sectorDto = sectorService.findById(id);
        if(sectorDto == null) {
            throw new SectorNotFoundException("Sector not found for id : " + id);
        }
        return ResponseEntity.ok(sectorDto);
    }

    @PostMapping(path = "")
    public ResponseEntity<SectorDto> save(@RequestBody SectorDto sectorDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sectorService.save(sectorDto));
    }

    @PutMapping(path = "")
    public ResponseEntity<SectorDto> update(@RequestBody SectorDto sectorDto)
            throws SectorNotFoundException
    {
        SectorDto updatedSectorDto = sectorService.save(sectorDto);
        if(updatedSectorDto == null) {
            throw new SectorNotFoundException("Sector not found for id : " + sectorDto.getId());
        }
        return ResponseEntity.ok(updatedSectorDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable String id) {
        sectorService.deleteById(id);
    }

    @GetMapping(path = "/{id}/companies")
    public ResponseEntity<List<CompanyDto>> getCompanies(@PathVariable String id)
            throws SectorNotFoundException
    {
        List<CompanyDto> companyDtos = sectorService.getCompanies(id);
        if(companyDtos == null) {
            throw new SectorNotFoundException("Sector not found for id : " + id);
        }
        return ResponseEntity.ok(companyDtos);
    }

    /* Feign Client Mapping */

    @PostMapping(path = "/{sectorName}/companies")
    public void addCompanyToSector(@PathVariable String sectorName, @RequestBody CompanyDto companyDto)
            throws SectorNotFoundException
    {
        SectorDto sectorDto = sectorService.addCompanyToSector(sectorName, companyDto);
        if(sectorDto == null) {
            throw new SectorNotFoundException("Sector not found with name : " + sectorName);
        }
    }
}

