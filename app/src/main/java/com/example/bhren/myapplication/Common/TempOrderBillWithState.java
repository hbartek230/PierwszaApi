package com.example.bhren.myapplication.Common;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TempOrderBillWithState {
    private final TempOrderBill tempOrderBill;
    private final ItemState itemState;

}


