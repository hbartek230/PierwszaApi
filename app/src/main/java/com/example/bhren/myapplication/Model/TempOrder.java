package com.example.bhren.myapplication.Model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TempOrder implements Serializable {

    private String amount;
    private String kind;
    private String quantity;

}
