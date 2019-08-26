package com.sb.sweetbucket.utils.comparators;

import com.sb.sweetbucket.rest.response.Product;

import java.util.Comparator;

/**
 * Created by harmeet on 27-08-2019.
 */

public class HIghToLowComparator implements Comparator<Product> {


    @Override
    public int compare(Product p1, Product p2) {
        if(Double.parseDouble(p1.getSalePrice())<Double.parseDouble(p2.getSalePrice()))
            return 1;
        else if(Double.parseDouble(p1.getSalePrice())>Double.parseDouble(p2.getSalePrice()))
            return -1;
        else
            return 0;
    }
}
