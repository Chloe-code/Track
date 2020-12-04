package com.example.myapplication.Model;

//public class Users implements KvmSerializable {

import android.util.Log;

public class Users{
    public String name, status, userimg;

    public Users(){}


    public Users(String name, String status ) {//String userimg
        this.name = name;
        this.status = status;
        //this.userimg = userimg;

    }

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }
}

   /*public List<User> list = new ArrayList<>();
    @Override
    public Object getProperty(int arg0) {
        return list.get(arg0);
    }

    @Override
    public int getPropertyCount() {
        return list.size();
    }

    @Override
    public void setProperty(int arg0, Object arg1) {
        list.add(arg0, (User) arg1);

    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        arg2.type = User.class;
        arg2.name = "User";
        arg2.setValue(list.get(arg0));
    }

    private class User implements KvmSerializable {
        public String name, status;


        @Override
        public Object getProperty(int arg0) {
            switch (arg0)
            {
                case 0:
                    return name;
                case 1:
                    return status;
            }
            return null;
        }

        @Override
        public int getPropertyCount() {
            return 2;
        }

        @Override
        public void setProperty(int arg0, Object arg1) {
            switch (arg0) {
                case 0:
                    name = String.valueOf(arg1);
                    break;
                case 1:
                    status = String.valueOf(arg1);
                    break;
            }

        }

        @Override
        public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
            switch (arg0) {
                case 0:
                    arg2.name = "Name";
                    break;
                case 1:
                    arg2.name = "Status";
                    break;
            }
        }


    }
}*/



