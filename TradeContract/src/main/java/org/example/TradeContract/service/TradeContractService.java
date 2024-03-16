package org.example.TradeContract.service;

import java.lang.Exception;
import java.lang.String;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.TradeContract.model.bo.TradeContractChooseArbitrationAndSubmitResultInputBO;
import org.example.TradeContract.model.bo.TradeContractSetArbitrationResultInputBO;
import org.example.TradeContract.model.bo.TradeContractSetPublicVoteResultInputBO;
import org.example.TradeContract.model.bo.TradeContractStartArbitrationInputBO;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class TradeContractService {
  public static final String ABI = org.example.TradeContract.utils.IOUtil.readResourceAsString("abi/TradeContract.abi");

  public static final String BINARY = org.example.TradeContract.utils.IOUtil.readResourceAsString("bin/ecc/TradeContract.bin");

  public static final String SM_BINARY = org.example.TradeContract.utils.IOUtil.readResourceAsString("bin/sm/TradeContract.bin");

  @Value("${system.contract.tradeContractAddress}")
  private String address;

  @Autowired
  private Client client;

  AssembleTransactionProcessor txProcessor;

  @PostConstruct
  public void init() throws Exception {
    this.txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, this.client.getCryptoSuite().getCryptoKeyPair());
  }

  public TransactionResponse chooseArbitrationAndSubmitResult(TradeContractChooseArbitrationAndSubmitResultInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "chooseArbitrationAndSubmitResult", input.toArgs());
  }

  public CallResponse dispute() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "dispute", Arrays.asList());
  }

  public CallResponse getContractBalance() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "getContractBalance", Arrays.asList());
  }

  public CallResponse tradeInfo() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "tradeInfo", Arrays.asList());
  }

  public CallResponse merchant() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "merchant", Arrays.asList());
  }

  public CallResponse depositAmount() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "depositAmount", Arrays.asList());
  }

  public TransactionResponse setPublicVoteResult(TradeContractSetPublicVoteResultInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "setPublicVoteResult", input.toArgs());
  }

  public TransactionResponse setArbitrationResult(TradeContractSetArbitrationResultInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "setArbitrationResult", input.toArgs());
  }

  public TransactionResponse payDeposit() throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "payDeposit", Arrays.asList());
  }

  public CallResponse farmer() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "farmer", Arrays.asList());
  }

  public TransactionResponse startArbitration(TradeContractStartArbitrationInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "startArbitration", input.toArgs());
  }

  public CallResponse deliveryPrice() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "deliveryPrice", Arrays.asList());
  }
}
