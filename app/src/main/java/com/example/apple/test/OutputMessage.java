package com.example.apple.test;

/**
 * Created by Apple on 20/01/16.
 */

public class OutputMessage {

        public static String out(String x){
            String[] y = x.split(" ");

            if(y[1].equals("1,")){
                return ("User Registered Successfully");
            }
            else if ((y[1].equals("0,")) && y[4].equals("already")){
              return ("User already registered");
            }
            else {

                return ("Data not posted");
            }


        }
    }


