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
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051606080610fff833981018060405281019080805190602001909291908051906020019092919080519060200190929190505050826000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555081600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550806002819055506064600a82028115156100de57fe5b04600381905550505050610f08806100f76000396000f3006080604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063419759f5146100bf5780634d3f4510146100ea5780635c274b86146101175780636da8a3a8146101215780636f9fb98a1461015a5780637b7e231b146101855780639a20004f14610216578063a11be5a914610241578063a5a26a5714610284578063a5ff7651146102b1578063d811fcf014610308578063f240f7c31461035f575b600080fd5b3480156100cb57600080fd5b506100d46103b4565b6040518082815260200191505060405180910390f35b3480156100f657600080fd5b50610115600480360381019080803590602001909291905050506103ba565b005b61011f6103ee565b005b34801561012d57600080fd5b506101586004803603810190808035906020019092919080351515906020019092919050505061063c565b005b34801561016657600080fd5b5061016f610743565b6040518082815260200191505060405180910390f35b34801561019157600080fd5b5061019a610762565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828152602001935050505060405180910390f35b34801561022257600080fd5b5061022b6107ba565b6040518082815260200191505060405180910390f35b34801561024d57600080fd5b5061028260048036038101908080359060200190929190803515159060200190929190803590602001909291905050506107c0565b005b34801561029057600080fd5b506102af600480360381019080803590602001909291905050506109cc565b005b3480156102bd57600080fd5b506102c6610b79565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561031457600080fd5b5061031d610b9f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561036b57600080fd5b50610374610bc4565b6040518084600281111561038457fe5b60ff16815260200183600181111561039857fe5b60ff168152602001828152602001935050505060405180910390f35b60035481565b8060028111156103c657fe5b600760000160006101000a81548160ff021916908360028111156103e657fe5b021790555050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806104965750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b1515610530576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001807f4f6e6c79206661726d6572206f72206d65726368616e742063616e206465706f81526020017f736974000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b600354341415156105cf576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260318152602001807f4465706f736974206d7573742062652065786163746c7920313025206f66207481526020017f68652064656c697665727920707269636500000000000000000000000000000081525060400191505060405180910390fd5b7ff1953715a33b9e021c0f2cf12911e8ac25fdb177bf6fc92d1331ae05201fe9f63334604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a1565b60018081111561064857fe5b600760000160019054906101000a900460ff16600181111561066657fe5b141515610701576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001807f4172626974726174696f6e2074797065206973206e6f74207075626c6963207681526020017f6f7465000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b816007600101819055508061071757600261071a565b60015b600760000160006101000a81548160ff0219169083600281111561073a57fe5b02179055505050565b60003073ffffffffffffffffffffffffffffffffffffffff1631905090565b60048060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020154905083565b60025481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806108685750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b1515610902576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260348152602001807f4f6e6c7920696e766f6c76656420706172746965732063616e2063686f6f736581526020017f206172626974726174696f6e206d6574686f642e00000000000000000000000081525060400191505060405180910390fd5b82600181111561090e57fe5b600760000160016101000a81548160ff0219169083600181111561092e57fe5b02179055506000600181111561094057fe5b8314156109805781610953576002610956565b60015b600760000160006101000a81548160ff0219169083600281111561097657fe5b02179055506109bf565b8060076001018190555081610996576002610999565b60015b600760000160006101000a81548160ff021916908360028111156109b957fe5b02179055505b6109c7610bf6565b505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161480610a745750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b1515610b0e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260308152602001807f4f6e6c79206661726d6572206f72206d65726368616e742063616e20696e697481526020017f69617465206172626974726174696f6e0000000000000000000000000000000081525060400191505060405180910390fd5b806001811115610b1a57fe5b600760000160016101000a81548160ff02191690836001811115610b3a57fe5b02179055507fe3d179e82725f4017e2cd7c543e789daec416599188407652080df0569557520816040518082815260200191505060405180910390a150565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60078060000160009054906101000a900460ff16908060000160019054906101000a900460ff16908060010154905083565b600080600080925060009150600260035402905060016002811115610c1757fe5b600760000160009054906101000a900460ff166002811115610c3557fe5b1415610c8f57600180811115610c4757fe5b600760000160019054906101000a900460ff166001811115610c6557fe5b1415610c8657610c79600760010154610e4e565b8094508193505050610c8a565b8091505b610d10565b600280811115610c9b57fe5b600760000160009054906101000a900460ff166002811115610cb957fe5b1415610d0f57600180811115610ccb57fe5b600760000160019054906101000a900460ff166001811115610ce957fe5b1415610d0a57610cfd600760010154610e4e565b8093508194505050610d0e565b8092505b5b5b6000831115610d82576000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc849081150290604051600060405180830381858888f19350505050158015610d80573d6000803e3d6000fd5b505b6000821115610df557600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f19350505050158015610df3573d6000803e3d6000fd5b505b7f57cce005d496355471bb5269f1dea3d6c81b6ac14c1d74ba28aefb022357013b600760000160009054906101000a900460ff166002811115610e3457fe5b6040518082815260200191505060405180910390a1505050565b600080600060026003540290506050841115610e6c57809250610ed0565b603284101515610e9f576064605a8202811515610e8557fe5b0492506064600a8202811515610e9757fe5b049150610ecf565b601e84101515610ece576064604b8202811515610eb857fe5b049250606460198202811515610eca57fe5b0491505b5b5b828292509250509150915600a165627a7a72305820851b4d239ac6b28cca3e13cba3419eb777f6ba74adf96cd4be87ff233d2edeeb0029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":true,\"inputs\":[],\"name\":\"depositAmount\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_result\",\"type\":\"uint256\"}],\"name\":\"setArbitrationResult\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"payDeposit\",\"outputs\":[],\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_publicVotePercentage\",\"type\":\"uint256\"},{\"name\":\"_isFarmerFault\",\"type\":\"bool\"}],\"name\":\"setPublicVoteResult\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getContractBalance\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"tradeInfo\",\"outputs\":[{\"name\":\"farmerAddress\",\"type\":\"address\"},{\"name\":\"merchantAddress\",\"type\":\"address\"},{\"name\":\"deliveryPrice\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"deliveryPrice\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_arbitrationType\",\"type\":\"uint256\"},{\"name\":\"_isFarmerFault\",\"type\":\"bool\"},{\"name\":\"_publicVotePercentage\",\"type\":\"uint256\"}],\"name\":\"chooseArbitrationAndSubmitResult\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_arbitrationType\",\"type\":\"uint256\"}],\"name\":\"startArbitration\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"merchant\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"farmer\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"dispute\",\"outputs\":[{\"name\":\"result\",\"type\":\"uint8\"},{\"name\":\"arbitrationType\",\"type\":\"uint8\"},{\"name\":\"publicVotePercentage\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"name\":\"_farmer\",\"type\":\"address\"},{\"name\":\"_merchant\",\"type\":\"address\"},{\"name\":\"_deliveryPrice\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"depositor\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"DepositPaid\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"arbitrationType\",\"type\":\"uint256\"}],\"name\":\"ArbitrationStarted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"result\",\"type\":\"uint256\"}],\"name\":\"DisputeResolved\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_DEPOSITAMOUNT = "depositAmount";

    public static final String FUNC_SETARBITRATIONRESULT = "setArbitrationResult";

    public static final String FUNC_PAYDEPOSIT = "payDeposit";

    public static final String FUNC_SETPUBLICVOTERESULT = "setPublicVoteResult";

    public static final String FUNC_GETCONTRACTBALANCE = "getContractBalance";

    public static final String FUNC_TRADEINFO = "tradeInfo";

    public static final String FUNC_DELIVERYPRICE = "deliveryPrice";

    public static final String FUNC_CHOOSEARBITRATIONANDSUBMITRESULT = "chooseArbitrationAndSubmitResult";

    public static final String FUNC_STARTARBITRATION = "startArbitration";

    public static final String FUNC_MERCHANT = "merchant";

    public static final String FUNC_FARMER = "farmer";

    public static final String FUNC_DISPUTE = "dispute";

    public static final Event DEPOSITPAID_EVENT = new Event("DepositPaid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ARBITRATIONSTARTED_EVENT = new Event("ArbitrationStarted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event DISPUTERESOLVED_EVENT = new Event("DisputeResolved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    protected TradeContract(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public BigInteger depositAmount() throws ContractException {
        final Function function = new Function(FUNC_DEPOSITAMOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt setArbitrationResult(BigInteger _result) {
        final Function function = new Function(
                FUNC_SETARBITRATIONRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_result)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void setArbitrationResult(BigInteger _result, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETARBITRATIONRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_result)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetArbitrationResult(BigInteger _result) {
        final Function function = new Function(
                FUNC_SETARBITRATIONRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_result)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getSetArbitrationResultInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETARBITRATIONRESULT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
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

    public BigInteger deliveryPrice() throws ContractException {
        final Function function = new Function(FUNC_DELIVERYPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt chooseArbitrationAndSubmitResult(BigInteger _arbitrationType, Boolean _isFarmerFault, BigInteger _publicVotePercentage) {
        final Function function = new Function(
                FUNC_CHOOSEARBITRATIONANDSUBMITRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_arbitrationType), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_isFarmerFault), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_publicVotePercentage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void chooseArbitrationAndSubmitResult(BigInteger _arbitrationType, Boolean _isFarmerFault, BigInteger _publicVotePercentage, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CHOOSEARBITRATIONANDSUBMITRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_arbitrationType), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_isFarmerFault), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_publicVotePercentage)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForChooseArbitrationAndSubmitResult(BigInteger _arbitrationType, Boolean _isFarmerFault, BigInteger _publicVotePercentage) {
        final Function function = new Function(
                FUNC_CHOOSEARBITRATIONANDSUBMITRESULT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_arbitrationType), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_isFarmerFault), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_publicVotePercentage)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<BigInteger, Boolean, BigInteger> getChooseArbitrationAndSubmitResultInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CHOOSEARBITRATIONANDSUBMITRESULT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<BigInteger, Boolean, BigInteger>(

                (BigInteger) results.get(0).getValue(), 
                (Boolean) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue()
                );
    }

    public TransactionReceipt startArbitration(BigInteger _arbitrationType) {
        final Function function = new Function(
                FUNC_STARTARBITRATION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_arbitrationType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void startArbitration(BigInteger _arbitrationType, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_STARTARBITRATION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_arbitrationType)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForStartArbitration(BigInteger _arbitrationType) {
        final Function function = new Function(
                FUNC_STARTARBITRATION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_arbitrationType)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getStartArbitrationInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_STARTARBITRATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
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
