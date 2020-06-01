package com.savchuk.thirdLab.service;

import com.savchuk.thirdLab.constans.ErrorMessage;
import com.savchuk.thirdLab.dto.InstructionRequestDto;
import com.savchuk.thirdLab.entity.EntityStatus;
import com.savchuk.thirdLab.entity.Instruction;
import com.savchuk.thirdLab.repository.ForesterRepository;
import com.savchuk.thirdLab.repository.InstructionRepository;
import com.savchuk.thirdLab.repository.OwnerRepository;
import com.savchuk.thirdLab.repository.PlantRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructionService {
    private final OwnerRepository ownerRepository;
    private final ForesterRepository foresterRepository;
    private final InstructionRepository instructionRepository;
    private final PlantRepository plantRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public InstructionService(OwnerRepository ownerRepository, ForesterRepository foresterRepository,
                              InstructionRepository instructionRepository, PlantRepository plantRepository,
                              ModelMapper modelMapper) {
        this.ownerRepository = ownerRepository;
        this.foresterRepository = foresterRepository;
        this.instructionRepository = instructionRepository;
        this.plantRepository = plantRepository;
        this.modelMapper = modelMapper;
    }

    public List<Instruction> findAll() {
        return instructionRepository.findAll().stream().filter
                (e -> e.getStatus().equals(EntityStatus.ACTIVE.getStatus())).collect(Collectors.toList());
    }

    public Instruction findOneById(Long id) {
        return instructionRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.NO_SUCH_OWNER_FOUND_BY_ID + " " + id));
    }

    public void delete(Long id) throws NotFoundException {
        if (!instructionRepository.findById(id).isPresent()) {
            throw new NotFoundException(ErrorMessage.NOT_DELETED_BY_OWNER_ID + " " + id);
        }
        instructionRepository.changeStatus(id, EntityStatus.NONACTIVE.getStatus());
    }

    public void create(InstructionRequestDto instructionRequestDto) throws NotFoundException {
        if (!foresterRepository.findById(instructionRequestDto.getForesterID()).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_FORESTER_FOUND_BY_ID + " " + instructionRequestDto.getForesterID());
        }
        Instruction newInstruction = new Instruction();
        newInstruction.setForester(foresterRepository.findById(instructionRequestDto.getForesterID()).get());
        newInstruction.setPlants(new ArrayList<>());
        for (Long plantID : instructionRequestDto.getPlantsID()) {
            if (plantID == 0) {
                break;
            } else if (!plantRepository.findById(plantID).isPresent()) {
                throw new NotFoundException(ErrorMessage.NO_SUCH_PLANT_FOUND_BY_ID + " " + plantID);
            }

            newInstruction.getPlants().add(plantRepository.findById(plantID).get());
        }
        if (!ownerRepository.findById(instructionRequestDto.getOwnerID()).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_OWNER_FOUND_BY_ID + " " + instructionRequestDto.getForesterID());
        }
        newInstruction.setOwner(ownerRepository.findById(instructionRequestDto.getOwnerID()).get());
        newInstruction.setData(instructionRequestDto.getData());
        newInstruction.setName(instructionRequestDto.getName());
        newInstruction.setDescription(instructionRequestDto.getDescription());
        instructionRepository.save(newInstruction);
    }

    public void update(Long instructionID, InstructionRequestDto instructionRequestDto) throws NotFoundException {
        if (!instructionRepository.findById(instructionID).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_OWNER_FOUND_BY_ID + " " + instructionID);
        }
        Instruction oldInstruction = instructionRepository.findById(instructionID).get();
        Instruction newInstruction = new Instruction();
        newInstruction.setId(instructionID);
        newInstruction.setName(instructionRequestDto.getName());
        newInstruction.setData(instructionRequestDto.getData());
        newInstruction.setDescription(instructionRequestDto.getName());
        newInstruction.setForester(oldInstruction.getForester());
        newInstruction.setPlants(oldInstruction.getPlants());
        instructionRepository.save(newInstruction);
    }

    public List<Instruction> checkAllResults(Long ownerID) throws NotFoundException {
        if (!ownerRepository.findById(ownerID).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_OWNER_FOUND_BY_ID + " " + ownerID);
        }
        List<Instruction> allInstructions = new ArrayList<>();
        for (Instruction instruction : instructionRepository.findAll()) {
            if (instruction.getOwner().getId().equals(ownerID)) {
                allInstructions.add(instruction);
            }
        }
        return allInstructions;
    }

    public List<Instruction> getAllInstructions(Long foresterID) throws NotFoundException {
        if (!foresterRepository.findById(foresterID).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_FORESTER_FOUND_BY_ID + " " + foresterID);
        }
        List<Instruction> allInstructions = new ArrayList<>();
        for (Instruction instruction : instructionRepository.findAll()) {
            if (instruction.getForester().getId().equals(foresterID)) {
                allInstructions.add(instruction);
            }
        }
        return allInstructions;
    }
}
