package com.savchuk.thirdLab.service;

import com.savchuk.thirdLab.constans.ErrorMessage;
import com.savchuk.thirdLab.dto.ForesterRequestDto;
import com.savchuk.thirdLab.dto.PlantDto;
import com.savchuk.thirdLab.dto.PlantRequestDto;
import com.savchuk.thirdLab.entity.EntityStatus;
import com.savchuk.thirdLab.entity.Forester;
import com.savchuk.thirdLab.entity.Instruction;
import com.savchuk.thirdLab.entity.Plant;
import com.savchuk.thirdLab.repository.ForesterRepository;
import com.savchuk.thirdLab.repository.InstructionRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForesterService {
    private final ForesterRepository foresterRepository;
    private final InstructionRepository instructionRepository;
    private final PlantService plantService;
    private final InstructionService instructionService;
    private final ModelMapper modelMapper;

    @Autowired
    public ForesterService(ForesterRepository foresterRepository, InstructionRepository instructionRepository,
                           PlantService plantService, InstructionService instructionService, ModelMapper modelMapper) {
        this.foresterRepository = foresterRepository;
        this.instructionRepository = instructionRepository;
        this.plantService = plantService;
        this.instructionService = instructionService;
        this.modelMapper = modelMapper;
    }

    public List<Forester> findAll() {
        return foresterRepository.findAll().stream().filter
                (e -> e.getStatus().equals(EntityStatus.ACTIVE.getStatus())).collect(Collectors.toList());
    }

    public Forester findOneById(Long id) {
        return foresterRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.NO_SUCH_FORESTER_FOUND_BY_ID + " " + id));
    }

    public void delete(Long id) throws NotFoundException {
        if (!foresterRepository.findById(id).isPresent()) {
            throw new NotFoundException(ErrorMessage.NOT_DELETED_BY_FORESTER_ID + " " + id);
        }
        foresterRepository.changeStatus(id, EntityStatus.NONACTIVE.getStatus());
    }

    public void create(ForesterRequestDto foresterRequestDto) {
        Forester newForester = modelMapper.map(foresterRequestDto, Forester.class);
        foresterRepository.save(newForester);
    }

    public void update(Long foresterID, ForesterRequestDto foresterRequestDto) throws NotFoundException {
        if (!foresterRepository.findById(foresterID).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_FORESTER_FOUND_BY_ID + " " + foresterID);
        }
        Forester oldForester = foresterRepository.findById(foresterID).get();
        Forester newForester = new Forester();
        newForester.setId(foresterID);
        newForester.setName(foresterRequestDto.getName());
        newForester.setDateOfEmployment(foresterRequestDto.getDateOfEmployment());
        newForester.setDescription(foresterRequestDto.getDescription());
        newForester.setPassword(foresterRequestDto.getPassword());
        newForester.setSecondName(foresterRequestDto.getSecondName());
        newForester.setInstructions(oldForester.getInstructions());
        foresterRepository.save(newForester);
    }

    public List<Plant> findAllPlants() {
        return plantService.findAll();
    }

    public Plant findOnePlantById(Long id) {
        return plantService.findOneById(id);
    }

    public void deletePlant(Long id) throws NotFoundException {
        plantService.delete(id);
    }

    public void createPlant(PlantDto plantDto) throws NotFoundException {
        plantService.create(plantDto);
    }

    public void updatePlant(Long plantID, PlantRequestDto plantRequestDto) throws NotFoundException {
        plantService.update(plantID, plantRequestDto);
    }

    public List<Instruction> getAllInstructions(Long foresterID) throws NotFoundException {
        return instructionService.getAllInstructions(foresterID);
    }

    public void updateInstruction(Long instructionID, String answerOfForester) throws NotFoundException {
        if (!instructionRepository.findById(instructionID).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_INSTRUCTION_FOUND_BY_ID + " " + instructionID);
        }
        Instruction newInstruction = instructionService.findOneById(instructionID);
        newInstruction.setAnswerOfForester(answerOfForester);
        instructionRepository.save(newInstruction);
    }
}
