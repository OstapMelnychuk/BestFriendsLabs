package com.savchuk.thirdLab.service;

import com.savchuk.thirdLab.constans.ErrorMessage;
import com.savchuk.thirdLab.dto.InstructionRequestDto;
import com.savchuk.thirdLab.dto.OwnerRequestDto;
import com.savchuk.thirdLab.entity.EntityStatus;
import com.savchuk.thirdLab.entity.Instruction;
import com.savchuk.thirdLab.entity.Owner;
import com.savchuk.thirdLab.repository.InstructionRepository;
import com.savchuk.thirdLab.repository.OwnerRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final InstructionRepository instructionRepository;
    private final InstructionService instructionService;
    private final ModelMapper modelMapper;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, InstructionRepository instructionRepository,
                        InstructionService instructionService, ModelMapper modelMapper) {
        this.ownerRepository = ownerRepository;
        this.instructionRepository = instructionRepository;
        this.instructionService = instructionService;
        this.modelMapper = modelMapper;
    }

    public List<Owner> findAll() {
        return ownerRepository.findAll().stream().filter
                (e -> e.getStatus().equals(EntityStatus.ACTIVE.getStatus())).collect(Collectors.toList());
    }

    public Owner findOneById(Long id) {
        return ownerRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.NO_SUCH_OWNER_FOUND_BY_ID + " " + id));
    }

    public void delete(Long id) throws NotFoundException {
        if (!ownerRepository.findById(id).isPresent()) {
            throw new NotFoundException(ErrorMessage.NOT_DELETED_BY_OWNER_ID + " " + id);
        }
        ownerRepository.changeStatus(id, EntityStatus.NONACTIVE.getStatus());
    }

    public void create(OwnerRequestDto ownerRequestDto) {
        Owner newOwner = modelMapper.map(ownerRequestDto, Owner.class);
        ownerRepository.save(newOwner);
    }

    public void update(Long ownerID, OwnerRequestDto ownerRequestDto) throws NotFoundException {
        if (!ownerRepository.findById(ownerID).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_OWNER_FOUND_BY_ID + " " + ownerID);
        }
        Owner oldOwner = ownerRepository.findById(ownerID).get();
        Owner newOwner = new Owner();
        newOwner.setId(ownerID);
        newOwner.setName(ownerRequestDto.getName());
        newOwner.setNameOfPark(ownerRequestDto.getNameOfPark());
        newOwner.setDescription(ownerRequestDto.getDescription());
        newOwner.setPassword(ownerRequestDto.getPassword());
        newOwner.setSecondName(ownerRequestDto.getSecondName());
        if (!oldOwner.getInstructions().isEmpty()) {
            newOwner.getInstructions().addAll(oldOwner.getInstructions());
        }
        ownerRepository.save(newOwner);
    }

    public void createNewInstruction(InstructionRequestDto instructionRequestDto) throws NotFoundException {
        instructionService.create(instructionRequestDto);
    }

    public void submitResults(Long instructionID) throws NotFoundException {
        if (!instructionRepository.findById(instructionID).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_INSTRUCTION_FOUND_BY_ID + " " + instructionID);
        }
        Instruction instruction = instructionRepository.findById(instructionID).get();
        instruction.setStatus(EntityStatus.NONACTIVE.getStatus());
        instructionRepository.save(instruction);
    }

    public void unApproveResults(Long instructionID) throws NotFoundException {
        if (!instructionRepository.findById(instructionID).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_INSTRUCTION_FOUND_BY_ID + " " + instructionID);
        }
        Instruction instruction = instructionRepository.findById(instructionID).get();
        instruction.setStatus(EntityStatus.ERROR.getStatus());
        instructionRepository.save(instruction);
    }

    public Instruction checkResults(Long instructionID) throws NotFoundException {
        if (!instructionRepository.findById(instructionID).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_INSTRUCTION_FOUND_BY_ID + " " + instructionID);
        }
        return instructionRepository.findById(instructionID).get();
    }
    public List<Instruction> checkAllResults(Long ownerID) throws NotFoundException {
        return instructionService.checkAllResults(ownerID);
    }
}
