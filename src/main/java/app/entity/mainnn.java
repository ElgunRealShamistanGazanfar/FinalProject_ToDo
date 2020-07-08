package app.entity;

import java.util.*;
import java.util.stream.Collectors;

public class mainnn {

    static int sockMerchant(int n, int[] ar) {
        List<Integer> lst = Arrays.stream(ar)
                .boxed()
                .collect(Collectors.toList());
        int cnt=0;

        Map<Integer, Long> myMap = lst.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        List<Long> dis = myMap.values().stream().collect(Collectors.toList());
        for (int i = 0; i < dis.size(); i++) {
            if (dis.get(i)%2==1){
                dis.set(i, dis.get(i)-1);
            }
            cnt+= dis.get(i);
        }
        return cnt/2;
    }

    public static void main(String[] args) {
        System.out.println(sockMerchant(10, new int[]{1, 1, 3, 1, 2, 1 ,3 ,3, 3, 3}));
    }
}
