package com.sb.sweetbucket.utils.comparators;

import com.sb.sweetbucket.rest.response.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by harmeet on 27-08-2019.
 */

public class SortByDateComparator implements Comparator <Product> {

    private SimpleDateFormat format;

    public SortByDateComparator(SimpleDateFormat format) {
        this.format = format;
    }

    @Override
    public int compare(Product p1, Product p2) {

        try {
            Date d1= format.parse(p1.getCreatedAt());
            Date d2= format.parse(p2.getCreatedAt());

            if(d1.before(d2))
                return -1;
            else if(d1.after(d2))
                return 1;
            else
                return 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
