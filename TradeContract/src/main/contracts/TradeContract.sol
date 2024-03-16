pragma solidity ^0.4.25;


contract TradeContract {


    address public farmer;


    address public merchant;


    uint public deliveryPrice;


    uint public depositAmount;


    enum ArbitrationType { Expert, PublicVote }


    enum DisputeResult { Unresolved, FarmerFault, MerchantFault }


    // 存储交易信息

    struct TradeInfo {

        address farmerAddress;

        address merchantAddress;

        uint deliveryPrice;

    }

    TradeInfo public tradeInfo;


    // 存储争议信息

    struct Dispute {

        DisputeResult result;

        ArbitrationType arbitrationType;

        uint publicVotePercentage;

    }

    Dispute public dispute;


    // 事件

    event DepositPaid(address depositor, uint amount);

    event ArbitrationStarted(uint arbitrationType);

    event DisputeResolved(uint result);


    // 构造函数

    constructor(address _farmer, address _merchant, uint _deliveryPrice) public {

        farmer = _farmer;

        merchant = _merchant;

        deliveryPrice = _deliveryPrice;

        depositAmount = _deliveryPrice * 10 / 100; // 计算10%的交割价格作为保证金

    }


    // 支付保证金

    function payDeposit() public payable {

        require(msg.sender == farmer || msg.sender == merchant, "Only farmer or merchant can deposit");

        require(msg.value == depositAmount, "Deposit must be exactly 10% of the delivery price");

        emit DepositPaid(msg.sender, msg.value);

    }


    // 开始裁决

    function startArbitration(uint _arbitrationType) external {

        require(msg.sender == farmer || msg.sender == merchant, "Only farmer or merchant can initiate arbitration");

        dispute.arbitrationType = ArbitrationType(_arbitrationType);

        emit ArbitrationStarted(_arbitrationType);

    }


    // 设置裁决结果

    function setArbitrationResult(uint _result) external {

        dispute.result = DisputeResult(_result);

    }


    // 根据公投结果设置裁决结果

    function setPublicVoteResult(uint _publicVotePercentage, bool _isFarmerFault) external {

        require(dispute.arbitrationType == ArbitrationType.PublicVote, "Arbitration type is not public vote");

        dispute.publicVotePercentage = _publicVotePercentage;

        dispute.result = _isFarmerFault ? DisputeResult.FarmerFault : DisputeResult.MerchantFault;

    }


    // 选择裁决方式和提交裁决结果

    function chooseArbitrationAndSubmitResult(uint _arbitrationType, bool _isFarmerFault, uint _publicVotePercentage) public {

        require(msg.sender == farmer || msg.sender == merchant, "Only involved parties can choose arbitration method.");

        dispute.arbitrationType = ArbitrationType(_arbitrationType);

        if(_arbitrationType == uint(ArbitrationType.Expert)) {

            dispute.result = _isFarmerFault ? DisputeResult.FarmerFault : DisputeResult.MerchantFault;

        } else {

            dispute.publicVotePercentage = _publicVotePercentage;

            dispute.result = _isFarmerFault ? DisputeResult.FarmerFault : DisputeResult.MerchantFault;

        }

        resolveDisputeBasedOnArbitration();

    }


    // 根据裁决类型解决争议

    function resolveDisputeBasedOnArbitration() private {

        uint refundFarmer = 0;

        uint refundMerchant = 0;

        uint totalDeposit = depositAmount * 2; // 每人10%


        if (dispute.result == DisputeResult.FarmerFault) {

            if (dispute.arbitrationType == ArbitrationType.PublicVote) {

                (refundMerchant, refundFarmer) = calculateRefund(dispute.publicVotePercentage);

            } else {

                // Expert decision, farmer at fault, merchant gets all

                refundMerchant = totalDeposit;

            }

        } else if (dispute.result == DisputeResult.MerchantFault) {

            if (dispute.arbitrationType == ArbitrationType.PublicVote) {

                // Calculate refund based on public vote percentage

                (refundFarmer, refundMerchant) = calculateRefund(dispute.publicVotePercentage);

            } else {

                // Expert decision, merchant at fault, farmer gets all

                refundFarmer = totalDeposit;

            }

        }


        if(refundFarmer > 0) farmer.transfer(refundFarmer);

        if(refundMerchant > 0) merchant.transfer(refundMerchant);


        emit DisputeResolved(uint(dispute.result));

    }


    function calculateRefund(uint publicVotePercentage) private view returns (uint refundWinner, uint refundLoser) {

        uint totalRefund = depositAmount * 2; // Total deposit from both parties


        if(publicVotePercentage > 80) {

            refundWinner = totalRefund;

        } else if(publicVotePercentage >= 50) {

            refundWinner = totalRefund * 90 / 100;

            refundLoser = totalRefund * 10 / 100;

        } else if(publicVotePercentage >= 30) {

            refundWinner = totalRefund * 75 / 100;

            refundLoser = totalRefund * 25 / 100;

        }


        // No action needed if less than 30%, defaults will return 0

        return (refundWinner, refundLoser);

    }


    // 附加功能：允许查看合约余额（用于测试和验证）

    function getContractBalance() public view returns (uint) {

        return address(this).balance;

    }

}

