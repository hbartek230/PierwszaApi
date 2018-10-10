package com.example.bhren.myapplication.Common;

import java.io.Serializable;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TempOrderBillWithState implements Serializable {
    private final TempOrderBill tempOrderBill;
    private final ItemState itemState;

}


