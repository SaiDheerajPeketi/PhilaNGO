package com.example.philango;

public class CustomItem {
        String[] Headlines;
        String[] Bys;

        CustomItem(String[] arr1,String[] arr2){
                this.Headlines = arr1;
                this.Bys = arr2;
        }

        public String[] getHeadlines() {
                return Headlines;
        }

        public void setHeadlines(String[] headlines) {
                Headlines = headlines;
        }

        public String[] getBys() {
                return Bys;
        }

        public void setBys(String[] bys) {
                Bys = bys;
        }
}
