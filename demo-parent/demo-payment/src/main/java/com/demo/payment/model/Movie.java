package com.demo.payment.model;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * @author zzj
 * @create 2019-04-26 15:12
 **/
@Data
public class Movie {


    private  String id;
    private  User user;

    public static void main(String[] args) {
        Movie movie=new Movie();
        System.out.println(ObjectUtils.isEmpty(movie));
        System.out.println(ObjectUtils.isEmpty(movie.getUser()));

    }
}
