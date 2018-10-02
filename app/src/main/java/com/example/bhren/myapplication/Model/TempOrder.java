package com.example.bhren.myapplication.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TempOrder {

    private String amount;
    private String kind;
    private String quantity;

}
