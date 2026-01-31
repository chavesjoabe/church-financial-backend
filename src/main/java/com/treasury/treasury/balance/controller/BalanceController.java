package com.treasury.treasury.balance.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.treasury.treasury.balance.dto.AccountingReportItemV2Dto;
import com.treasury.treasury.balance.dto.BalanceDto;
import com.treasury.treasury.balance.schema.Balance;
import com.treasury.treasury.balance.service.BalanceService;

@RestController
@RequestMapping("api/balance")
@CrossOrigin(origins = "*")
public class BalanceController {

  private final BalanceService balanceService;

  public BalanceController(BalanceService balanceService) {
    this.balanceService = balanceService;
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    String response = "HEALTH - OK";
    return ResponseEntity.ok(response);
  }

  @PostMapping("/create")
  public ResponseEntity<Balance> create(
      @RequestBody BalanceDto balanceDto,
      @RequestAttribute("loggedUserDocument") String loggedUserDocument,
      @RequestAttribute("loggedUserRole") String loggedUserRole,
      @RequestAttribute("loggedUserName") String loggedUserName) {

    Balance balance = this.balanceService.create(balanceDto, loggedUserDocument, loggedUserRole, loggedUserName);
    return new ResponseEntity<Balance>(balance, HttpStatus.CREATED);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Balance>> findAll() {
    List<Balance> balances = this.balanceService.findAll();
    return new ResponseEntity<List<Balance>>(balances, HttpStatus.OK);
  }

  @GetMapping("/all/date")
  public ResponseEntity<List<Balance>> findAllByDate(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestAttribute("loggedUserDocument") String loggedUserDocument,
      @RequestAttribute("loggedUserRole") String loggedUserRole) {

    Instant balanceStartDate = Instant.parse(startDate);
    Instant balanceEndDate = Instant.parse(endDate);

    List<Balance> balances = this.balanceService.findAllByDate(
        balanceStartDate,
        balanceEndDate,
        loggedUserDocument,
        loggedUserRole);

    return new ResponseEntity<List<Balance>>(balances, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<Balance> findById(@PathVariable String id) {
    Balance balance = this.balanceService.findById(id);
    return new ResponseEntity<Balance>(balance, HttpStatus.OK);
  }

  @PutMapping("/approve/{id}")
  public ResponseEntity<Balance> approveBalance(
      @PathVariable String id,
      @RequestAttribute("loggedUserDocument") String loggedUserDocument,
      @RequestAttribute("loggedUserRole") String loggedUserRole) {
    Balance balance = this.balanceService.approveBalance(id, loggedUserDocument, loggedUserRole);
    return new ResponseEntity<Balance>(balance, HttpStatus.OK);
  }

  @PutMapping("/reject/{id}")
  public ResponseEntity<Balance> rejectBalance(
      @PathVariable String id,
      @RequestAttribute("loggedUserDocument") String loggedUserDocument,
      @RequestAttribute("loggedUserRole") String loggedUserRole) {
    Balance balance = this.balanceService.rejectBalance(id, loggedUserDocument, loggedUserRole);
    return new ResponseEntity<Balance>(balance, HttpStatus.OK);
  }

  @PutMapping("/remove/{id}")
  public ResponseEntity<Balance> removeBalance(
      @PathVariable String id,
      @RequestAttribute("loggedUserDocument") String loggedUserDocument,
      @RequestAttribute("loggedUserRole") String loggedUserRole) {
    Balance balance = this.balanceService.removeBalance(id, loggedUserDocument, loggedUserRole);
    return new ResponseEntity<Balance>(balance, HttpStatus.OK);
  }

  @GetMapping("/pending")
  public ResponseEntity<List<Balance>> findAllPendingBalances(
      @RequestAttribute("loggedUserDocument") String loggedUserDocument,
      @RequestAttribute("loggedUserRole") String loggedUserRole) {

    List<Balance> balances = this.balanceService.findAllPendingBalances(loggedUserDocument, loggedUserRole);
    return new ResponseEntity<>(balances, HttpStatus.OK);
  }

  @GetMapping("/report/accounting")
  public ResponseEntity<AccountingReportItemV2Dto> extractAccountingReportV2(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate) {
    Instant balanceStartDate = Instant.parse(startDate);
    Instant balanceEndDate = Instant.parse(endDate);
    AccountingReportItemV2Dto response = this.balanceService
        .extractAccountingReportV2(
            balanceStartDate,
            balanceEndDate);

    return new ResponseEntity<AccountingReportItemV2Dto>(response, HttpStatus.OK);
  }

  @GetMapping("/report/incoming_outgoing")
  public ResponseEntity<List<Balance>> findAllBalancesByDate(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate) {
    Instant balanceStartDate = Instant.parse(startDate);
    Instant balanceEndDate = Instant.parse(endDate);
    List<Balance> balances = this.balanceService
        .findAllApprovedBalancesByDate(
            balanceStartDate,
            balanceEndDate);

    return new ResponseEntity<List<Balance>>(balances, HttpStatus.OK);
  }

  @GetMapping("/report/incoming")
  public ResponseEntity<List<Balance>> findAllIncomingBalancesByDate(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate) {
    Instant balanceStartDate = Instant.parse(startDate);
    Instant balanceEndDate = Instant.parse(endDate);
    List<Balance> balances = this.balanceService
        .findAllIncomingBalancesByDate(
            balanceStartDate,
            balanceEndDate);

    return new ResponseEntity<List<Balance>>(balances, HttpStatus.OK);
  }

  @GetMapping("/report/outgoing")
  public ResponseEntity<List<Balance>> findAllOutgoingBalancesByDate(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate) {
    Instant balanceStartDate = Instant.parse(startDate);
    Instant balanceEndDate = Instant.parse(endDate);
    List<Balance> balances = this.balanceService
        .findAllOutgoingBalancesByDate(
            balanceStartDate,
            balanceEndDate);

    return new ResponseEntity<List<Balance>>(balances, HttpStatus.OK);
  }

  @PostMapping("create/ofx")
  public ResponseEntity<String> createByOfxFile(
      @RequestParam("file") MultipartFile file,
      @RequestAttribute("loggedUserDocument") String loggedUserDocument,
      @RequestAttribute("loggedUserName") String loggedUserName) {
    if (file.isEmpty()) {
      System.out.println("EMPTY FILE");
      return ResponseEntity.badRequest().body(null);
    }

    String fileName = file.getOriginalFilename();

    int lastDotIndex = fileName.lastIndexOf(".");

    String fileExtension = fileName.substring(lastDotIndex + 1);

    System.out.println(fileExtension);
    Boolean isInvalidFileExtension = !fileExtension.toLowerCase().equalsIgnoreCase("ofx");

    System.out.println(isInvalidFileExtension);

    if (isInvalidFileExtension) {
      System.out.println("INCOMPATIBLE FILE TYPE");
      return ResponseEntity.badRequest().body(null);
    }

    balanceService.processBalancesByOfxFile(file, loggedUserName, loggedUserDocument);

    return ResponseEntity.ok("OK");
  }
}
