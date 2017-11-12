package com.sneakerate.web.domain;

import java.util.Comparator;

public class SpecialOfferComparator implements Comparator<SpecialOffer> {
    @Override
    public int compare(SpecialOffer o1, SpecialOffer o2) {
        String youSave1 = o1.getDiscount();
        String youSave2 = o2.getDiscount();

        String percent1 = youSave1.substring(youSave1.indexOf('(') + 1, youSave1.lastIndexOf('%'));
        String percent2 = youSave2.substring(youSave2.indexOf('(') + 1, youSave2.lastIndexOf('%'));

        return Integer.valueOf(percent1).compareTo(Integer.valueOf(percent2));
    }
}
