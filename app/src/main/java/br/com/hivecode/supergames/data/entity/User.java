package br.com.hivecode.supergames.data.entity;

public class User {
    private String name;
    private int age;
    private String address;

    private User(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public static class Builder{
        private String name;
        private int age;
        private String address;

        public Builder setName(final String name){
            this.name = name;
            return this;
        }

        public Builder setAge(final int age){
            this.age = age;
            return this;
        }

        public Builder setAddress(final String address){
            this.address = address;
            return this;
        }

        public User create(){
            User user = new User(name, age, address);
            if (user.name.isEmpty()) {
                throw new IllegalStateException("First name can not be empty!");
            }
            return user;

        }
    }
}
