package com.savchuk.thirdLab.service;

import com.savchuk.thirdLab.constans.ErrorMessage;
import com.savchuk.thirdLab.dto.PlantDto;
import com.savchuk.thirdLab.dto.PlantRequestDto;
import com.savchuk.thirdLab.entity.EntityStatus;
import com.savchuk.thirdLab.entity.Plant;
import com.savchuk.thirdLab.repository.InstructionRepository;
import com.savchuk.thirdLab.repository.PlantRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantService {
    private final PlantRepository plantRepository;
    private final InstructionRepository instructionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PlantService(PlantRepository plantRepository, InstructionRepository instructionRepository, ModelMapper modelMapper) {
        this.plantRepository = plantRepository;
        this.instructionRepository = instructionRepository;
        this.modelMapper = modelMapper;
    }

    public List<Plant> findAll() {
        return plantRepository.findAll().stream().filter
                (e -> e.getStatus().equals(EntityStatus.ACTIVE.getStatus())).collect(Collectors.toList());
    }

    public Plant findOneById(Long id) {
        return plantRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.NO_SUCH_OWNER_FOUND_BY_ID + " " + id));
    }

    public void delete(Long id) throws NotFoundException {
        if (!plantRepository.findById(id).isPresent()) {
            throw new NotFoundException(ErrorMessage.NOT_DELETED_BY_OWNER_ID + " " + id);
        }
        plantRepository.changeStatus(id, EntityStatus.NONACTIVE.getStatus());
    }

    public void create(PlantDto plantDto) throws NotFoundException {
        Plant newPlant = modelMapper.map(plantDto, Plant.class);
        if (!instructionRepository.findById(plantDto.getInstructionID()).isPresent())
        {
            throw new NotFoundException(ErrorMessage.NO_SUCH_INSTRUCTION_FOUND_BY_ID + " " + plantDto.getInstructionID());
        }
        newPlant.setInstruction(instructionRepository.findById(plantDto.getInstructionID()).get());
        plantRepository.save(newPlant);
    }

    public void update(Long plantID, PlantRequestDto plantRequestDto) throws NotFoundException {
        if (!plantRepository.findById(plantID).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_PLANT_FOUND_BY_ID + " " + plantID);
        }
        Plant newPlant = new Plant();
        newPlant.setId(plantID);
        newPlant.setType(plantRequestDto.getType());
        newPlant.setLocation(plantRequestDto.getLocation());
        plantRepository.save(newPlant);
    }

}
