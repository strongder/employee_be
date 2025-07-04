package com.example.employee.service.impl;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.CreateContractRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.ContractResponse;
import com.example.employee.exception.AppException;
import com.example.employee.exception.ErrorResponse;
import com.example.employee.model.Contract;
import com.example.employee.repository.AccountRepository;
import com.example.employee.repository.ContractRepository;
import com.example.employee.repository.EmployeeProfileRepository;
import com.example.employee.service.ContractService;
import com.example.employee.service.FileService;
import com.example.employee.utils.SearchUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ContractServiceImpl extends BaseServiceImpl<Contract, Long> implements ContractService {

    @Autowired
    ContractRepository contractRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SearchUtils<Contract> searchUtils;
    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;
    @Autowired
    private FileService fileService;

    public ContractServiceImpl(ContractRepository repository) {
        super(repository);
        this.contractRepository = repository;
    }

    @Override
    protected <Req> Contract convertToEntity(Req request) {
        CreateContractRequest contractRequest = (CreateContractRequest) request;
        Contract contract = modelMapper.map(contractRequest, Contract.class);
        contract.setEmployee(employeeProfileRepository.findByCode(contractRequest.getEmployeeCode())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
        return contract;
    }

    @Override
    protected <Req> Contract convertToEntity(Req request, Contract existingEntity) {
        return null;
    }

    @Override
    protected <Res> Res convertToResponse(Contract entity, Class<Res> responseType) {
        return modelMapper.map(entity, responseType);
    }

    @Override
    @Transactional
    public ContractResponse create(CreateContractRequest request) throws IOException {
        Contract contract = convertToEntity(request);
        contract.setContractCode(generationContractCode());
        Contract saveContract = contractRepository.save(contract);
        if(request.getContractFile()!= null && !request.getContractFile().isEmpty())
            uploadFileAsync(request.getContractFile(), saveContract.getId());
        return convertToResponse(saveContract, ContractResponse.class);
    }

    @Override
    @Transactional
    public ContractResponse update(Long id, CreateContractRequest request) throws IOException {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setContractType(request.getContractType());
        contract.setContractStatus(request.getContractStatus());
        contract.setMonthlySalary(request.getMonthlySalary());
        // Nếu người dùng upload file mới thì lưu lại và thay thế
        if (request.getContractFile() != null && !request.getContractFile().isEmpty()) {
            uploadFileAsync(request.getContractFile(), contract.getId());
        }
        contractRepository.save(contract);
        return convertToResponse(contract, ContractResponse.class);
    }

    @Override
    public Long softDelete(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        contract.setDeleted(true);
        contractRepository.save(contract);
        return id;
    }

    @Override
    public BaseSearchResponse<ContractResponse> search(BaseSearchRequest request, Class<ContractResponse> responseType) {
        return searchUtils.search(Contract.class, request, contract -> convertToResponse(contract, responseType));
    }
    public String generationContractCode()
    {
        LocalDate now = LocalDate.now();
        String datePart = now.format(DateTimeFormatter.ofPattern("yyyy"));
        // Đếm số nhân viên đã tạo trong tháng hiện tại
        long countThisMonth = employeeProfileRepository.countByMonth(now.getYear());
        long nextNumber = countThisMonth + 1;
        return String.format("HD%s%04d", datePart, nextNumber); // Ví dụ: EMP20240003
    }

    @Async
    public void uploadFileAsync(MultipartFile file, Long contractId) {
        try {
            String fileName = fileService.uploadFile(file); // upload file bình thường
            Contract contract = contractRepository.findById(contractId)
                    .orElseThrow(() -> new RuntimeException("Not found"));
            contract.setContractUrl(fileName);
            contractRepository.save(contract);
        } catch (IOException e) {
            // log error
        }
    }

    @Override
    public List<ContractResponse> getContractByEmployeeId(Long employeeId) {
        List<Contract> list = contractRepository.findByEmployeeId(employeeId);
        return list.stream().map(
                contract -> convertToResponse(contract, ContractResponse.class)
        ).toList();
    }

    @Override
    public Object findAll()
    {
        List<Contract> lists = contractRepository.findContractsByDeletedFalse();
        return lists.stream()
                .map(contract -> convertToResponse(contract, ContractResponse.class))
                .toList();
    }

    //scheduled moi ngay chuyen hop dong neu qua ngay thi chuyen trang thai EXPIRED

     // CHAYJ MOI GIO CHA
     @Scheduled(cron = "0 0 0 * * ?") // Mỗi ngày lúc 00:00
     public void updateExpiredContracts() {
         LocalDate today = LocalDate.now();
         List<Contract> contracts = contractRepository.findByEndDateBeforeAndContractStatus(today, "ACTIVE");
         for (Contract contract : contracts) {
             contract.setContractStatus("EXPIRED");
             contractRepository.save(contract);
         }
     }
}
