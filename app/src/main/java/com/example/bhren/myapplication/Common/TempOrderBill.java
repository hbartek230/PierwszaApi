package com.example.bhren.myapplication.Common;

import com.example.bhren.myapplication.Model.TempOrder;

import java.io.Serializable;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(of = {"key"})
public class TempOrderBill implements Serializable {
    private String key;
    private TempOrder tempOrder;
}


