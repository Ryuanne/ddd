import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionEncoder;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint8;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class TradeContract extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040526000600760006101000a81548160ff02191690831515021790555034801561002b57600080fd5b5060405160608061101e833981018060405281019080805190602001909291908051906020019092919080519060200190929190505050826000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555081600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550806002819055506064600a82028115156100f957fe5b04600381905550505050610f0c806101126000396000f3006080604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063083c4d2c146100bf5780632202a44e14610105578063419759f5146101355780635c274b86146101605780636da8a3a81461016a5780636f9fb98a146101a35780637b7e231b146101ce57806387a967431461025f5780639a20004f1461028f578063a5ff7651146102ba578063d811fcf014610311578063f240f7c314610368575b600080fd5b3480156100cb57600080fd5b50610103600480360381019080803560ff169060200190929190803515159060200190929190803590602001909291905050506103bd565b005b34801561011157600080fd5b50610133600480360381019080803560ff1690602001909291905050506105c9565b005b34801561014157600080fd5b5061014a6105f2565b6040518082815260200191505060405180910390f35b6101686105f8565b005b34801561017657600080fd5b506101a160048036038101908080359060200190929190803515159060200190929190505050610846565b005b3480156101af57600080fd5b506101b861094d565b6040518082815260200191505060405180910390f35b3480156101da57600080fd5b506101e361096c565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828152602001935050505060405180910390f35b34801561026b57600080fd5b5061028d600480360381019080803560ff1690602001909291905050506109c4565b005b34801561029b57600080fd5b506102a4610b74565b6040518082815260200191505060405180910390f35b3480156102c657600080fd5b506102cf610b7a565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561031d57600080fd5b50610326610ba0565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561037457600080fd5b5061037d610bc5565b6040518084600281111561038d57fe5b60ff1681526020018360018111156103a157fe5b60ff168152602001828152602001935050505060405180910390f35b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806104655750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b15156104ff576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260348152602001807f4f6e6c7920696e766f6c76656420706172746965732063616e2063686f6f736581526020017f206172626974726174696f6e206d6574686f642e00000000000000000000000081525060400191505060405180910390fd5b82600860000160016101000a81548160ff0219169083600181111561052057fe5b02179055506000600181111561053257fe5b83600181111561053e57fe5b141561057d5781610550576002610553565b60015b600860000160006101000a81548160ff0219169083600281111561057357fe5b02179055506105bc565b8060086001018190555081610593576002610596565b60015b600860000160006101000a81548160ff021916908360028111156105b657fe5b02179055505b6105c4610bf7565b505050565b80600860000160006101000a81548160ff021916908360028111156105ea57fe5b021790555050565b60035481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806106a05750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b151561073a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001807f4f6e6c79206661726d6572206f72206d65726368616e742063616e206465706f81526020017f736974000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b600354341415156107d9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260318152602001807f4465706f736974206d7573742062652065786163746c7920313025206f66207481526020017f68652064656c697665727920707269636500000000000000000000000000000081525060400191505060405180910390fd5b7ff1953715a33b9e021c0f2cf12911e8ac25fdb177bf6fc92d1331ae05201fe9f63334604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a1565b60018081111561085257fe5b600860000160019054906101000a900460ff16600181111561087057fe5b14151561090b576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001807f4172626974726174696f6e2074797065206973206e6f74207075626c6963207681526020017f6f7465000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b8160086001018190555080610921576002610924565b60015b600860000160006101000a81548160ff0219169083600281111561094457fe5b02179055505050565b60003073ffffffffffffffffffffffffffffffffffffffff1631905090565b60048060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020154905083565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161480610a6c5750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b1515610b06576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260308152602001807f4f6e6c79206661726d6572206f72206d65726368616e742063616e20696e697481526020017f69617465206172626974726174696f6e0000000000000000000000000000000081525060400191505060405180910390fd5b80600860000160016101000a81548160ff02191690836001811115610b2757fe5b02179055507f469751ee6186e4402060aa667d2781cc94d35b71445ac27c19347654f0761ce38160405180826001811115610b5e57fe5b60ff16815260200191505060405180910390a150565b60025481565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60088060000160009054906101000a900460ff16908060000160019054906101000a900460ff16908060010154905083565b600080600080925060009150600260035402905060016002811115610c1857fe5b600860000160009054906101000a900460ff166002811115610c3657fe5b1415610c9057600180811115610c4857fe5b600860000160019054906101000a900460ff166001811115610c6657fe5b1415610c8757610c7a600860010154610e52565b8094508193505050610c8b565b8091505b610d11565b600280811115610c9c57fe5b600860000160009054906101000a900460ff166002811115610cba57fe5b1415610d1057600180811115610ccc57fe5b600860000160019054906101000a900460ff166001811115610cea57fe5b1415610d0b57610cfe600860010154610e52565b8093508194505050610d0f565b8092505b5b5b6000831115610d83576000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc849081150290604051600060405180830381858888f19350505050158015610d81573d6000803e3d6000fd5b505b6000821115610df657600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f19350505050158015610df4573d6000803e3d6000fd5b505b7f1d815b948ecb3f2c196e1482e5c3dcaf77806c7ae3b48a063d8185f9a253c1bf600860000160009054906101000a900460ff1660405180826002811115610e3a57fe5b60ff16815260200191505060405180910390a1505050565b600080600060026003540290506050841115610e7057809250610ed4565b603284101515610ea3576064605a8202811515610e8957fe5b0492506064600a8202811515610e9b57fe5b049150610ed3565b601e84101515610ed2576064604b8202811515610ebc57fe5b049250606460198202811515610ece57fe5b0491505b5b5b828292509250509150915600a165627a7a723058200cf30ec8","b88f2b2128c50455350181efb7ea2ab1e0b92dd1f26f1d956942b24e0029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"_arbitrationType\",\"type\":\"uint8\"},{\"name\":\"_isFarmerFault\",\"type\":\"bool\"},{\"name\":\"_publicVotePercentage\",\"type\":\"uint256\"}],\"name\":\"chooseArbitrationAndSubmitResult\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_result\",\"type\":\"uint8\"}],\"name\":\"setArbitrationResult\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"depositAmount\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"payDeposit\",\"outputs\":[],\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_publicVotePercentage\",\"type\":\"uint256\"},{\"name\":\"_isFarmerFault\",\"type\":\"bool\"}],\"name\":\"setPublicVoteResult\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getContractBalance\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"tradeInfo\",\"outputs\":[{\"name\":\"farmerAddress\",\"type\":\"address\"},{\"name\":\"merchantAddress\",\"type\":\"address\"},{\"name\":\"deliveryPrice\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_arbitrationType\",\"type\":\"uint8\"}],\"name\":\"startArbitration\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"deliveryPrice\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"merchant\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"farmer\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"dispute\",\"outputs\":[{\"name\":\"result\",\"type\":\"uint8\"},{\"name\":\"arbitrationType\",\"type\":\"uint8\"},{\"name\":\"publicVotePercentage\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"name\":\"_farmer\",\"type\":\"address\"},{\"name\":\"_merchant\",\"type\":\"address\"},{\"name\":\"_deliveryPrice\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"farmer\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"FarmerPaid\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"depositor\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"DepositPaid\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"arbitrationType\",\"type\":\"uint8\"}],\"name\":\"ArbitrationStarted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"result\",\"type\":\"uint8\"}],\"name\":\"DisputeResolved\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_CHOOSEARBITRATIONANDSUBMITRESULT = "chooseArbitrationAndSubmitResult";

    public static final String FUNC_SETARBITRATIONRESULT = "setArbitrationResult";

    public static final String FUNC_DEPOSITAMOUNT = "depositAmount";

    public static final String FUNC_PAYDEPOSIT = "payDeposit";

    public static final String FUNC_SETPUBLICVOTERESULT = "setPublicVoteResult";

    public static final String FUNC_GETCONTRACTBALANCE = "getContractBalance";

    public static final String FUNC_TRADEINFO = "tradeInfo";

    public static final String FUNC_STARTARBITRATION = "startArbitration";

    public static final String FUNC_DELIVERYPRICE = "deliveryPrice";

    public static final String FUNC_MERCHANT = "merchant";

    public static final String FUNC_FARMER = "farmer";

    public static final String FUNC_DISPUTE = "dispute";

    public static final Event FARMERPAID_EVENT = new Event("FarmerPaid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DEPOSITPAID_EVENT = new Event("DepositPaid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ARBITRATIONSTARTED_EVENT = new Event("ArbitrationStarted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
    ;

    public static final Event DISPUTERESOLVED_EVENT = new Event("DisputeResolved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
    ;

    protected TradeContract(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt chooseArbitrationAndSubmitResult(BigInteger _arbitrationType, Boolean _isFarmerFault, BigInteger _publicVotePercentage) {
        final Function function = new Function(
                FUNC_CHOOSEARBITRATIONANDSUBMITRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_arbitrationType), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_isFarmerFault), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_publicVotePercentage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void chooseArbitrationAndSubmitResult(BigInteger _arbitrationType, Boolean _isFarmerFault, BigInteger _publicVotePercentage, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CHOOSEARBITRATIONANDSUBMITRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_arbitrationType), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_isFarmerFault), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_publicVotePercentage)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForChooseArbitrationAndSubmitResult(BigInteger _arbitrationType, Boolean _isFarmerFault, BigInteger _publicVotePercentage) {
        final Function function = new Function(
                FUNC_CHOOSEARBITRATIONANDSUBMITRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_arbitrationType), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_isFarmerFault), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_publicVotePercentage)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<BigInteger, Boolean, BigInteger> getChooseArbitrationAndSubmitResultInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CHOOSEARBITRATIONANDSUBMITRESULT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<BigInteger, Boolean, BigInteger>(

                (BigInteger) results.get(0).getValue(), 
                (Boolean) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue()
                );
    }

    public TransactionReceipt setArbitrationResult(BigInteger _result) {
        final Function function = new Function(
                FUNC_SETARBITRATIONRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_result)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void setArbitrationResult(BigInteger _result, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETARBITRATIONRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_result)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetArbitrationResult(BigInteger _result) {
        final Function function = new Function(
                FUNC_SETARBITRATIONRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_result)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getSetArbitrationResultInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETARBITRATIONRESULT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public BigInteger depositAmount() throws ContractException {
        final Function function = new Function(FUNC_DEPOSITAMOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt payDeposit() {
        final Function function = new Function(
                FUNC_PAYDEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void payDeposit(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_PAYDEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForPayDeposit() {
        final Function function = new Function(
                FUNC_PAYDEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public TransactionReceipt setPublicVoteResult(BigInteger _publicVotePercentage, Boolean _isFarmerFault) {
        final Function function = new Function(
                FUNC_SETPUBLICVOTERESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_publicVotePercentage), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_isFarmerFault)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void setPublicVoteResult(BigInteger _publicVotePercentage, Boolean _isFarmerFault, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETPUBLICVOTERESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_publicVotePercentage), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_isFarmerFault)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetPublicVoteResult(BigInteger _publicVotePercentage, Boolean _isFarmerFault) {
        final Function function = new Function(
                FUNC_SETPUBLICVOTERESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_publicVotePercentage), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_isFarmerFault)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<BigInteger, Boolean> getSetPublicVoteResultInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETPUBLICVOTERESULT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<BigInteger, Boolean>(

                (BigInteger) results.get(0).getValue(), 
                (Boolean) results.get(1).getValue()
                );
    }

    public BigInteger getContractBalance() throws ContractException {
        final Function function = new Function(FUNC_GETCONTRACTBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public Tuple3<String, String, BigInteger> tradeInfo() throws ContractException {
        final Function function = new Function(FUNC_TRADEINFO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple3<String, String, BigInteger>(
                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue());
    }

    public TransactionReceipt startArbitration(BigInteger _arbitrationType) {
        final Function function = new Function(
                FUNC_STARTARBITRATION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_arbitrationType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void startArbitration(BigInteger _arbitrationType, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_STARTARBITRATION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_arbitrationType)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForStartArbitration(BigInteger _arbitrationType) {
        final Function function = new Function(
                FUNC_STARTARBITRATION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(_arbitrationType)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getStartArbitrationInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_STARTARBITRATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public BigInteger deliveryPrice() throws ContractException {
        final Function function = new Function(FUNC_DELIVERYPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public String merchant() throws ContractException {
        final Function function = new Function(FUNC_MERCHANT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public String farmer() throws ContractException {
        final Function function = new Function(FUNC_FARMER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public Tuple3<BigInteger, BigInteger, BigInteger> dispute() throws ContractException {
        final Function function = new Function(FUNC_DISPUTE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                (BigInteger) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue());
    }

    public List<FarmerPaidEventResponse> getFarmerPaidEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FARMERPAID_EVENT, transactionReceipt);
        ArrayList<FarmerPaidEventResponse> responses = new ArrayList<FarmerPaidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FarmerPaidEventResponse typedResponse = new FarmerPaidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.farmer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeFarmerPaidEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(FARMERPAID_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeFarmerPaidEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(FARMERPAID_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<DepositPaidEventResponse> getDepositPaidEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSITPAID_EVENT, transactionReceipt);
        ArrayList<DepositPaidEventResponse> responses = new ArrayList<DepositPaidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositPaidEventResponse typedResponse = new DepositPaidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.depositor = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeDepositPaidEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(DEPOSITPAID_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeDepositPaidEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(DEPOSITPAID_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<ArbitrationStartedEventResponse> getArbitrationStartedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ARBITRATIONSTARTED_EVENT, transactionReceipt);
        ArrayList<ArbitrationStartedEventResponse> responses = new ArrayList<ArbitrationStartedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ArbitrationStartedEventResponse typedResponse = new ArbitrationStartedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.arbitrationType = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeArbitrationStartedEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(ARBITRATIONSTARTED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeArbitrationStartedEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(ARBITRATIONSTARTED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<DisputeResolvedEventResponse> getDisputeResolvedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DISPUTERESOLVED_EVENT, transactionReceipt);
        ArrayList<DisputeResolvedEventResponse> responses = new ArrayList<DisputeResolvedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DisputeResolvedEventResponse typedResponse = new DisputeResolvedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.result = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeDisputeResolvedEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(DISPUTERESOLVED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeDisputeResolvedEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(DISPUTERESOLVED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static TradeContract load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new TradeContract(contractAddress, client, credential);
    }

    public static TradeContract deploy(Client client, CryptoKeyPair credential, String _farmer, String _merchant, BigInteger _deliveryPrice) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_farmer), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_merchant), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_deliveryPrice)));
        return deploy(TradeContract.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
    }

    public static class FarmerPaidEventResponse {
        public TransactionReceipt.Logs log;

        public String farmer;

        public BigInteger amount;
    }

    public static class DepositPaidEventResponse {
        public TransactionReceipt.Logs log;

        public String depositor;

        public BigInteger amount;
    }

    public static class ArbitrationStartedEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger arbitrationType;
    }

    public static class DisputeResolvedEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger result;
    }
}
