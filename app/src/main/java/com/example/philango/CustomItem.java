package com.example.philango;

import java.util.ArrayList;

public class CustomItem {
        ArrayList<String> Headlines;
        ArrayList<String> Bys;

        CustomItem(ArrayList<String> arr1,ArrayList<String> arr2){
                this.Headlines = arr1;
                this.Bys = arr2;
        }

        public ArrayList<String> getBys() {
                return Bys;
        }

        public void setBys(ArrayList<String> bys) {
                Bys = bys;
        }

        public ArrayList<String> getHeadlines() {
                return Headlines;
        }

        public void setHeadlines(ArrayList<String> headlines) {
                Headlines = headlines;
        }
}
