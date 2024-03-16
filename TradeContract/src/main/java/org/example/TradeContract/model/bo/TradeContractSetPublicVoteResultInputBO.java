package org.example.TradeContract.model.bo;

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
public class TradeContractSetPublicVoteResultInputBO {
  private BigInteger _publicVotePercentage;

  private Boolean _isFarmerFault;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(_publicVotePercentage);
    args.add(_isFarmerFault);
    return args;
  }
}
