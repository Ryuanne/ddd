package org.example.demo.model.bo;

import java.lang.Boolean;
import java.lang.Object;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeContractChooseArbitrationAndSubmitResultInputBO {
  private BigInteger _arbitrationType;

  private Boolean _isFarmerFault;

  private BigInteger _publicVotePercentage;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(_arbitrationType);
    args.add(_isFarmerFault);
    args.add(_publicVotePercentage);
    return args;
  }
}
