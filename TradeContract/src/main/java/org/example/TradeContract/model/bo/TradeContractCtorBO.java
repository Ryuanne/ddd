package org.example.TradeContract.model.bo;

import java.lang.Object;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeContractCtorBO {
  private String _farmer;

  private String _merchant;

  private BigInteger _deliveryPrice;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(_farmer);
    args.add(_merchant);
    args.add(_deliveryPrice);
    return args;
  }
}
