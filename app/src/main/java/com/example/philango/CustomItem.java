package com.example.philango;

import java.util.ArrayList;

public class CustomItem {
        ArrayList<String> Headlines;
        ArrayList<String> Bys;
        ArrayList<String> Username;
        ArrayList<Double> Amount;

        CustomItem(ArrayList<String> arr1,ArrayList<String> arr2,ArrayList<String> arr3,ArrayList<Double> arr4){
                this.Headlines = arr1;
                this.Bys = arr2;
                this.Username = arr3;
                this.Amount = arr4;
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

        public ArrayList<String> getUsername() {
                return Username;
        }

        public void setUsername(ArrayList<String> username) {
                Username = username;
        }

        public ArrayList<Double> getAmount() {
                return Amount;
        }

        public void setAmount(ArrayList<Double> amount) {
                Amount = amount;
        }
}
